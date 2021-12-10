package com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement;

import com.flair.server.exerciseGeneration.exerciseManagement.SimpleExerciseGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.feedBookXmlManagement.JumbledSentencesXmlManager;
import com.flair.server.exerciseGeneration.exerciseManagement.feedBookXmlManagement.SimpleExerciseXmlManager;
import com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement.DragJsonManager;

public class JumbledSentencesSettings extends ContentTypeSettings {

    public JumbledSentencesSettings(String name) {
        super("drag_the_words.h5p", new DragJsonManager(), true, new SimpleExerciseGenerator(), "H5P.XDragText 1.1", 
        		name, new JumbledSentencesXmlManager());
    }

}
