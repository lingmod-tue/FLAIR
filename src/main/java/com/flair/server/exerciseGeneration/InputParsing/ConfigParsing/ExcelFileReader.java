package com.flair.server.exerciseGeneration.InputParsing.ConfigParsing;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.flair.server.utilities.ServerLogger;

public abstract class ExcelFileReader {
	
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
	
	protected abstract ExcelFileElements extractCellValues(XSSFSheet sheet, int nRows, int nCols);
	protected abstract ArrayList<ExerciseConfigData> readCellValues(ExcelFileElements excelElements);
	
}
