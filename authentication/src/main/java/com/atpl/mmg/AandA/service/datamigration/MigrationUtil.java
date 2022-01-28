package com.atpl.mmg.AandA.service.datamigration;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.atpl.mmg.AandA.constant.Role;
import com.atpl.mmg.AandA.exception.MmgRestException.NOT_FOUND;
import com.atpl.mmg.AandA.model.bankaccount.BankAccountModel;
import com.atpl.mmg.AandA.model.datamigration.BoardingRequestTemplateModel;
import com.atpl.mmg.AandA.model.datamigration.ProfileImageTemplateModel;
import com.atpl.mmg.AandA.model.datamigration.ProfileTemplateModel;
import com.atpl.mmg.AandA.model.profile.Franchise;

@Component
public class MigrationUtil {

	public static boolean hasExcelFormat(MultipartFile file) throws Exception {
		String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		if (!TYPE.equals(file.getContentType())) {
			throw new NOT_FOUND("Please upload the excel file");
		}
		return true;
	}

	public void commonProfile(int cellIdx, Cell currentCell, ProfileTemplateModel customerModel) {
		switch (cellIdx) {
		case 0:
			if (!currentCell.getStringCellValue().isEmpty())
				customerModel.setFirstName(currentCell.getStringCellValue());
			break;
		case 1:
			if (!currentCell.getStringCellValue().isEmpty() || !currentCell.getStringCellValue().equals("null"))
				customerModel.setLastName(currentCell.getStringCellValue());
			break;
		case 2:
			String roleId = NumberToTextConverter.toText(currentCell.getNumericCellValue());
			customerModel.setRoleId(Integer.parseInt(roleId));
			break;
		case 3:
			if (currentCell.getNumericCellValue() != 0.0) {
				String gender = NumberToTextConverter.toText(currentCell.getNumericCellValue());
				customerModel.setGenderId(Integer.parseInt(gender));
			} else
				customerModel.setGenderId(1);
			break;
		case 4:
			if (currentCell.getNumericCellValue() != 0.0) {
				String country = NumberToTextConverter.toText(currentCell.getNumericCellValue());
				customerModel.setCountryId(Integer.parseInt(country));
			} else
				customerModel.setCountryId(0);
			break;
		case 5:
			if (currentCell.getNumericCellValue() != 0.0) {
				String state = NumberToTextConverter.toText(currentCell.getNumericCellValue());
				customerModel.setStateId(Integer.parseInt(state));
			} else
				customerModel.setStateId(0);
			break;
		case 6:
			if (currentCell.getNumericCellValue() != 0.0) {
				String city = NumberToTextConverter.toText(currentCell.getNumericCellValue());
				customerModel.setCityId(Integer.parseInt(city));
			} else
				customerModel.setCityId(0);
			break;
		case 7:
			if (!currentCell.getStringCellValue().isEmpty() || !currentCell.getStringCellValue().equals("null"))
				customerModel.setDoorNumber(currentCell.getStringCellValue());
			break;
		case 8:
			if (!currentCell.getStringCellValue().isEmpty() || !currentCell.getStringCellValue().equals("null"))
				customerModel.setStreet(currentCell.getStringCellValue());
			break;
		case 9:
			if (!currentCell.getStringCellValue().isEmpty()) {
				if (!currentCell.getStringCellValue().equals("null"))
					customerModel.setMobileNumber(currentCell.getStringCellValue());
				else
					customerModel.setMobileNumber(null);
			}
			break;
		case 10:
			if (!currentCell.getStringCellValue().isEmpty()) {
				if (!currentCell.getStringCellValue().equals("null"))
					customerModel.setAlternativeNumber(currentCell.getStringCellValue());
				else
					customerModel.setAlternativeNumber("0");
			}
			break;
		case 11:
			if (!currentCell.getStringCellValue().isEmpty())
				if (!currentCell.getStringCellValue().equals("null"))
					customerModel.setEmailId(currentCell.getStringCellValue());
				else
					customerModel.setEmailId(null);
			break;
		case 12:
			if (currentCell.getNumericCellValue() != 0.0) {
				String pincode = NumberToTextConverter.toText(currentCell.getNumericCellValue());
				BigInteger pinCode = new BigInteger(pincode);
				customerModel.setPincode(pinCode);
			}
			break;
		case 13:
			System.out.println(currentCell.getDateCellValue());
			Date dob = currentCell.getDateCellValue();
			customerModel.setDob(dob);
			break;
		case 14:
			customerModel.setPassword(currentCell.getStringCellValue());
			break;
		case 15:
			customerModel.setConfirmPassword(currentCell.getStringCellValue());
			break;
		case 16:
			if (!currentCell.getStringCellValue().isEmpty() || !currentCell.getStringCellValue().equals("null"))
				customerModel.setTokenId(currentCell.getStringCellValue());
			break;
		case 17:
			if (!currentCell.getStringCellValue().isEmpty() || !currentCell.getStringCellValue().equals("null"))
				customerModel.setWebToken(currentCell.getStringCellValue());
			break;
		case 18:
			Date creationDate = currentCell.getDateCellValue();
			customerModel.setCreationDate(creationDate);
			break;
		case 19:
			Date modificationDate = currentCell.getDateCellValue();
			customerModel.setModificationDate(modificationDate);
			break;
		default:
			break;
		}
	}

