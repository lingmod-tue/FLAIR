package com.flair.server.exerciseGeneration.exerciseManagement.feedBookXmlManagement;

public class AdvancedFIBXmlManager extends SimpleExerciseXmlManager {

	public AdvancedFIBXmlManager(boolean useBlanks) {
        taskType = useBlanks ? "FILL_IN_THE_BLANKS" : "MULTIPLE_CHOICE";
    }
}
