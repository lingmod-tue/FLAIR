package com.flair.server.exerciseGeneration.exerciseManagement;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.flair.server.exerciseGeneration.OutputComponents;
import com.flair.server.exerciseGeneration.downloadManagement.ResourceDownloader;
import com.flair.server.exerciseGeneration.exerciseManagement.configBasedGeneration.CategorizeJsonComponentGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.configBasedGeneration.ConditionalJsonComponentPreparer;
import com.flair.server.exerciseGeneration.exerciseManagement.configBasedGeneration.FillInTheBlanksJsonComponentGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.configBasedGeneration.FindJsonComponentGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.configBasedGeneration.JsonComponentPreparer;
import com.flair.server.exerciseGeneration.exerciseManagement.configBasedGeneration.JumbledSentencesJsonComponentGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.configBasedGeneration.SingleChoiceJsonComponentGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.ContentTypeSettings;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.OpenNlpParser;
import com.flair.server.parser.SimpleNlgParser;
import com.flair.server.utilities.ServerLogger;
import com.flair.shared.exerciseGeneration.ConfigExerciseSettings;
import com.flair.shared.exerciseGeneration.Pair;

public class ConfigBasedExerciseGenerator extends ExerciseGenerator {
	
    @Override
	public ArrayList<OutputComponents> generateExercise(ContentTypeSettings settings,
			CoreNlpParser parser, SimpleNlgParser generator, OpenNlpParser lemmatizer, ResourceDownloader resourceDownloader) {
        ArrayList<JsonComponents> jsonComponents = prepareExercises(settings, parser, generator, lemmatizer, resourceDownloader);

    	if(jsonComponents != null) {
    		ArrayList<OutputComponents> exercises = new ArrayList<>();

    		for(JsonComponents jc : jsonComponents) {
    			ArrayList<JsonComponents> c = new ArrayList<>();
    			c.add(jc);
    			
    			//TODO: also support H5P exercises
    			/*OutputComponents output = createH5pPackage(jc.getSettings(), c, new ArrayList<>());
    	        if(output != null) {
    	        	output.setXmlFile(writeXmlToFile(output.getFeedBookXml()));
    	        }
	            exercises.add(output);*/
    		}
	        
            return exercises;
	        
    	} else {
    		return null;
    	}
	}
    
    private HashMap<String, byte[]> writeXmlToFile(HashMap<String, String> xml) {
    	if(xml == null) {
    		return null;
    	}
    	
    	HashMap<String, byte[]> files = new HashMap<>();
    	
    	for(Entry<String, String> entry : xml.entrySet()) {
    		files.put(entry.getKey(), entry.getValue().getBytes(StandardCharsets.UTF_8));
    	}
    	return files;
    }

    /**
     * Extracts all exercise components relevant for the JSON configuration from the HTML based on the plain text.
     * @param settings  The content type settings
     * @return          The extracted exercise components relevant for the JSON configuration
     */
    public ArrayList<JsonComponents> prepareExercises(ContentTypeSettings settings, CoreNlpParser parser, SimpleNlgParser generator, 
    		OpenNlpParser lemmatizer, ResourceDownloader resourceDownloader) {
    	ConfigExerciseSettings exerciseSettings = (ConfigExerciseSettings)settings.getExerciseSettings();
    	ArrayList<ExerciseConfigData> configData = readExcelFile(new ByteArrayInputStream(exerciseSettings.getFileStream()));
    	
    	HashMap<Integer, ArrayList<ExerciseConfigData>> configs = new HashMap<>();
    	for(ExerciseConfigData cd : configData) {
    		if(!configs.containsKey(cd.getActivity())) {
    			configs.put(cd.getActivity(), new ArrayList<>());
    		}
    		configs.get(cd.getActivity()).add(cd);
    	}
    	
		ArrayList<JsonComponents> jsonConfigs = new ArrayList<>();
    	for(Entry<Integer, ArrayList<ExerciseConfigData>> entry : configs.entrySet()) {    		
    		JsonComponentPreparer preparer = new JsonComponentPreparer(entry.getValue());
    		
    		if(((ConfigExerciseSettings)settings.getExerciseSettings()).getTopic().equals("Past")) {
    			jsonConfigs.add((new FillInTheBlanksJsonComponentGenerator(true, false)).generateJsonComponents(entry.getValue(), preparer, (ConfigExerciseSettings)settings.getExerciseSettings()));
        		jsonConfigs.add((new FillInTheBlanksJsonComponentGenerator(true, true)).generateJsonComponents(entry.getValue(), preparer, (ConfigExerciseSettings)settings.getExerciseSettings()));
        		jsonConfigs.add((new FillInTheBlanksJsonComponentGenerator(false, true)).generateJsonComponents(entry.getValue(), preparer, (ConfigExerciseSettings)settings.getExerciseSettings()));
        		jsonConfigs.add((new SingleChoiceJsonComponentGenerator(1)).generateJsonComponents(entry.getValue(), preparer, (ConfigExerciseSettings)settings.getExerciseSettings()));
        		jsonConfigs.add((new SingleChoiceJsonComponentGenerator(3)).generateJsonComponents(entry.getValue(), preparer, (ConfigExerciseSettings)settings.getExerciseSettings()));
        		jsonConfigs.add((new JumbledSentencesJsonComponentGenerator()).generateJsonComponents(entry.getValue(), preparer, (ConfigExerciseSettings)settings.getExerciseSettings()));
        		jsonConfigs.add((new FindJsonComponentGenerator()).generateJsonComponents(entry.getValue(), preparer, (ConfigExerciseSettings)settings.getExerciseSettings()));
    		} 
    		// for underline exercises evtl. separate exercise per line
    		// add support field in xml
    		//TODO: instructions per stamp-activity type combination?
    		//TODO: all support texts?
    		
    		
    	}

    	return jsonConfigs;
    }

