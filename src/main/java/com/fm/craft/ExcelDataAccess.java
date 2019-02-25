package com.fm.craft;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelDataAccess {
	private String filePath;
	private String fileName;
	private String datasheetName;

	public String getDatasheetName() {
		/* 31 */return this.datasheetName;
	}

	public void setDatasheetName(String datasheetName) {
		/* 39 */this.datasheetName = datasheetName;
	}

	public ExcelDataAccess(String filePath, String fileName) {
		/* 50 */this.filePath = filePath;
		/* 51 */this.fileName = fileName;
	}

	private void checkPreRequisites() {
		/* 56 */if (this.datasheetName == null)
			/* 57 */throw new FrameworkException(
					"ExcelDataAccess.datasheetName is not set!");
	}

	private HSSFWorkbook openFileForReading() {
		/* 63 */FrameworkParameters frameworkParameters = FrameworkParameters
				.getInstance();
		/* 64 */String absoluteFilePath = this.filePath
				+ frameworkParameters.fileSeparator + this.fileName + ".xls";
		FileInputStream fileInputStream;
		HSSFWorkbook workbook;
		try {
			/* 69 */fileInputStream = new FileInputStream(absoluteFilePath);
		} catch (FileNotFoundException e) {

			/* 73 */e.printStackTrace();
			/* 74 */throw new FrameworkException("The specified file \""
					+ absoluteFilePath + "\" does not exist!");
		}
		try {

			/* 79 */workbook = new HSSFWorkbook(fileInputStream);
		} catch (IOException e) {

			/* 82 */e.printStackTrace();
			/* 83 */throw new FrameworkException(
					"Error while opening the specified Excel workbook \""
							+ absoluteFilePath + "\"");
		}

		/* 86 */return workbook;
	}

	private void writeIntoFile(HSSFWorkbook workbook) {
		/* 91 */FrameworkParameters frameworkParameters = FrameworkParameters
				.getInstance();
		/* 92 */String absoluteFilePath = this.filePath
				+ frameworkParameters.fileSeparator + this.fileName + ".xls";
		FileOutputStream fileOutputStream;
		try {
			/* 97 */fileOutputStream = new FileOutputStream(absoluteFilePath);
		} catch (FileNotFoundException e) {

			/* 101 */e.printStackTrace();
			/* 102 */throw new FrameworkException("The specified file \""
					+ absoluteFilePath + "\" does not exist!");
		}
		try {
			workbook.write(fileOutputStream);
			/* 107 */fileOutputStream.close();
		} catch (IOException e) {
			/* 109 */e.printStackTrace();
			/* 110 */throw new FrameworkException(
					"Error while writing into the specified Excel workbook \""
							+ absoluteFilePath + "\"");
		}
	}

	private HSSFSheet getWorkSheet(HSSFWorkbook workbook) {
		/* 116 */HSSFSheet worksheet = workbook.getSheet(this.datasheetName);
		/* 117 */if (worksheet == null) {
			/* 118 */throw new FrameworkException("The specified sheet \""
					+ this.datasheetName
					+ "\" does not exist within the workbook \""
					+ this.fileName + ".xls\"");
		}

		/* 121 */return worksheet;
	}

	@SuppressWarnings("deprecation")
	private String getCellValue(HSSFSheet worksheet, int rowNum, int columnNum) {
		/* 126 */HSSFRow row = worksheet.getRow(rowNum);
		/* 127 */HSSFCell cell = row.getCell((short) columnNum);

		String cellValue;
		/* 130 */if (cell == null)
			/* 131 */cellValue = "";
		else {
			/* 133 */cellValue = cell.getStringCellValue().trim();
		}
		/* 135 */return cellValue;
	}

	@SuppressWarnings("deprecation")
	private String getCellValue(HSSFSheet worksheet, HSSFRow row, int columnNum) {
		/* 140 */HSSFCell cell = row.getCell((short) columnNum);
		String cellValue;

		/* 143 */if (cell == null)
			/* 144 */cellValue = "";
		else {
			/* 146 */cellValue = cell.getStringCellValue().trim();
		}
		/* 148 */return cellValue;
	}

	public int getRowNum(String key, int columnNum, int startRowNum) {
		/* 160 */checkPreRequisites();

		/* 162 */HSSFWorkbook workbook = openFileForReading();
		/* 163 */HSSFSheet worksheet = getWorkSheet(workbook);

		/* 166 */int currentRowNum = startRowNum;
		/* 167 */for (; currentRowNum <= worksheet.getLastRowNum(); currentRowNum++) {
			/* 169 */String currentValue = getCellValue(worksheet,
					currentRowNum, columnNum);

			/* 171 */if (currentValue.equals(key)) {
				/* 172 */return currentRowNum;
			}
		}

		/* 176 */return -1;
	}

	public int getRowNum(String key, int columnNum) {
		/* 187 */return getRowNum(key, columnNum, 0);
	}

	public int getLastRowNum() {
		/* 196 */checkPreRequisites();

		/* 198 */HSSFWorkbook workbook = openFileForReading();
		/* 199 */HSSFSheet worksheet = getWorkSheet(workbook);

		/* 201 */return worksheet.getLastRowNum();
	}

	public int getRowCount(String key, int columnNum, int startRowNum) {
		/* 213 */checkPreRequisites();

		/* 215 */HSSFWorkbook workbook = openFileForReading();
		/* 216 */HSSFSheet worksheet = getWorkSheet(workbook);

		/* 218 */int rowCount = 0;
		/* 219 */Boolean keyFound = Boolean.valueOf(false);

		/* 222 */int currentRowNum = startRowNum;
		/* 223 */for (; currentRowNum <= worksheet.getLastRowNum(); currentRowNum++) {
			/* 225 */String currentValue = getCellValue(worksheet,
					currentRowNum, columnNum);

			/* 227 */if (currentValue.equals(key)) {
				/* 228 */rowCount++;
				/* 229 */keyFound = Boolean.valueOf(true);
			} else {
				/* 231 */if (keyFound.booleanValue()) {
					break;
				}
			}
		}
		/* 237 */return rowCount;
	}

	public int getRowCount(String key, int columnNum) {
		/* 248 */return getRowCount(key, columnNum, 0);
	}

	public int getColumnNum(String key, int rowNum) {
		/* 259 */checkPreRequisites();

		/* 261 */HSSFWorkbook workbook = openFileForReading();
		/* 262 */HSSFSheet worksheet = getWorkSheet(workbook);

		/* 264 */HSSFRow row = worksheet.getRow(rowNum);

		/* 266 */int currentColumnNum = 0;
		/* 267 */for (; currentColumnNum < row.getLastCellNum(); currentColumnNum++) {
			/* 269 */String currentValue = getCellValue(worksheet, row,
					currentColumnNum);

			/* 271 */if (currentValue.equals(key)) {
				/* 272 */return currentColumnNum;
			}
		}

		/* 276 */return -1;
	}

	public String getValue(int rowNum, int columnNum) {
		/* 287 */checkPreRequisites();

		/* 289 */HSSFWorkbook workbook = openFileForReading();
		/* 290 */HSSFSheet worksheet = getWorkSheet(workbook);

		/* 292 */String cellValue = getCellValue(worksheet, rowNum, columnNum);

		/* 294 */return cellValue;
	}

	public String getValue(int rowNum, String columnHeader) {
		/* 305 */checkPreRequisites();

		/* 307 */HSSFWorkbook workbook = openFileForReading();
		/* 308 */HSSFSheet worksheet = getWorkSheet(workbook);

		/* 310 */HSSFRow row = worksheet.getRow(0);
		/* 311 */int columnNum = -1;

		/* 313 */int currentColumnNum = 0;
		/* 314 */for (; currentColumnNum < row.getLastCellNum(); currentColumnNum++) {
			/* 316 */String currentValue = getCellValue(worksheet, row,
					currentColumnNum);

			/* 318 */if (currentValue.equals(columnHeader)) {
				/* 319 */columnNum = currentColumnNum;
				/* 320 */break;
			}
		}

		/* 324 */if (columnNum == -1) {
			/* 325 */throw new FrameworkException(
					"The specified column header \"" + columnHeader
							+ "\" is not found in the sheet \""
							+ this.datasheetName + "\"!");
		}
		/* 327 */String cellValue = getCellValue(worksheet, rowNum, columnNum);
		/* 328 */return cellValue;
	}

	@SuppressWarnings("deprecation")
	public void setValue(int rowNum, int columnNum, String value) {
		/* 340 */checkPreRequisites();

		/* 342 */HSSFWorkbook workbook = openFileForReading();
		/* 343 */HSSFSheet worksheet = getWorkSheet(workbook);

		/* 345 */HSSFRow row = worksheet.getRow(rowNum);
		/* 346 */HSSFCell cell = row.createCell((short) columnNum);
		/* 347 */cell.setCellType(1);
		/* 348 */cell.setCellValue(value);

		/* 350 */writeIntoFile(workbook);
	}

	@SuppressWarnings("deprecation")
	public void setValue(int rowNum, String columnHeader, String value) {
		/* 361 */checkPreRequisites();

		/* 363 */HSSFWorkbook workbook = openFileForReading();
		/* 364 */HSSFSheet worksheet = getWorkSheet(workbook);

		/* 366 */HSSFRow row = worksheet.getRow(0);
		/* 367 */int columnNum = -1;

		/* 369 */int currentColumnNum = 0;
		/* 370 */for (; currentColumnNum < row.getLastCellNum(); currentColumnNum++) {
			/* 372 */String currentValue = getCellValue(worksheet, row,
					currentColumnNum);

			/* 374 */if (currentValue.equals(columnHeader)) {
				/* 375 */columnNum = currentColumnNum;
				/* 376 */break;
			}
		}

		/* 380 */if (columnNum == -1) {
			/* 381 */throw new FrameworkException(
					"The specified column header " + columnHeader
							+ " is not found in the sheet \""
							+ this.datasheetName + "\"!");
		}
		/* 383 */row = worksheet.getRow(rowNum);
		/* 384 */HSSFCell cell = row.createCell((short) columnNum);
		/* 385 */cell.setCellType(1);
		/* 386 */cell.setCellValue(value);

		/* 388 */writeIntoFile(workbook);
	}

	public void createWorkbook() {
		/* 397 */HSSFWorkbook workbook = new HSSFWorkbook();

		/* 399 */writeIntoFile(workbook);
	}

	public void addSheet(String sheetName) {
		/* 408 */HSSFWorkbook workbook = new HSSFWorkbook();
		/* 409 */HSSFSheet worksheet = workbook.createSheet(sheetName);
		/* 410 */worksheet.createRow(0);

		/* 412 */writeIntoFile(workbook);

		/* 414 */setDatasheetName(sheetName);
	}

	public int addRow() {
		/* 423 */checkPreRequisites();

		/* 425 */HSSFWorkbook workbook = openFileForReading();
		/* 426 */HSSFSheet worksheet = getWorkSheet(workbook);

		/* 428 */int newRowNum = worksheet.getLastRowNum() + 1;
		/* 429 */worksheet.createRow(newRowNum);

		/* 431 */writeIntoFile(workbook);

		/* 433 */return newRowNum;
	}

	@SuppressWarnings("deprecation")
	public void addColumn(String columnHeader) {
		/* 442 */checkPreRequisites();

		/* 444 */HSSFWorkbook workbook = openFileForReading();
		/* 445 */HSSFSheet worksheet = getWorkSheet(workbook);

		/* 447 */HSSFRow row = worksheet.getRow(0);
		/* 448 */int lastCellNum = row.getLastCellNum();
		/* 449 */if (lastCellNum == -1) {
			/* 450 */lastCellNum = 0;
		}
		/* 452 */HSSFCell cell = row.createCell((short) lastCellNum);
		/* 453 */cell.setCellType(1);
		/* 454 */cell.setCellValue(columnHeader);

		/* 456 */writeIntoFile(workbook);
	}
}
