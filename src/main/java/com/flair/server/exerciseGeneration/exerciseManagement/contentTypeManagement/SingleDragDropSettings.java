package com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement;

import com.flair.server.exerciseGeneration.exerciseManagement.SimpleExerciseGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement.SingleExerciseWithInlineBlanksJsonManager;

public class SingleDragDropSettings extends ContentTypeSettings {

    public SingleDragDropSettings() {
        super("drag_the_words_1task.h5p", new SingleExerciseWithInlineBlanksJsonManager(), true, new SimpleExerciseGenerator(), "H5P.XXDragText 0.1");
    }

}
