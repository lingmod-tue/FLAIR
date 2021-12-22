package com.flair.server.exerciseGeneration.InputParsing.ConfigParsing;

import java.util.ArrayList;
import java.util.HashMap;

public class ExcelFileElements {

	public ExcelFileElements(HashMap<Integer, String> columnHeaders, HashMap<String, ArrayList<String>> columnValues) {
		this.columnValues = columnValues;
		this.columnHeaders = columnHeaders;
	}
	
	/**
	 * The column indices and the corresponding column headers
	 */
	private HashMap<Integer, String> columnHeaders = new HashMap<>();
	/**
	 * The values of the columns for all columns headers individually
	 */
	private HashMap<String, ArrayList<String>> columnValues = new HashMap<>();
	
	public HashMap<String, ArrayList<String>> getColumnValues() { return columnValues; }
	public HashMap<Integer, String> getColumnHeaders() { return columnHeaders; }

}
