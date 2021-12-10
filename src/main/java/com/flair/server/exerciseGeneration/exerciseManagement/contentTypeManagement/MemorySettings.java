package com.flair.server.exerciseGeneration.exerciseManagement.contentTypeManagement;

import com.flair.server.exerciseGeneration.exerciseManagement.SimpleExerciseGenerator;
import com.flair.server.exerciseGeneration.exerciseManagement.feedBookXmlManagement.FindXmlManager;
import com.flair.server.exerciseGeneration.exerciseManagement.feedBookXmlManagement.MemoryXmlManager;
import com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement.FindJsonManager;
import com.flair.server.exerciseGeneration.exerciseManagement.jsonManagement.MemoryJsonManager;

public class MemorySettings extends ContentTypeSettings {

    public MemorySettings(String name) {
        super("memory.h5p", new MemoryJsonManager(), false, new SimpleExerciseGenerator(), "H5P.XMemoryGame 0.1", 
        		name, new MemoryXmlManager());
    }

}