	private ArrayList<ExerciseConfigData> readExcelFile(InputStream inputStream) {
		try (XSSFWorkbook wb = new XSSFWorkbook(inputStream)) {
			XSSFSheet sheet = wb.getSheetAt(0);
			XSSFRow row;
			XSSFCell cell;

			int rows; // # rows
			rows = sheet.getPhysicalNumberOfRows();

			int cols = 0; // # columns
			int tmp = 0;

			// This trick ensures that we get the data properly even if it doesn't start from first few rows
			for(int i = 0; i < 10 || i < rows; i++) {
			    row = sheet.getRow(i);
			    if(row != null) {
			        tmp = sheet.getRow(i).getPhysicalNumberOfCells();
			        if(tmp > cols) cols = tmp;
			    }
			}

			HashMap<String, ArrayList<String>> columnValues = new HashMap<>();
			HashMap<Integer, String> columnHeaders = new HashMap<>();

			int counter = 0;
			int r = 0;
			while(counter < rows) {
			    row = sheet.getRow(r);
			    if(row != null) {    		    	
			        for(int c = 0; c < cols; c++) {
			            cell = row.getCell((short)c);
			            if(cell != null && !cell.toString().isEmpty()) {
			            	if(r == 0) {
				        		// initialize the hash map with the column headers
				        		columnValues.put(cell.toString(), new ArrayList<String>());
				        		columnHeaders.put(c, cell.toString());
				        	} else {
				        		ArrayList<String> column = columnValues.get(columnHeaders.get(c));
				        		column.add(cell.toString());
				        	}
			            } else {
			            	if(r != 0 && sheet.getRow(0).getCell((short)c) != null && row.getCell(0) != null) {
			            		ArrayList<String> column = columnValues.get(columnHeaders.get(c));
				        		column.add("");
			            	}
			            }
			        }
			        
			        counter++;
			    }
			    r++;
			}
				    	
			ArrayList<ExerciseConfigData> configData = new ArrayList<>();
			boolean isFirstCol = true;
			for(Entry<Integer, String> entry : columnHeaders.entrySet()) {	// columns
				for(int i = 0; i < columnValues.get(entry.getValue()).size(); i++) {	// rows
					if(isFirstCol) {
						// it's a new activity
						configData.add(new ExerciseConfigData());
					}
					if(entry.getValue().trim().equals("Aufgabennr.")) {
						configData.get(i).setActivity((int)Float.parseFloat(columnValues.get(entry.getValue()).get(i).trim()));
			    	} else if(entry.getValue().trim().equals("stamp")) {
						configData.get(i).setStamp(columnValues.get(entry.getValue()).get(i));
			    	} else if(entry.getValue().trim().equals("item")) {
						configData.get(i).setItem((int)Float.parseFloat(columnValues.get(entry.getValue()).get(i)));
			    	} else if(entry.getValue().trim().equals("gap")) {
			    		String value = columnValues.get(entry.getValue()).get(i);
			    		int startIndex = 0; 
			    		int endIndex = 0;

			    		try {
				    		if(value.contains("-")) {
				    			String[] parts = value.split("-");
				    			if(parts.length == 2) {
				    				startIndex = (int)Float.parseFloat(parts[0].trim());
				    				endIndex = (int)Float.parseFloat(parts[1].trim());
				    			}
				    		} else {
				    			startIndex = (int)Float.parseFloat(value.trim());
				    			endIndex = startIndex;
				    		}
			    		} catch(Exception e) { }
			    		
			    		if(startIndex != 0 && endIndex != 0) {
			    			configData.get(i).setGap(new Pair<>(startIndex, endIndex));
			    		}
			    	} else if(entry.getValue().trim().equals("infinitive")) {
			    		configData.get(i).setLemma(columnValues.get(entry.getValue()).get(i));
			    	} else if(entry.getValue().trim().equals("alternative")) {
			    		configData.get(i).setDistractorLemma(columnValues.get(entry.getValue()).get(i));
			    	} else if(entry.getValue().trim().startsWith("position")) {
			    		configData.get(i).getPositions().add(new Pair<>((int)Float.parseFloat(entry.getValue().trim().substring(9).trim()), columnValues.get(entry.getValue()).get(i)));
			    	} else if(entry.getValue().trim().startsWith("distractor")) {
			    		configData.get(i).getDistractors().add(columnValues.get(entry.getValue()).get(i));
			    	}
				}	
				isFirstCol = false;
			}
			
			return configData;
		} catch(Exception e) { 
			ServerLogger.get().error("Excel config file could not be read.");
		}
		
		return null;
	}

