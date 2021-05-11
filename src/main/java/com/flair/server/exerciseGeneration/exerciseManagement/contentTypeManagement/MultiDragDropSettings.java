package com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement;

import com.flair.server.exerciseGeneration.exerciseManagement.SimpleExerciseGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement.ExerciseWithInlineBlanksJsonManager;

public class MultiDragDropSettings extends ContentTypeSettings {

    public MultiDragDropSettings(String name) {
        super("drag_the_words.h5p", new ExerciseWithInlineBlanksJsonManager(), true, new SimpleExerciseGenerator(), "H5P.XDragText 1.1", name);
    }

}
