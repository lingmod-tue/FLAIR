package com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement;

import com.flair.server.exerciseGeneration.exerciseManagement.SimpleExerciseGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement.MultipleExercisesWithInlineBlanksJsonManager;

public class MultiDragDropSettings extends ContentTypeSettings {

    public MultiDragDropSettings() {
        super("drag_the_words.h5p", new MultipleExercisesWithInlineBlanksJsonManager(), true, new SimpleExerciseGenerator(), "H5P.XDragText 1.0");
    }

}
