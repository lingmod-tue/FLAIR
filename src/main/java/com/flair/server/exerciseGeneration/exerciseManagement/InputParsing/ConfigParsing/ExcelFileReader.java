package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.flair.server.utilities.ServerLogger;

public abstract class ExcelFileReader {
	
	/**
	 * Extracts the data from an excel file.
	 * @param inputStream	The file input stream
	 * @return	The extracted cell values converted into config data per row
	 */
	public ArrayList<ExerciseConfigData> readExcelFile(InputStream inputStream) {
		try (XSSFWorkbook wb = new XSSFWorkbook(inputStream)) {
			XSSFSheet sheet = wb.getSheetAt(0);
			XSSFRow row;

			int rows = sheet.getPhysicalNumberOfRows();
			int cols = 0; 
			int tmp = 0;

			for(int i = 0; i < 10 || i < rows; i++) {
			    row = sheet.getRow(i);
			    if(row != null) {
			        tmp = sheet.getRow(i).getPhysicalNumberOfCells();
			        if(tmp > cols) cols = tmp;
			    }
			}
			
			return readCellValues(extractCellValues(sheet, rows, cols));
		} catch (IOException e) {
			ServerLogger.get().error("Excel config file could not be read.");
			return null;
		}
	}
	
	/**
	 * Extracts the cell values from an excel file.
	 * @param sheet	The excel file
	 * @param nRows	The number of rows with content
	 * @param nCols	The number of columns with content
	 * @return	The cell values per column and a mapping of the column names to the column indices
	 */
	protected abstract ExcelFileElements extractCellValues(XSSFSheet sheet, int nRows, int nCols);
	
	/**
	 * Converts the cell value data into a per-row representation
	 * @param excelElements	The cell values per column and a mapping of the column names to the column indices
	 * @return	The cell values in a per-row representation
	 */
	protected abstract ArrayList<ExerciseConfigData> readCellValues(ExcelFileElements excelElements);
	
	/**
	 * Trims whitespaces, including non-breaking spaces.
	 * @param text The text to trim
	 * @return	The trimmed text
	 */
	protected String xTrim(String text) {
		return StringUtils.strip(text.trim(), " ").trim();
	}
	
}
