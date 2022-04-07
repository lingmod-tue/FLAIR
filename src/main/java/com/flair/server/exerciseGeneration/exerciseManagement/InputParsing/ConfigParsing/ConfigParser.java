package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing;

import java.io.InputStream;
import java.util.ArrayList;

import com.flair.server.exerciseGeneration.exerciseManagement.ConstructionTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.TextPart;
import com.flair.shared.exerciseGeneration.Pair;

public abstract class ConfigParser {
	
	/**
	 * Punctuation marks before which no space is to be inserted
	 */
	private static final String punctuations = ".,:;!?";

	/**
	 * Compiles a sentence from a list of positions with correct spacing.
	 * @param positions The list of positions
	 * @return The sentences string with correct spacing
	 */
	protected String generateSentencesFromPositions(ArrayList<Pair<Integer, String>> positions) {
		StringBuilder sb = new StringBuilder();
		for (Pair<Integer, String> position : positions) {
			if (!position.second.isEmpty()) {
				if (!(position.first == 1) && !punctuations.contains(position.second.charAt(0) + "")) {
					sb.append(" ");
				}
				sb.append(position.second);
			}
		}

		return sb.toString().trim();
	}

	/**
	 * Determines the overall plain text from the parts and adds the construction
	 * indices respective to this plain text to the constructions
	 * @param data The exercise data
	 */
	protected void addPlainText(ExerciseData data) {
		StringBuilder sb = new StringBuilder();
		for (TextPart part : data.getParts()) {
			if (sb.length() > 0 && !sb.toString().endsWith(" ") && !part.getValue().startsWith(" ")) {
				sb.append(" ");
			}

			if (part instanceof ConstructionTextPart) {
				int startIndex = sb.length();
				int endIndex = sb.length() + part.getValue().length();
				((ConstructionTextPart) part).setIndicesInPlainText(new Pair<>(startIndex, endIndex));
			}
			sb.append(part.getValue());
		}

		data.setPlainText(sb.toString());
	}
	
	/**
	 * Parses the input stream and generates a set of exercise data of varying
	 * complexities.
	 * @param inputStream The input stream of the config file
	 * @return The exercise data structured into exercise types and blocks of max.
	 *         10 items
	 */
	public ArrayList<ExerciseData> parseConfigFile(InputStream inputStream, String topic, String fileExtension) {
		ArrayList<ExerciseConfigData> configData;
		if(fileExtension.equals("xlsx") ) {
			configData = new ExcelFileManager().readExcelFile(inputStream, topic);
		} else if(fileExtension.equals("json")) {
			configData = JsonFileReaderFactory.getReader(topic).parse(inputStream);
		} else {
			return null;
		}
	
		ArrayList<ExerciseData> result = new ArrayList<>();
		for(ExerciseConfigData data : configData) {
			ArrayList<ExerciseData> generatedExercises = generateExerciseForConfig(data);
			
			if(generatedExercises != null) {
				result.addAll(generatedExercises);
			}
		}

		return result;
	}
	
	/**
	 * Generates all exercises defined in the resource file for conditional exercises,
	 * for a single exercise defined in the uploaded file.
	 * @param exerciseConstellations	The exercise definitions defined in the resource file
	 * @param data						The exercise as defined in the uploaded file
	 * @return	The generated exercises in abstracted format
	 */
	protected abstract ArrayList<ExerciseData> generateExerciseForConfig(ExerciseConfigData data);
	
}
