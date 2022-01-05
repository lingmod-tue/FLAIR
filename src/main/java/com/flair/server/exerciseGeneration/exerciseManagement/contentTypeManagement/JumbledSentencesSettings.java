package com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement;

import com.flair.server.exerciseGeneration.exerciseManagement.DocumentBasedExerciseGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement.DragJsonManager;

public class JumbledSentencesSettings extends ContentTypeSettings {

    public JumbledSentencesSettings(String name) {
        super("drag_the_words.h5p", new DragJsonManager(), true, new DocumentBasedExerciseGenerator(), "H5P.XDragText 1.1", 
        		name);
    }

}
