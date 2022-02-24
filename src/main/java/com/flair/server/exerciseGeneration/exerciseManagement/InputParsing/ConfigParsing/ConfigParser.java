package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.ConfigParsing;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.flair.server.exerciseGeneration.exerciseManagement.ConstructionTextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.ExerciseData;
import com.flair.server.exerciseGeneration.exerciseManagement.TextPart;
import com.flair.server.exerciseGeneration.exerciseManagement.resourceManagement.ResourceLoader;
import com.flair.shared.exerciseGeneration.Pair;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;

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

		List<String[]> exerciseConstellations = readExerciseConstellations();
		if(exerciseConstellations == null) {
			return null;
		}
				
		ArrayList<ExerciseData> result = new ArrayList<>();
		for(ExerciseConfigData data : configData) {
			ArrayList<ExerciseData> generatedExercises = generateExerciseForConfig(exerciseConstellations, data);
			
			if(generatedExercises != null) {
				result.addAll(generatedExercises);
			}
		}

		return result;
	}

	/**
	 * Reads the file of exercise constellations (exercise type with parameter settings) which we want to generate.
	 * @return	The exercise constellations which we want to generate
	 */
	protected List<String[]> readExerciseConstellations() {
		try (InputStream content = ResourceLoader.loadFile("feedbook_exercise_configurations.tsv");
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(content))) {
			TsvParserSettings settings = new TsvParserSettings();
			settings.getFormat().setLineSeparator("\n");
			TsvParser parser = new TsvParser(settings);

			return parser.parseAll(bufferedReader);
		} catch (IOException e) {
			return null;
		}
	}
	
	/**
	 * Generates all exercises defined in the resource file for conditional exercises,
	 * for a single exercise defined in the uploaded file.
	 * @param exerciseConstellations	The exercise definitions defined in the resource file
	 * @param data						The exercise as defined in the uploaded file
	 * @return	The generated exercises in abstracted format
	 */
	protected abstract ArrayList<ExerciseData> generateExerciseForConfig(List<String[]> exerciseConstellations, ExerciseConfigData data);
	
}
