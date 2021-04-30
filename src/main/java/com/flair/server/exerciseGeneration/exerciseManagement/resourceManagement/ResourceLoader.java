package com.flair.server.exerciseGeneration.exerciseManagement.resourceManagement;

import java.io.InputStream;

public abstract class ResourceLoader {

    public static InputStream loadFile(String fileName) {   	
        return ResourceLoader.class.getResourceAsStream(fileName);           
    }

}