	public void customer(int cellIdx, Cell currentCell, ProfileTemplateModel customerModel) {
		switch (cellIdx) {
		case 18:
			customerModel.setTermsAndConditionsId(currentCell.getStringCellValue());
			break;
		default:
			break;
		}
	}

	public void franchise(int cellIdx, Cell currentCell, ProfileTemplateModel customerModel, Franchise franchise,
			BankAccountModel bank) {
		switch (cellIdx) {
		case 20:
			if (currentCell.getNumericCellValue() != 0.0) {
				customerModel.setStatus(true);
			} else
				customerModel.setStatus(false);
			break;
		case 21:
			if (!currentCell.getStringCellValue().isEmpty())
				customerModel.setPanNumber(currentCell.getStringCellValue());
			break;
		case 22:
			if (currentCell.getNumericCellValue() != 0.0) {
				String franchiseId = NumberToTextConverter.toText(currentCell.getNumericCellValue());
				franchise.setFranchiseId(franchiseId);
			}
			break;
		case 23:
			if (!currentCell.getStringCellValue().isEmpty())
				franchise.setCompanyName(currentCell.getStringCellValue());
			break;
		case 24:
			if (!currentCell.getStringCellValue().isEmpty())
				franchise.setProprietorName(currentCell.getStringCellValue());
			break;
		case 25:
			if (currentCell.getNumericCellValue() != 0.0) {
				String yearOfContract = NumberToTextConverter.toText(currentCell.getNumericCellValue());
				franchise.setYearOfContract(Integer.parseInt(yearOfContract));
			}
			break;
		case 26:
			if (!currentCell.getStringCellValue().isEmpty())
				franchise.setGstNo(currentCell.getStringCellValue());
			break;
		case 27:
			if (!currentCell.getStringCellValue().isEmpty())
				franchise.setLicenseNo(currentCell.getStringCellValue());
			break;
		case 28:
			if (currentCell.getNumericCellValue() != 0.0) {
				double latitude = currentCell.getNumericCellValue();
				franchise.setLatitude(latitude);
			}
		case 29:
			if (currentCell.getNumericCellValue() != 0.0) {
				double longitude = currentCell.getNumericCellValue();
				franchise.setLongitude(longitude);
			}
			break;
		case 30:
			if (currentCell.getNumericCellValue() != 0.0) {
				String organisationType = NumberToTextConverter.toText(currentCell.getNumericCellValue());
				franchise.setOrganisationType(Integer.parseInt(organisationType));
			}
			break;
		case 31:
			if (currentCell.getNumericCellValue() != 0.0) {
				String accountNumber = NumberToTextConverter.toText(currentCell.getNumericCellValue());
				BigInteger accountNo = new BigInteger(accountNumber);
				bank.setAccountNumber(accountNo);
			}
			break;
		case 32:
			if (!currentCell.getStringCellValue().isEmpty())
				bank.setIfscCode(currentCell.getStringCellValue());
			break;
		case 33:
			if (!currentCell.getStringCellValue().isEmpty())
				bank.setBranchName(currentCell.getStringCellValue());
			break;
		case 34:
			if (currentCell.getNumericCellValue() != 0.0) {
				String bankId = NumberToTextConverter.toText(currentCell.getNumericCellValue());
				bank.setBankId(Integer.parseInt(bankId));
			}
			break;
		default:
			break;
		}
	}

