package com.atpl.mmg.service.download;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.dao.city.CityDAO;
import com.atpl.mmg.dao.country.CountryDAO;
import com.atpl.mmg.dao.state.StateDAO;
import com.atpl.mmg.domain.city.CityDomain;
import com.atpl.mmg.domain.country.CountryDomain;
import com.atpl.mmg.domain.state.StateDomain;
import com.atpl.mmg.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.model.faredist.EarningsModel;
import com.atpl.mmg.service.faredist.FareDistributionUtil;
import com.atpl.mmg.service.fdtrans.FareDistributionTransactionService;
import com.atpl.mmg.utils.DateUtility;
import com.atpl.mmg.utils.EmailValidator;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

@Service
public class DownloadServiceImpl implements DownloadService {

	@Autowired
	FareDistributionTransactionService fareDistributionTransactionService;

	@Autowired
	CityDAO cityDAO;

	@Autowired
	StateDAO stateDAO;

	@Autowired
	CountryDAO countryDAO;

	@Autowired
	EmailValidator emailValidator;

	@Autowired
	FareDistributionUtil fareDistributionUtil;

	@Override
	public MultiValueMap<String, String> getHeaders(String type) {
		final MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		String fileName = null;

		if (type.equalsIgnoreCase(Constants.EXCEL_TYPE))
			fileName = "Earnings.xlsx";
		else if (type.equalsIgnoreCase(Constants.PDF_TYPE))
			fileName = "Earnings.xlsx";

		headers.add("Content-disposition", "attachment; filename=" + fileName);
		return headers;
	}

