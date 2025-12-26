package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelManagementUtilities {

	public static Object[][] getExcelSheetData(String filePath, String sheetName)
			throws EncryptedDocumentException, IOException {

		// Read the Excel File from filePath and create a Workbook object
		File file = new File(filePath);
		FileInputStream fileInputStream = new FileInputStream(file);
		Workbook workbook = WorkbookFactory.create(fileInputStream);
		Sheet sheet = workbook.getSheet(sheetName);

		// Get the number of rows and columns in the Excel sheet
		int rowCount = sheet.getLastRowNum();
		int columnCount = sheet.getRow(0).getLastCellNum();
		Object[][] sheetData = new Object[rowCount][columnCount];

		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				Cell cell = sheet.getRow(rowIndex + 1).getCell(columnIndex);
				if (cell != null) {
					sheetData[rowIndex][columnIndex] = cell.getStringCellValue();
				} else {
					sheetData[rowIndex][columnIndex] = "";
				}
			}
		}
		return sheetData;
	}
}
