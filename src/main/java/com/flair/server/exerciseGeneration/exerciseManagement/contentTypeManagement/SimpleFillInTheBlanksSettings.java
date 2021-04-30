package com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement;

import com.flair.server.exerciseGeneration.exerciseManagement.SimpleExerciseGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement.SingleExerciseWithInlineBlanksJsonManager;

public class SimpleFillInTheBlanksSettings extends ContentTypeSettings {

    public SimpleFillInTheBlanksSettings() {
        super("fib", new SingleExerciseWithInlineBlanksJsonManager(), false, new SimpleExerciseGenerator(), "H5P.XBlanks 0.41");
    }

}
