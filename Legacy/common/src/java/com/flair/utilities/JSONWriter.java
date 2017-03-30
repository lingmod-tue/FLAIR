/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 
 */
package com.flair.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Generic JSON serializer
 * @author shadeMe
 */
public class JSONWriter
{
    private final GsonBuilder	    builder;
    private final Gson		    writer;
    
    public JSONWriter()
    {
	builder = new GsonBuilder();
	builder.setPrettyPrinting();
	writer = builder.create();
    }
    
    public boolean writeObject(Object toWrite, String fileName, String path)
    {
	String outputJSON = writer.toJson(toWrite);
	File pathObj = new File(path);
	
	// create intermediate dirs as reqd.
	pathObj.mkdirs();
	try 
	{
	    FileWriter stream = new FileWriter(path + "\\" + fileName + ".json");
	    stream.write(outputJSON);
	    stream.close();
	    
	    return true;
	}
	catch (IOException e)
	{
	    FLAIRLogger.get().error("Couldn't serialize object to JSON. Exception: " + e.getMessage());
	    return false;
	}
    }
}
