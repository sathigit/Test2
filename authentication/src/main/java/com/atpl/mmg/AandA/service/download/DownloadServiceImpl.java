package com.atpl.mmg.AandA.service.download;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;

import com.atpl.mmg.AandA.constant.Constants;
import com.atpl.mmg.AandA.constant.EnquiryStatus;
import com.atpl.mmg.AandA.constant.Role;
import com.atpl.mmg.AandA.domain.profile.ProfileDomainV2;
import com.atpl.mmg.AandA.exception.MmgRestException.ROLE_NOT_FOUND;
import com.atpl.mmg.AandA.model.profile.ListDto;
import com.atpl.mmg.AandA.model.profile.Profile;
import com.atpl.mmg.AandA.service.profile.ProfileServiceV2;
import com.atpl.mmg.AandA.service.profile.ProfileUtil;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class DownloadServiceImpl implements DownloadService {

	@Autowired
	ProfileServiceV2 profileServiceV2;

	@Autowired
	ProfileUtil profileUtil;

	@Override
	public MultiValueMap<String, String> getHeaders(String type) {
		final MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		String fileName = null;

		if (type.equalsIgnoreCase(Constants.EXCEL_TYPE))
			fileName = "Customer.xlsx";
		else if (type.equalsIgnoreCase(Constants.PDF_TYPE))
			fileName = "Customer.pdf";

		headers.add("Content-disposition", "attachment; filename=" + fileName);
		return headers;
	}

	@Override
	public byte[] downloadProfile(int roleId, @RequestParam Map<String, String> reqParam) throws Exception {
		byte[] byteArray = null;
		String status = null, franchiseId = null;
		Map<String, String> reqParams = new HashMap<String, String>();
		ListDto listDto = null;
		List<Profile> profileModelList = null;
		if (reqParam.size() > 0) {
			if (reqParam.containsKey("status")) {
				status = reqParam.get("status");
			}
			if (reqParam.containsKey("franchiseId")) {
				franchiseId = reqParam.get("franchiseId");
			}
		}
		if (status != null && franchiseId != null)
			listDto = profileServiceV2.getProfileByRoleAndFranchiseId(roleId, Boolean.parseBoolean(status), franchiseId,
					true,reqParam);
		if (franchiseId != null) {
			listDto = profileServiceV2.getProfileByRoleAndFranchiseId(roleId, Boolean.parseBoolean(status), franchiseId,
					false,reqParam);
		}
		if (status != null) {
			boolean sts = Boolean.parseBoolean(status);
			listDto = profileServiceV2.getActiveOrInactiveProfiles(roleId, sts, reqParams);
		}
		if (status == null && franchiseId == null)
			listDto = profileServiceV2.getProfileDetailsByRoleId(roleId, reqParams);
		profileModelList = listDto.getList();
		profileModelList = profileUtil.downloadProfileList(profileModelList);
		String[] commonColumnshead = { "Name", "Mobile Number", "Email", "Address", "Requested Date", "Status" };
		String[] commonGeneralhead = { "GstNo", "licenseNo", "PanNo" };
		Role role = Role.getRole(roleId + "");
		if (null == role)
			throw new ROLE_NOT_FOUND(roleId + "");
		switch (role) {
		case FRANCHISE:
		case WAREHOUSE:
		case FLEET_OPERATOR:
			String[] columnshead = { role + " Id", "CompanyName", "Year Of Contract", "Start Date", "end Date" };
			if (role.equals(Role.WAREHOUSE) || role.equals(Role.FLEET_OPERATOR)) {
				String[] extraHead = { "franchiseId" };
				commonColumnshead = ArrayUtils.addAll(commonColumnshead, commonGeneralhead);
				commonColumnshead = ArrayUtils.addAll(commonColumnshead, columnshead);
				commonColumnshead = ArrayUtils.addAll(commonColumnshead, extraHead);
			} else {
				commonColumnshead = ArrayUtils.addAll(commonColumnshead, commonGeneralhead);
				commonColumnshead = ArrayUtils.addAll(commonColumnshead, columnshead);
			}
			break;
		case ENTERPRISE:
			String[] enterprisehead = { role + " Id", "CompanyName", "entrepreneurName", "Year Of Contract",
					"Start Date", "end Date", "franchiseId" };
			commonColumnshead = ArrayUtils.addAll(commonColumnshead, commonGeneralhead);
			commonColumnshead = ArrayUtils.addAll(commonColumnshead, enterprisehead);
			break;
		default:
			break;
		}
		byteArray = downloadExcelReport(profileModelList, commonColumnshead, role);
		return byteArray;
	}

	private byte[] downloadExcelReport(List<Profile> profileModelList, String[] columnshead, Role role)
			throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		// Blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();

		// Create a blank sheet
		XSSFSheet sheet = workbook.createSheet(role + " Data");

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
		for (Profile profile : profileModelList) {
			row = sheet.createRow(count);
			for (int j = 0; j < columnshead.length; j++) {
				rowCell = row.createCell(j);
				commonExcelReport(j, rowCell, profile);
				switch (role) {
				case FRANCHISE:
					if (j > 5)
						otherExcelReport(j, rowCell, profile, role);
					if (j > 8)
						franchiseExcelReport(j, rowCell, profile);
					break;
				case WAREHOUSE:
					if (j > 5)
						otherExcelReport(j, rowCell, profile, role);
					if (j > 8)
						warehouseExcelReport(j, rowCell, profile);
					break;
				case FLEET_OPERATOR:
					if (j > 5)
						otherExcelReport(j, rowCell, profile, role);
					if (j > 8)
						fleetExcelReport(j, rowCell, profile);
					break;
				case ENTERPRISE:
					if (j > 5)
						otherExcelReport(j, rowCell, profile, role);
					if (j > 8)
						enterpriseExcelReport(j, rowCell, profile);
					break;
				default:
					break;
				}
			}
			count++;
		}
		workbook.write(out);
		workbook.close();
		return out.toByteArray();

	}

	private void commonExcelReport(int j, Cell rowCell, Profile profile) {
		String isActive = null;
		switch (j) {
		case 0:
			rowCell.setCellValue(profile.getFirstName() + " " + profile.getLastName());
			break;
		case 1:
			rowCell.setCellValue(String.valueOf(profile.getMobileNumber()));
			break;
		case 2:
			rowCell.setCellValue(profile.getEmailId());
			break;
		case 3:
			if (!profile.getAddress().isEmpty()) {
				String address = null;
				if (profile.getAddress().get(0).getAddress1() != null
						&& profile.getAddress().get(0).getAddress2() != null)
					address = profile.getAddress().get(0).getAddress1() + ","
							+ profile.getAddress().get(0).getAddress2();
				else
					address = profile.getAddress().get(0).getAddress1();
				rowCell.setCellValue(address + "," + profile.getAddress().get(0).getCityName() + ","
						+ profile.getAddress().get(0).getStateName() + ","
						+ profile.getAddress().get(0).getCountryName());
			}

			break;
		case 4:
			rowCell.setCellValue(String.valueOf(profile.getCreatedDate()));
			break;
		case 5:
			if (profile.getIsActive())
				isActive = EnquiryStatus.ACTIVE.name();
			else
				isActive = EnquiryStatus.INACTIVE.name();
			rowCell.setCellValue(isActive);
			break;
		}
	}

	private void otherExcelReport(int j, Cell rowCell, Profile profile, Role role) {
		switch (j) {
		case 6:
			if (Role.FRANCHISE.equals(role))
				rowCell.setCellValue(profile.getFranchise().getGstNo());
			break;
		case 7:
			if (Role.FRANCHISE.equals(role))
				rowCell.setCellValue(profile.getFranchise().getLicenseNo());
			break;
		case 8:
			rowCell.setCellValue(profile.getPanNumber());
			break;
		}
	}

	private void franchiseExcelReport(int j, Cell rowCell, Profile profile) {
		switch (j) {
		case 9:
			rowCell.setCellValue(profile.getFranchise().getFranchiseId());
			break;
		case 10:
			rowCell.setCellValue(profile.getFranchise().getCompanyName());
			break;
		case 11:
			rowCell.setCellValue(profile.getFranchise().getYearOfContract());
			break;
		case 12:
			rowCell.setCellValue(profile.getFranchise().getGstNo());
			break;
		case 13:
			rowCell.setCellValue(profile.getFranchise().getLicenseNo());
			break;
		case 14:
			rowCell.setCellValue(String.valueOf(profile.getFranchise().getStartDate()));
			break;
		case 15:
			rowCell.setCellValue(String.valueOf(profile.getFranchise().getEndDate()));
			break;
		}
	}

	private void warehouseExcelReport(int j, Cell rowCell, Profile profile) {
		switch (j) {
		case 9:
			rowCell.setCellValue(profile.getWarehouse().getWareHouseId());
			break;
		case 10:
			rowCell.setCellValue(profile.getWarehouse().getCompanyName());
			break;
		case 11:
			rowCell.setCellValue(profile.getWarehouse().getYearOfContract());
			break;
		case 12:
			rowCell.setCellValue(profile.getWarehouse().getStartDate());
			break;
		case 13:
			rowCell.setCellValue(profile.getWarehouse().getEndDate());
			break;
		case 14:
			rowCell.setCellValue(profile.getWarehouse().getFranchiseId());
			break;
		}
	}

	private void fleetExcelReport(int j, Cell rowCell, Profile profile) {
		switch (j) {
		case 9:
			rowCell.setCellValue(profile.getFleet().getFleetId());
			break;
		case 10:
			rowCell.setCellValue(profile.getFleet().getCompanyName());
			break;
		case 11:
			rowCell.setCellValue(profile.getFleet().getYearOfContract());
			break;
		case 12:
			rowCell.setCellValue(profile.getFleet().getStartDate());
			break;
		case 13:
			rowCell.setCellValue(profile.getFleet().getEndDate());
			break;
		case 14:
			rowCell.setCellValue(profile.getFleet().getFranchiseId());
			break;
		}
	}

	private void enterpriseExcelReport(int j, Cell rowCell, Profile profile) {
		switch (j) {
		case 9:
			rowCell.setCellValue(profile.getEnterprise().getEnterpriseId());
			break;
		case 10:
			rowCell.setCellValue(profile.getEnterprise().getCompanyName());
			break;
		case 11:
			rowCell.setCellValue(profile.getEnterprise().getEntrepreneurName());
			break;
		case 12:
			rowCell.setCellValue(profile.getEnterprise().getYearOfContract());
			break;
		case 13:
			rowCell.setCellValue(String.valueOf(profile.getEnterprise().getStartDate()));
			break;
		case 14:
			rowCell.setCellValue(String.valueOf(profile.getEnterprise().getEndDate()));
			break;
		case 15:
			rowCell.setCellValue(profile.getEnterprise().getFranchiseId());
			break;
		}
	}

	private byte[] downloadPDFReport(List<ProfileDomainV2> profileModelList, String[] columnshead) throws Exception {
		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		PdfPTable table = new PdfPTable(columnshead.length);
		table.setWidthPercentage(100);

		com.itextpdf.text.Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

		PdfPCell hcell;
		for (int count = 0; count < columnshead.length; count++) {
			hcell = new PdfPCell(new Phrase(columnshead[count], headFont));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);
		}

		for (ProfileDomainV2 profileDomain : profileModelList) {
			PdfPCell cell;
			for (int j = 0; j < columnshead.length; j++) {
				switch (j + 1) {
				case 1:
					cell = new PdfPCell(new Phrase(profileDomain.getFirstName().toString()));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_CENTER);
					table.addCell(cell);
					break;
				case 2:
					cell = new PdfPCell(new Phrase(String.valueOf(profileDomain.getMobileNumber())));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);
					break;
				case 3:
					cell = new PdfPCell(new Phrase(String.valueOf(profileDomain.getEmailId())));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);
					break;
				case 4:
					cell = new PdfPCell(new Phrase(String.valueOf(profileDomain.getCreatedDate())));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					table.addCell(cell);
					break;
				}
			}
		}

		PdfWriter.getInstance(document, out);
		document.open();
		document.add(table);

		document.close();

		return out.toByteArray();
	}
}