	public List<BoardingRequestTemplateModel> covertOnboardDocToList(MultipartFile file) throws Exception {
		BoardingRequestTemplateModel boardingRequestTemplateModel = new BoardingRequestTemplateModel();
		List<BoardingRequestTemplateModel> boardingRequestTemplateModelList = new ArrayList<BoardingRequestTemplateModel>();
		try {
			Workbook workbook = new XSSFWorkbook(file.getInputStream());
			Sheet sheet = workbook.getSheet(workbook.getSheetName(0));
			Row row = null;
			Iterator<Row> rowIterator = sheet.iterator();
			int rowNumber = 0;
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
						if (!currentCell.getStringCellValue().isEmpty())
							boardingRequestTemplateModel.setMobileNumber(currentCell.getStringCellValue());
						break;
					case 1:
						if (!currentCell.getStringCellValue().isEmpty())
							boardingRequestTemplateModel.setName(currentCell.getStringCellValue());
						break;
					case 2:
						if (!currentCell.getStringCellValue().isEmpty())
							boardingRequestTemplateModel.setEmail(currentCell.getStringCellValue());
						break;
					case 3:
						if (!currentCell.getStringCellValue().isEmpty())
							boardingRequestTemplateModel.setMessage(currentCell.getStringCellValue());
						break;
					case 4:
						if (currentCell.getNumericCellValue() != 0.0) {
							String status = NumberToTextConverter.toText(currentCell.getNumericCellValue());
							boardingRequestTemplateModel.setStatus(Integer.parseInt(status));
						} else
							boardingRequestTemplateModel.setStatus(0);
						break;
					case 5:
						if (currentCell.getNumericCellValue() != 0.0) {
							String country = NumberToTextConverter.toText(currentCell.getNumericCellValue());
							boardingRequestTemplateModel.setCountryId(Integer.parseInt(country));
						} else
							boardingRequestTemplateModel.setCountryId(0);
						break;
					case 6:
						if (currentCell.getNumericCellValue() != 0.0) {
							String state = NumberToTextConverter.toText(currentCell.getNumericCellValue());
							boardingRequestTemplateModel.setStateId(Integer.parseInt(state));
						} else
							boardingRequestTemplateModel.setStateId(0);
						break;
					case 7:
						if (currentCell.getNumericCellValue() != 0.0) {
							String city = NumberToTextConverter.toText(currentCell.getNumericCellValue());
							boardingRequestTemplateModel.setCityId(Integer.parseInt(city));
						} else
							boardingRequestTemplateModel.setCityId(0);
						break;
					case 8:
						if (!currentCell.getStringCellValue().isEmpty())
							boardingRequestTemplateModel.setRequestNumber(currentCell.getStringCellValue());
						break;
					case 9:
						String roleId = NumberToTextConverter.toText(currentCell.getNumericCellValue());
						boardingRequestTemplateModel.setRoleId(Integer.parseInt(roleId));
						break;
					case 10:
						if (!currentCell.getStringCellValue().isEmpty())
							boardingRequestTemplateModel.setOnHoldReason(currentCell.getStringCellValue());
						break;
					case 11:
						if (!currentCell.getStringCellValue().isEmpty())
							boardingRequestTemplateModel.setRefferenceCode(currentCell.getStringCellValue());
						break;
					case 12:
						if (!currentCell.getStringCellValue().isEmpty())
							boardingRequestTemplateModel.setCreationDate(currentCell.getStringCellValue());
						break;
					default:
						break;
					}
					cellIdx++;
				}
				BoardingRequestTemplateModel boardingRTemplateModel = new BoardingRequestTemplateModel(
						boardingRequestTemplateModel.getMobileNumber(), boardingRequestTemplateModel.getName(),
						boardingRequestTemplateModel.getEmail(), boardingRequestTemplateModel.getMessage(),
						boardingRequestTemplateModel.getRoleId(), boardingRequestTemplateModel.getCountryId(),
						boardingRequestTemplateModel.getStateId(), boardingRequestTemplateModel.getCityId(),
						boardingRequestTemplateModel.getStatus(), boardingRequestTemplateModel.getRequestNumber(),
						boardingRequestTemplateModel.getCreationDate(), boardingRequestTemplateModel.getOnHoldReason(),
						boardingRequestTemplateModel.getRefferenceCode());
				boardingRequestTemplateModelList.add(boardingRTemplateModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return boardingRequestTemplateModelList;
	}

