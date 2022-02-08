package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.flair.server.utilities.ServerLogger;

public class ExcelFileManager {
	
	/**
	 * Extracts the data from an excel file.
	 * @param inputStream	The file input stream
	 * @return	The extracted cell values converted into config data per row
	 */
	public ArrayList<ExerciseConfigData> readExcelFile(InputStream inputStream, String topic) {
		try (XSSFWorkbook wb = new XSSFWorkbook(inputStream)) {
			ArrayList<ExerciseConfigData> configData = new ArrayList<>();
			for(int n = 0; n < wb.getNumberOfSheets(); n++) {
				XSSFSheet sheet = wb.getSheetAt(n);
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
				
				configData.addAll(ExcelFileReaderFactory.getReader(topic, n).parseFile(sheet, rows, cols));
			}
			return configData;
		} catch (IOException e) {
			ServerLogger.get().error("Excel config file could not be read.");
			return null;
		}
	}

}
