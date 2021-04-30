package com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement;

import com.flair.server.exerciseGeneration.exerciseManagement.SimpleExerciseGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement.SingleExerciseWithInlineBlanksJsonManager;

public class FindSettings extends ContentTypeSettings {

    public FindSettings() {
        super("mark_the_words.h5p", new SingleExerciseWithInlineBlanksJsonManager(), false, new SimpleExerciseGenerator(), "H5P.XMarkTheWords 0.1");
    }

}
