package com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement;

import com.flair.server.exerciseGeneration.exerciseManagement.SimpleExerciseGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement.ExerciseWithInlineBlanksJsonManager;

public class SingleDragDropSettings extends ContentTypeSettings {

    public SingleDragDropSettings(String name) {
        super("drag_the_words_1task.h5p", new ExerciseWithInlineBlanksJsonManager(), true, new SimpleExerciseGenerator(), "H5P.XXDragText 0.1", name);
    }

}
