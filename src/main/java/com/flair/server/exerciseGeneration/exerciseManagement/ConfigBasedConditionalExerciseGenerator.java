package com.flair.server.exerciseGeneration.exerciseManagement;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.flair.server.exerciseGeneration.ExerciseData;
import com.flair.server.exerciseGeneration.OutputComponents;
import com.flair.server.exerciseGeneration.InputEnrichment.FeedbackGenerator;
import com.flair.server.exerciseGeneration.InputParsing.ConfigParsing.ConditionalConfigParser;
import com.flair.server.exerciseGeneration.downloadManagement.ResourceDownloader;
import com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement.ContentTypeSettings;
import com.flair.server.exerciseGeneration.exerciseManagement.feedBookXmlManagement.SimpleExerciseXmlGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.feedBookXmlManagement.XmlGeneratorFactory;
import com.flair.server.exerciseGeneration.exerciseManagement.taskCompilation.NlpManager;
import com.flair.server.parser.CoreNlpParser;
import com.flair.server.parser.OpenNlpParser;
import com.flair.server.parser.SimpleNlgParser;
import com.flair.server.utilities.ServerLogger;
import com.flair.shared.exerciseGeneration.ConfigExerciseSettings;
import com.flair.shared.exerciseGeneration.ExerciseSettings;

public class ConfigBasedConditionalExerciseGenerator extends ConfigBasedExerciseGenerator {

	public static byte[] zipFiles(HashMap<String, ArrayList<HashMap<String, byte[]>>> activities) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
        try {
        	for(Entry<String, ArrayList<HashMap<String, byte[]>>> blocks : activities.entrySet()) {
	        	int i = 0;
	        	for(HashMap<String, byte[]> block : blocks.getValue()) {
	        		for(Entry<String, byte[]> entry : block.entrySet()) {
	                    ZipEntry zipEntry = new ZipEntry(blocks.getKey() + "/" + i + "/" + entry.getKey() + ".xml");
	                    zipOutputStream.putNextEntry(zipEntry);
	                    zipOutputStream.write(entry.getValue());
	                    zipOutputStream.closeEntry();
	            	}
	        		i++;
	        	}
        	}
        } catch (IOException e) {
			ServerLogger.get().error(e, "File could not be zipped. Exception: " + e.toString());
        } finally {
            try {
                zipOutputStream.close();
            } catch (IOException e) {
    			ServerLogger.get().error(e, "Non-fatal error. Exception: " + e.toString());
            }
        }
        
        byte[] outputArray = byteArrayOutputStream.toByteArray();
        try {
			byteArrayOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        return outputArray;
    }
	
	@Override
	public ArrayList<OutputComponents> generateExercise(ContentTypeSettings settings,
		CoreNlpParser parser, SimpleNlgParser generator, OpenNlpParser lemmatizer, ResourceDownloader resourceDownloader) {
		
		ConfigExerciseSettings exerciseSettings = (ConfigExerciseSettings)settings.getExerciseSettings();
		
		ConditionalConfigParser p = new ConditionalConfigParser();
		HashMap<String,ArrayList<HashMap<String,ExerciseData>>> exerciseData = p.parseConfigFile(new ByteArrayInputStream(exerciseSettings.getFileStream()));
				
		HashMap<String, ArrayList<HashMap<String, byte[]>>> activities = new HashMap<>();
        ArrayList<OutputComponents> res = new ArrayList<>();
        
        FeedbackGenerator feedbackGenerator = new FeedbackGenerator();

    	for(Entry<String, ArrayList<HashMap<String, ExerciseData>>> entry : exerciseData.entrySet()) { 
            ArrayList<HashMap<String, byte[]>> blocks = new ArrayList<>();
    		
    		for(HashMap<String, ExerciseData> block : entry.getValue()) {
        		HashMap<String, byte[]> files = new HashMap<>();

    			for(Entry<String, ExerciseData> exercise : block.entrySet()) {
    		        NlpManager nlpManager = new NlpManager(parser, generator, exercise.getValue().getPlainText(), lemmatizer);

    				feedbackGenerator.generateFeedback(settings.getExerciseSettings(), nlpManager, exercise.getValue(), 
    						((ExerciseSettings)settings.getExerciseSettings()).getContentType(), exercise.getValue().getTopic());
    				
        			SimpleExerciseXmlGenerator g = XmlGeneratorFactory.getXmlGenerator(exercise.getKey());
        			byte[] file = g.generateXMLFile(exercise.getValue(), exercise.getKey());
        			files.put(exercise.getKey(), file);
    			}
    			
    			blocks.add(files);
    		}
    		
    		activities.put(entry.getKey(), blocks);
    	}
		
    		// for underline exercises evtl. separate exercise per line
    		//TODO: all support texts?

    	HashMap<String, byte[]> hm = new HashMap<>();
    	hm.put("Generated_exercises", zipFiles(activities));
    	OutputComponents output = new OutputComponents(null, null, null, null, null, null, null, "feedbookExercises", null);
    	output.setZipFiles(hm);
        res.add(output);
        
        return res;
    }
	
	@Override
	public void cancelGeneration() {}
	

}