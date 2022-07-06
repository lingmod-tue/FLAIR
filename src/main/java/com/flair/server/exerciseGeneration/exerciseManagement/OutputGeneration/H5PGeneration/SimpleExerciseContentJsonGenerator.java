package com.flair.server.exerciseGeneration.exerciseManagement.OutputGeneration.H5PGeneration;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.flair.server.exerciseGeneration.exerciseManagement.ConstructionTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.Distractor;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.HtmlTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.PlainTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.TextPart;

public abstract class SimpleExerciseContentJsonGenerator extends ContentJsonGenerator {

    /**
     * Counter for exercises so that they get a unique id.
     * Necessary for Advanced FiB exercises within a quiz.
     */
    protected static int internalId = 0;
    
    @Override
    public ArrayList<JSONObject> modifyJsonContent(ArrayList<ExerciseData> exerciseDefinition) {
        internalId++;

        JSONObject jsonObject = getContentJson(exerciseDefinition.get(0).getExerciseType());
        if(jsonObject == null) {
        	return null;
        }

        String instructions = exerciseDefinition.get(0).getInstructions();
    	if(exerciseDefinition.get(0).getInstructionLemmas() != null && exerciseDefinition.get(0).getInstructionLemmas().size() > 0) {
        	instructions += "</br><em>" + String.join("</em>, <em>", exerciseDefinition.get(0).getInstructionLemmas()) + "</em>";
    	}
        jsonObject.put("taskDescription", instructions);

        addTextPartsToJson(jsonObject, exerciseDefinition.get(0));
        
        setExerciseSpecificAttributes(jsonObject);
                
        ArrayList<JSONObject> ret = new ArrayList<>();
        ret.add(jsonObject);
        return ret;
    }

    /**
     * Replaces single asterisks in a string with double asterisks.
     * @param textToEscape	The strings in which asterisks are to be escaped
     */
    public String escapeAsterisks(String textToEscape) {
    	return textToEscape.replace("*", "**");
    }

    /**
     * Adds the extracted plain text which is modifyable in the authoring tool to the JSON object.
     * @param jsonObject 		The JSON object
     * @param plainTextElements	The extracted plain text
     * @param constructions 	The extracted constructions
     */
    protected void addTextPartsToJson(JSONObject jsonObject, ExerciseData data) {
		StringBuilder sb = new StringBuilder();
		JSONArray htmlArray = new JSONArray();        
		int feedbackId = 1;
		boolean previousWasPlaintext = false;
		int questionId = 1;
		StringBuilder questionBuilder = new StringBuilder();
		int previousSentenceId = 0;
		
		for(TextPart part : data.getParts()) {
			if((part instanceof HtmlTextPart || previousSentenceId != part.getSentenceId()) && 
					previousWasPlaintext && questionBuilder.length() > 0) {
		        String plainText = "<span data-internal=\"" + questionId + "\">" + questionBuilder.toString() + "</span>";
				sb.append(plainText);
				questionBuilder = new StringBuilder();
				htmlArray.add(questionId + " " + "sentenceHtml " + part.getSentenceId());
				questionId++;
			}
			
			if(part instanceof ConstructionTextPart) {
				String blankElement = getPlacehholderReplacement(((ConstructionTextPart)part).getValue().replace("*", "**"), 
						((ConstructionTextPart)part).getDistractors(), "feedback" + feedbackId, jsonObject, ((ConstructionTextPart)part).getBrackets());
				questionBuilder.append(blankElement);
				feedbackId++;
				previousWasPlaintext = true;
			} else if(part instanceof PlainTextPart) {
				questionBuilder.append(part.getValue().replace("*", "**"));	// escape asterisks used to designate blanks
				previousWasPlaintext = true;
			} else {
				htmlArray.add("sentenceHtml " + part.getSentenceId() + " " + prepareHtmlElement(part.getValue(), data));
		        previousWasPlaintext = false;
			}
		}
		
		if(previousWasPlaintext && questionBuilder.length() > 0) {
	        String plainText = "<span data-internal=\"" + questionId + "\">" + questionBuilder.toString() + "</span>";
			sb.append(plainText);
			htmlArray.add(questionId + " " + "sentenceHtml " + previousSentenceId);
		}
		
		String plainText = sb.toString();
		jsonObject.put("textField", plainText);
		jsonObject.put("htmlElements", htmlArray);		
	}
    
	/**
	 * Replaces all HTML symbols which may not be used if we don't want H5P to
	 * recognize it as HTML.
	 * 
	 * @param htmlString The string to normalize
	 * @return The normalized string
	 */
	private String prepareHtmlElement(String htmlString, ExerciseData data) {
		htmlString = htmlString.replace("<", "ltRep;").replace("\"", "quotRep;").replace(">", "gtRep;").replace("'", "#039Rep;")
				.replace("&", "ampRep;");
		
        return escapeAsterisksInHtml(htmlString);
	}
	
	protected String escapeAsterisksInHtml(String htmlString) {
		return htmlString.replace("*", "**");
	}

    /**
     * Allows to set other attributes in the JSON file particular to a specific content type.
     * @param jsonObject	The JSON object
     */
    protected void setExerciseSpecificAttributes(JSONObject jsonObject) {}
    
    /**
     * Puts together the string for an inline blank definition.
     * @param construction  The correct solution
     * @return              The blank definition
     */
    protected String getPlacehholderReplacement(String construction, ArrayList<Distractor> distractorList, 
    		String feedbackId, JSONObject jsonObject, ArrayList<String> brackets) {
        return "*" + construction + "*";
    }
    
    /**
     * Adds feedback elements per blank to JSON.
     * @param jsonObject	The JSON object
     * @param feedbackId	The feedback id referenced in the inline specification
     * @param distractors	The distractors of the current blank
     * @return				<code>true</code> if any feedback elements were added; otherwise <code>false</code>
     */
    protected boolean addFeedbackToJson(JSONObject jsonObject, String feedbackId, ArrayList<Distractor> distractors) {
        JSONArray incorrectAnswers = new JSONArray();
        for(int i = 0; i < distractors.size(); i++) {
        	Distractor distractor = distractors.get(i);

            if(distractor.getValue().trim().length() > 0) {
                JSONObject incorrectAnswer = new JSONObject();
                incorrectAnswer.put("incorrectAnswerText", distractor.getValue());
                if(distractor.getFeedback() != null && !distractor.getFeedback().trim().isEmpty()) {
                    incorrectAnswer.put("incorrectAnswerFeedback", distractor.getFeedback());
                }
                incorrectAnswers.add(incorrectAnswer);
            }
        }

        if(incorrectAnswers.size() > 0) {
            JSONObject feedbackObject = new JSONObject();
            feedbackObject.put("feedbackId", feedbackId);
            feedbackObject.put("incorrectAnswersList", incorrectAnswers);
            ((JSONArray) jsonObject.get("blanksFeedback")).add(feedbackObject);

            return true;
        }

        return false;
    }

}
