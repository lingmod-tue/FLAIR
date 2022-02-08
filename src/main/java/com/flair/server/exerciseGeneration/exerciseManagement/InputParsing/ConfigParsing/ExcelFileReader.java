package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public abstract class ExcelFileReader {
		
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
	 * Parses the cell values of an excel file.
	 * @param sheet	The excel file
	 * @param nRows	The number of rows with content
	 * @param nCols	The number of columns with content
	 * @return	The extracted cell values in abstracted exercise config format
	 */
	public ArrayList<ExerciseConfigData> parseFile(XSSFSheet sheet, int nRows, int nCols) {
		return readCellValues(extractCellValues(sheet, nRows, nCols));
	}
	
	/**
	 * Trims whitespaces, including non-breaking spaces.
	 * @param text The text to trim
	 * @return	The trimmed text
	 */
	protected String xTrim(String text) {
		return StringUtils.strip(text.trim(), " ").trim();
	}
	
}