	public List<ProfileImageTemplateModel> covertDocImagesToList(MultipartFile file, Role role) throws Exception {
		ProfileImageTemplateModel commonImageTemplateModel = new ProfileImageTemplateModel();
		ProfileImageTemplateModel profileImageTemplateModel = null;
		List<ProfileImageTemplateModel> profileImageTemplateModelList = new ArrayList<ProfileImageTemplateModel>();
		try {
			Workbook workbook = new XSSFWorkbook(file.getInputStream());
			Sheet sheet = workbook.getSheet(workbook.getSheetName(0));
			Row row = null;
			Iterator<Row> rowIterator = sheet.iterator();
			int rowNumber = 0;
			while (rowIterator.hasNext()) {
				row = rowIterator.next();
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}
				Iterator<Cell> cellIterator = row.cellIterator();
				int cellIdx = 0;
				System.out.println(cellIdx);
				while (cellIterator.hasNext()) {
					Cell currentCell = cellIterator.next();
					switch (role) {
					case FRANCHISE:
						if (cellIdx >= 0 && cellIdx <= 7)
							commonProfileImage(cellIdx, currentCell, commonImageTemplateModel);
						break;
					case DRIVER:
						break;
					default:
						break;
					}
					cellIdx++;
				}
				if (Role.FRANCHISE.equals(role)) {
					profileImageTemplateModel = new ProfileImageTemplateModel(commonImageTemplateModel.getUuid(),
							commonImageTemplateModel.getName(), commonImageTemplateModel.getType(),
							commonImageTemplateModel.getSize(), commonImageTemplateModel.getPath(),
							commonImageTemplateModel.getCategory(), commonImageTemplateModel.getCreatedDate(),
							commonImageTemplateModel.getFranchiseId());
				}

				profileImageTemplateModelList.add(profileImageTemplateModel);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return profileImageTemplateModelList;
	}

	public void commonProfileImage(int cellIdx, Cell currentCell, ProfileImageTemplateModel profileImageTemplateModel) {
		switch (cellIdx) {
		case 0:
			if (!currentCell.getStringCellValue().isEmpty())
				profileImageTemplateModel.setUuid(currentCell.getStringCellValue());
			break;
		case 1:
			if (!currentCell.getStringCellValue().isEmpty())
				profileImageTemplateModel.setCategory(currentCell.getStringCellValue());
			break;
		case 2:
			if (!currentCell.getStringCellValue().isEmpty())
				profileImageTemplateModel.setName(currentCell.getStringCellValue());
			break;
		case 3:
			if (!currentCell.getStringCellValue().isEmpty())
				profileImageTemplateModel.setType(currentCell.getStringCellValue());
			break;
		case 4:
			if (!currentCell.getStringCellValue().isEmpty())
				profileImageTemplateModel.setPath(currentCell.getStringCellValue());
			break;
		case 5:
			Date createdDate = currentCell.getDateCellValue();
			profileImageTemplateModel.setCreatedDate(createdDate);
			break;
		case 6:
			if (currentCell.getNumericCellValue() != 0.0) {
				String franchiseId = NumberToTextConverter.toText(currentCell.getNumericCellValue());
				profileImageTemplateModel.setFranchiseId(franchiseId);
			}
			break;
		default:
			break;
		}
	}
}