	@Override
	public void cancelGeneration() {}
	
	public class ExerciseConfigData {
								
		private String stamp;
		private int item;
		private int activity;
		private ArrayList<Pair<Integer, String>> positions = new ArrayList<>();
		private Pair<Integer, Integer> gap;
		private ArrayList<String> distractors = new ArrayList<>();
		private String lemma;
		private String distractorLemma;
		private int conditionalType;
		private String ifClause;
		private String mainClause;
		private String translationIfClause;
		private String translationMainClause;
		private ArrayList<Pair<Integer, String>> distractorsIfClause = new ArrayList<>();
		private ArrayList<Pair<Integer, String>> distractorsMainClause = new ArrayList<>();
		private String lemmaIfClause;
		private String lemmaMainClause;
		private String distractorLemmaIfClause;
		private String distractorLemmaMainClause;
		private ArrayList<String> bracketsIfClause = new ArrayList<>();
		private ArrayList<String> bracketsMainClause = new ArrayList<>();
		private ArrayList<Pair<Integer, Integer>> gapIfClause = new ArrayList<>();
		private ArrayList<Pair<Integer, Integer>> gapMainClause = new ArrayList<>();
		private ArrayList<Pair<Integer, Integer>> underlineIfClause = new ArrayList<>();
		private ArrayList<Pair<Integer, Integer>> underlineMainClause = new ArrayList<>();
		private ArrayList<Pair<Integer, String>> positionsIfClause = new ArrayList<>();
		private ArrayList<Pair<Integer, String>> positionsMainClause = new ArrayList<>();
		private boolean isType1VsType2;

		
		public boolean isType1VsType2() {
			return isType1VsType2;
		}
		public void setType1VsType2(boolean isType1VsType2) {
			this.isType1VsType2 = isType1VsType2;
		}
		public String getStamp() { return stamp; }
		public int getItem() { return item; }
		public int getActivity() { return activity; }
		public ArrayList<Pair<Integer, String>> getPositions() { return positions; }
		public Pair<Integer, Integer> getGap() { return gap; }
		public ArrayList<String> getDistractors() { return distractors; }
		public String getLemma() { return lemma; }
		public String getDistractorLemma() { return distractorLemma; }
		public void setStamp(String stamp) {
			this.stamp = stamp;
		}
		public void setItem(int item) {
			this.item = item;
		}
		public void setActivity(int activity) {
			this.activity = activity;
		}
		public void setPositions(ArrayList<Pair<Integer, String>> positions) {
			this.positions = positions;
		}
		public void setGap(Pair<Integer, Integer> gap) {
			this.gap = gap;
		}
		public void setDistractors(ArrayList<String> distractors) {
			this.distractors = distractors;
		}
		public void setLemma(String lemma) {
			this.lemma = lemma;
		}
		public void setDistractorLemma(String distractorLemma) {
			this.distractorLemma = distractorLemma;
		}
		public int getConditionalType() {
			return conditionalType;
		}
		public void setConditionalType(int conditionalType) {
			this.conditionalType = conditionalType;
		}
		public String getIfClause() {
			return ifClause;
		}
		public void setIfClause(String ifClause) {
			this.ifClause = ifClause;
		}
		public String getMainClause() {
			return mainClause;
		}
		public void setMainClause(String mainClause) {
			this.mainClause = mainClause;
		}
		public String getTranslationIfClause() {
			return translationIfClause;
		}
		public void setTranslationIfClause(String translationIfClause) {
			this.translationIfClause = translationIfClause;
		}
		public String getTranslationMainClause() {
			return translationMainClause;
		}
		public void setTranslationMainClause(String translationMainClause) {
			this.translationMainClause = translationMainClause;
		}
		public ArrayList<Pair<Integer, String>> getDistractorsIfClause() {
			return distractorsIfClause;
		}
		public void setDistractorsIfClause(ArrayList<Pair<Integer, String>> distractorsIfClause) {
			this.distractorsIfClause = distractorsIfClause;
		}
		public ArrayList<Pair<Integer, String>> getDistractorsMainClause() {
			return distractorsMainClause;
		}
		public void setDistractorsMainClause(ArrayList<Pair<Integer, String>> distractorsMainClause) {
			this.distractorsMainClause = distractorsMainClause;
		}
		public String getLemmaIfClause() {
			return lemmaIfClause;
		}
		public void setLemmaIfClause(String lemmaIfClause) {
			this.lemmaIfClause = lemmaIfClause;
		}
		public String getLemmaMainClause() {
			return lemmaMainClause;
		}
		public void setLemmaMainClause(String lemmaMainClause) {
			this.lemmaMainClause = lemmaMainClause;
		}
		public String getDistractorLemmaIfClause() {
			return distractorLemmaIfClause;
		}
		public void setDistractorLemmaIfClause(String distractorLemmaIfClause) {
			this.distractorLemmaIfClause = distractorLemmaIfClause;
		}
		public String getDistractorLemmaMainClause() {
			return distractorLemmaMainClause;
		}
		public void setDistractorLemmaMainClause(String distractorLemmaMainClause) {
			this.distractorLemmaMainClause = distractorLemmaMainClause;
		}
		public ArrayList<String> getBracketsIfClause() {
			return bracketsIfClause;
		}
		public void setBracketsIfClause(ArrayList<String> bracketsIfClause) {
			this.bracketsIfClause = bracketsIfClause;
		}
		public ArrayList<String> getBracketsMainClause() {
			return bracketsMainClause;
		}
		public void setBracketsMainClause(ArrayList<String> bracketsMainClause) {
			this.bracketsMainClause = bracketsMainClause;
		}
		public ArrayList<Pair<Integer, Integer>> getGapIfClause() {
			return gapIfClause;
		}
		public void setGapIfClause(ArrayList<Pair<Integer, Integer>> gapIfClause) {
			this.gapIfClause = gapIfClause;
		}
		public ArrayList<Pair<Integer, Integer>> getGapMainClause() {
			return gapMainClause;
		}
		public void setGapMainClause(ArrayList<Pair<Integer, Integer>> gapMainClause) {
			this.gapMainClause = gapMainClause;
		}
		public ArrayList<Pair<Integer, Integer>> getUnderlineIfClause() {
			return underlineIfClause;
		}
		public void setUnderlineIfClause(ArrayList<Pair<Integer, Integer>> underlineIfClause) {
			this.underlineIfClause = underlineIfClause;
		}
		public ArrayList<Pair<Integer, Integer>> getUnderlineMainClause() {
			return underlineMainClause;
		}
		public void setUnderlineMainClause(ArrayList<Pair<Integer, Integer>> underlineMainClause) {
			this.underlineMainClause = underlineMainClause;
		}
		public ArrayList<Pair<Integer, String>> getPositionsIfClause() {
			return positionsIfClause;
		}
		public void setPositionsIfClause(ArrayList<Pair<Integer, String>> positionsIfClause) {
			this.positionsIfClause = positionsIfClause;
		}
		public ArrayList<Pair<Integer, String>> getPositionsMainClause() {
			return positionsMainClause;
		}
		public void setPositionsMainClause(ArrayList<Pair<Integer, String>> positionsMainClause) {
			this.positionsMainClause = positionsMainClause;
		}
								
	}

}