	@Override
	public byte[] downloadEarnings(String type, int roleId, Map<String, String> reqParam) throws Exception {
		byte[] byteArray = null;
		String startDate = null, endDate = null;
		Date startsDate = null, endsDate = null;
		startsDate = fareDistributionUtil.validateMendatoryDateByDateType(reqParam, Constants.START_DATE);
		endsDate = fareDistributionUtil.validateMendatoryDateByDateType(reqParam, Constants.END_DATE);
		EarningsModel earningModel = new EarningsModel();
		if (null != startsDate && null != startsDate) {
			fareDistributionUtil.checkStartDateAndEndDate(startsDate, endsDate);
			startDate = DateUtility.getDateByStringFormat(startsDate, DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
			endDate = DateUtility.getDateByStringFormat(endsDate, DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
			earningModel = fareDistributionTransactionService.getEarningsDataForDownload(roleId, reqParam, startDate,
					endDate);
			startDate = DateUtility.dateFormater(startDate, DateUtility.DATE_FORMAT_YYYY_MM_DD,
					DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
			endDate = DateUtility.dateFormater(endDate, DateUtility.DATE_FORMAT_YYYY_MM_DD,
					DateUtility.DATE_FORMAT_YYYY_MM_DD_HHMMSS);
		}
		if (type.equalsIgnoreCase(Constants.PDF_TYPE)) {
			byteArray = downloadPDFReport(earningModel, startDate, endDate);
		}
		return byteArray;
	}

	private byte[] downloadPDFReport(EarningsModel earningModelList, String startDate, String endDate)
			throws DocumentException, IOException {
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		/* Invoice Html to Pdf */
		File f1 = new File("Earning-" + earningModelList.getRole() + ".pdf");
		OutputStream file = new FileOutputStream(f1);
		try {
			PdfWriter writer = PdfWriter.getInstance(document, file);
			StringBuilder htmlString = new StringBuilder();

			Map<String, Object> pdfVariables = new HashMap<>();
			pdfVariables.put("companyName",
					earningModelList.getProfile().getFirstName() + " " + earningModelList.getProfile().getLastName());
			pdfVariables.put("mobileNumber", earningModelList.getProfile().getMobileNumber());
			pdfVariables.put("address", earningModelList.getProfile().getAddr());
			pdfVariables.put("emailId", earningModelList.getProfile().getEmailId());
			pdfVariables.put("earnings", earningModelList.getEarnings());
			pdfVariables.put("totalEarnings", earningModelList.getTotalEarnings());
			pdfVariables.put("role", earningModelList.getRole());
			pdfVariables.put("startDate", startDate);
			pdfVariables.put("endDate", endDate);
			String body = emailValidator.generateMailHtml("earning", pdfVariables);
			htmlString.append(body);

			document.open();
			InputStream input = new ByteArrayInputStream(htmlString.toString().getBytes());
			XMLWorkerHelper.getInstance().parseXHtml(writer, document, input);
			document.close();
			file.close();
//			HtmlConverter.convertToPdf(htmlString.toString(), file);
//			System.out.println("PDF Created!");

		} catch (Exception e) {
			e.printStackTrace();
			throw new NOT_FOUND(e.toString());
		}

		FileInputStream fis = new FileInputStream(f1.getPath());
		byte[] buf = new byte[1024];
		try {
			for (int readNum; (readNum = fis.read(buf)) != -1;) {
				out.write(buf, 0, readNum); // no doubt here is 0
				// Writes len bytes from the specified byte array starting at offset off to this
				// byte array output stream.
				System.out.println("read " + readNum + " bytes,");
			}
		} catch (IOException ex) {

		}
		document.close();
		file.close();
		f1.delete();
		return out.toByteArray();
	}

	@Override
	public byte[] exportGeography(String geographyType, Map<String, String> reqParam) throws Exception {
		int state = 0, country = 0;
		List<CityDomain> cityDomainList = new ArrayList<CityDomain>();
		List<StateDomain> stateDomainList = new ArrayList<StateDomain>();
		List<CountryDomain> countryDomainList = new ArrayList<CountryDomain>();
		byte[] byteArray = null;
		if (reqParam.size() != 0) {
			if (reqParam.containsKey("countryId")) {
				String countryId = reqParam.get("countryId");
				country = Integer.parseInt(countryId);
			}
			if (reqParam.containsKey("stateId")) {
				String stateId = reqParam.get("stateId");
				state = Integer.parseInt(stateId);
			}
		}
		if (geographyType.equalsIgnoreCase(Constants.CITY)) {
			cityDomainList = cityDAO.getCities(country, state, 0, 0);
			if (!cityDomainList.isEmpty()) {
				String[] columnshead = { "id", "name", "state_id", "pincode", "alias", "shortName", "isGovtPreferred" };
				byteArray = downloadExcelReport(cityDomainList, stateDomainList, countryDomainList, columnshead,
						geographyType);
			}
		}
		if (geographyType.equalsIgnoreCase(Constants.STATE)) {
			stateDomainList = stateDAO.getStates(country, 0, 0);
			if (!stateDomainList.isEmpty()) {
				String[] columnshead = { "id", "name", "country_id", "shortName" };
				byteArray = downloadExcelReport(cityDomainList, stateDomainList, countryDomainList, columnshead,
						geographyType);
			}
		}
		if (geographyType.equalsIgnoreCase(Constants.COUNTRY)) {
			countryDomainList = countryDAO.getCountry(0, 0);
			if (!countryDomainList.isEmpty()) {
				String[] columnshead = { "id", "name", "code", "shortName" };
				byteArray = downloadExcelReport(cityDomainList, stateDomainList, countryDomainList, columnshead,
						geographyType);
			}
		}
		return byteArray;
	}

	private byte[] downloadExcelReport(List<CityDomain> cityDomainList, List<StateDomain> stateDomainList,
			List<CountryDomain> countryDomainList, String[] columnshead, String geographyType) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		// Blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet(geographyType + "_Data");

		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 11);
		headerFont.setColor(IndexedColors.BLACK.getIndex());
		headerFont.setFontName("Arial");

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		Row headerRow = sheet.createRow(0);

		for (int count = 0; count < columnshead.length; count++) {
			Cell cell = headerRow.createCell(count);
			cell.setCellValue(columnshead[count]);
			cell.setCellStyle(headerCellStyle);
		}

		for (int i = 0; i < columnshead.length; i++) {
			sheet.autoSizeColumn(i);
		}

		Row row = null;
		Cell rowCell = null;
		int count = 1;
		if (!cityDomainList.isEmpty() || cityDomainList != null)
			cityReport(row, cityDomainList, rowCell, count, sheet, columnshead);
		if (!stateDomainList.isEmpty() || stateDomainList != null)
			stateReport(row, stateDomainList, rowCell, count, sheet, columnshead);
		if (!countryDomainList.isEmpty() || countryDomainList != null)
			countryReport(row, countryDomainList, rowCell, count, sheet, columnshead);
		workbook.write(out);
		workbook.close();
		return out.toByteArray();
	}

	private void cityReport(Row row, List<CityDomain> cityDomainList, Cell rowCell, int count, XSSFSheet sheet,
			String[] columnshead) {
		for (CityDomain cityDomain : cityDomainList) {
			row = sheet.createRow(count);
			for (int j = 0; j < columnshead.length; j++) {
				rowCell = row.createCell(j);
				cityExcel(j, rowCell, cityDomain);
			}
			count++;
		}
	}

	private void stateReport(Row row, List<StateDomain> stateDomainList, Cell rowCell, int count, XSSFSheet sheet,
			String[] columnshead) {
		for (StateDomain stateDomain : stateDomainList) {
			row = sheet.createRow(count);
			for (int j = 0; j < columnshead.length; j++) {
				rowCell = row.createCell(j);
				stateExcel(j, rowCell, stateDomain);
			}
			count++;
		}
	}

	private void countryReport(Row row, List<CountryDomain> countryDomainList, Cell rowCell, int count, XSSFSheet sheet,
			String[] columnshead) {
		for (CountryDomain countryDomain : countryDomainList) {
			row = sheet.createRow(count);
			for (int j = 0; j < columnshead.length; j++) {
				rowCell = row.createCell(j);
				countryExcel(j, rowCell, countryDomain);
			}
			count++;
		}
	}

	private void countryExcel(int j, Cell rowCell, CountryDomain countryDomain) {
		switch (j) {
		case 0:
			rowCell.setCellValue(countryDomain.getId());
			break;
		case 1:
			rowCell.setCellValue(countryDomain.getName());
			break;
		case 2:
			rowCell.setCellValue(String.valueOf(countryDomain.getCode()));
			break;
		case 3:
			rowCell.setCellValue(countryDomain.getShortname());
			break;
		}
	}

	private void stateExcel(int j, Cell rowCell, StateDomain stateDomain) {
		switch (j) {
		case 0:
			rowCell.setCellValue(stateDomain.getId());
			break;
		case 1:
			rowCell.setCellValue(stateDomain.getName());
			break;
		case 2:
			rowCell.setCellValue(String.valueOf(stateDomain.getCountry_id()));
			break;
		case 3:
			rowCell.setCellValue(stateDomain.getShortName());
			break;
		}
	}

	private void cityExcel(int j, Cell rowCell, CityDomain cityDomain) {
		switch (j) {
		case 0:
			rowCell.setCellValue(cityDomain.getId());
			break;
		case 1:
			rowCell.setCellValue(cityDomain.getName());
			break;
		case 2:
			rowCell.setCellValue(String.valueOf(cityDomain.getState_id()));
			break;
		case 3:
			rowCell.setCellValue(cityDomain.getPinCode());
			break;
		case 4:
			rowCell.setCellValue(cityDomain.getAlias());
			break;
		case 5:
			rowCell.setCellValue(cityDomain.getShortName());
			break;
		case 6:
			rowCell.setCellValue(cityDomain.getIsGovtPreferred());
			break;
		}
	}

	@Override
	public String importGeography(MultipartFile file, String geographyType) throws Exception {
		hasExcelFormat(file);
		fetchGeographyData(file, geographyType);
		return "Success";
	}

	@Transactional(rollbackFor = Exception.class)
	private void fetchGeographyData(MultipartFile file, String geographyType) throws IOException {
		List<CityDomain> cityDomainList = new ArrayList<CityDomain>();
		List<StateDomain> stateDomainList = new ArrayList<StateDomain>();
		List<CountryDomain> countryDomainList = new ArrayList<CountryDomain>();
		try {
			XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
			XSSFSheet sheet = workbook.getSheetAt(0);
			/**
			 * checkTemplate(sheet, Constants.CITY_DATA_IDENTIFIER);
			 */

			Row row = null;
			Iterator<Row> rowIterator = sheet.iterator();
			int rowNumber = 0;
			if (geographyType.equalsIgnoreCase(Constants.CITY)) {
				cityDomainList = fetchCities(rowIterator, row, rowNumber);
				/* Save City Domain */
				saveUpdateCities(cityDomainList);
			}
			if (geographyType.equalsIgnoreCase(Constants.STATE)) {
				stateDomainList = fetchStates(rowIterator, row, rowNumber);
				saveUpdateStates(stateDomainList);
			}
			if (geographyType.equalsIgnoreCase(Constants.COUNTRY)) {
				countryDomainList = fetchCountres(rowIterator, row, rowNumber);
				saveUpdateCountries(countryDomainList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new NOT_FOUND("Upload cities dump failed");
		}
	}

	private List<CityDomain> fetchCities(Iterator<Row> rowIterator, Row row, int rowNumber) {
		List<CityDomain> cityDomainList = new ArrayList<CityDomain>();
		CityDomain cityDomain = new CityDomain();
		while (rowIterator.hasNext()) {
			row = rowIterator.next();
			if (rowNumber == 0) {
				rowNumber++;
				continue;
			}
			Iterator<Cell> cellIterator = row.cellIterator();
			int cellIdx = 0;
			while (cellIterator.hasNext()) {
				Cell currentCell = cellIterator.next();
				switch (cellIdx) {
				case 0:
					if (currentCell.getNumericCellValue() != 0.0) {
						int id = (int) currentCell.getNumericCellValue();
						cityDomain.setId(id);
					}
					break;
				case 1:
					if (!currentCell.getStringCellValue().isEmpty())
						cityDomain.setName(currentCell.getStringCellValue());
					break;
				case 2:
					if (!currentCell.getStringCellValue().isEmpty())
						cityDomain.setState_id(Integer.parseInt(currentCell.getStringCellValue()));
					break;
				case 3:
					if (!currentCell.getStringCellValue().isEmpty())
						cityDomain.setPinCode(currentCell.getStringCellValue());
					break;
				case 4:
					if (!currentCell.getStringCellValue().isEmpty())
						cityDomain.setAlias(currentCell.getStringCellValue());
					else
						cityDomain.setAlias(null);
					break;
				case 5:
					if (!currentCell.getStringCellValue().isEmpty())
						cityDomain.setShortName(currentCell.getStringCellValue());
					else
						cityDomain.setShortName(null);
					break;
				case 6:
					if (!currentCell.getStringCellValue().isEmpty())
						cityDomain.setIsGovtPreferred(currentCell.getStringCellValue());
					else
						cityDomain.setIsGovtPreferred(null);
					break;
				default:
					break;
				}
				cellIdx++;
			}
			CityDomain cityDomainv2 = new CityDomain();
			BeanUtils.copyProperties(cityDomain, cityDomainv2);
			cityDomainList.add(cityDomainv2);
		}
		return cityDomainList;
	}

	private List<StateDomain> fetchStates(Iterator<Row> rowIterator, Row row, int rowNumber) {
		List<StateDomain> stateDomainList = new ArrayList<StateDomain>();
		StateDomain stateDomain = new StateDomain();
		while (rowIterator.hasNext()) {
			row = rowIterator.next();
			if (rowNumber == 0) {
				rowNumber++;
				continue;
			}
			Iterator<Cell> cellIterator = row.cellIterator();
			int cellIdx = 0;
			while (cellIterator.hasNext()) {
				Cell currentCell = cellIterator.next();
				switch (cellIdx) {
				case 0:
					if (currentCell.getNumericCellValue() != 0.0) {
						int id = (int) currentCell.getNumericCellValue();
						stateDomain.setId(id);
					}
					break;
				case 1:
					if (!currentCell.getStringCellValue().isEmpty())
						stateDomain.setName(currentCell.getStringCellValue());
					break;
				case 2:
					if (!currentCell.getStringCellValue().isEmpty())
						stateDomain.setCountry_id(Integer.parseInt(currentCell.getStringCellValue()));
					break;
				case 3:
					if (!currentCell.getStringCellValue().isEmpty())
						stateDomain.setShortName(currentCell.getStringCellValue());
					else
						stateDomain.setShortName(null);
					break;
				default:
					break;
				}
				cellIdx++;
			}
			StateDomain stateDomainV2 = new StateDomain();
			BeanUtils.copyProperties(stateDomain, stateDomainV2);
			stateDomainList.add(stateDomainV2);
		}
		return stateDomainList;
	}

	private List<CountryDomain> fetchCountres(Iterator<Row> rowIterator, Row row, int rowNumber) {
		List<CountryDomain> countryDomainList = new ArrayList<CountryDomain>();
		CountryDomain countryDomain = new CountryDomain();
		while (rowIterator.hasNext()) {
			row = rowIterator.next();
			if (rowNumber == 0) {
				rowNumber++;
				continue;
			}
			Iterator<Cell> cellIterator = row.cellIterator();
			int cellIdx = 0;
			while (cellIterator.hasNext()) {
				Cell currentCell = cellIterator.next();
				switch (cellIdx) {
				case 0:
					if (currentCell.getNumericCellValue() != 0.0) {
						int id = (int) currentCell.getNumericCellValue();
						countryDomain.setId(id);
					}
					break;
				case 1:
					if (!currentCell.getStringCellValue().isEmpty())
						countryDomain.setName(currentCell.getStringCellValue());
					break;
				case 2:
					if (!currentCell.getStringCellValue().isEmpty())
						countryDomain.setCode(Integer.parseInt(currentCell.getStringCellValue()));
					break;
				case 3:
					if (!currentCell.getStringCellValue().isEmpty())
						countryDomain.setShortname(currentCell.getStringCellValue());
					else
						countryDomain.setShortname(null);
					break;
				default:
					break;
				}
				cellIdx++;
			}
			CountryDomain countryDomainV2 = new CountryDomain();
			BeanUtils.copyProperties(countryDomain, countryDomainV2);
			countryDomainList.add(countryDomainV2);
		}
		return countryDomainList;
	}

	private void saveUpdateCities(List<CityDomain> cityDomainList) throws Exception {
		for (CityDomain cityDomain : cityDomainList) {
			CityDomain cityDomains = cityDAO.getCity(cityDomain.getId());
			if (cityDomains != null)
				cityDAO.updateCity(cityDomain);
			else
				cityDAO.addCity(cityDomain);
		}
	}

	private void saveUpdateStates(List<StateDomain> stateDomainList) throws Exception {
		for (StateDomain stateDomain : stateDomainList) {
			StateDomain stateDomains = stateDAO.getState(stateDomain.getId());
			if (stateDomains != null)
				stateDAO.UpdateState(stateDomain);
			else
				stateDAO.addState(stateDomain);
		}
	}

	private void saveUpdateCountries(List<CountryDomain> countryDomainList) throws Exception {
		for (CountryDomain countryDomain : countryDomainList) {
			CountryDomain countryDomains = countryDAO.getCountry(countryDomain.getId());
			if (countryDomains != null)
				countryDAO.UpdateCountry(countryDomain);
			else
				countryDAO.addCountry(countryDomain);
		}
	}

	public static boolean hasExcelFormat(MultipartFile file) throws Exception {
		String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		if (!TYPE.equals(file.getContentType())) {
			throw new NOT_FOUND("Please upload the excel file");
		}
		return true;
	}

}
