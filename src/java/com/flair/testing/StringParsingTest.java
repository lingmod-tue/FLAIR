/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flair.testing;

import com.flair.grammar.Language;
import com.flair.parser.AbstractDocumentSource;
import com.flair.parser.DocumentCollection;
import com.flair.parser.StringDocumentSource;
import com.flair.taskmanager.AbstractPipelineOperation;
import com.flair.taskmanager.MasterJobPipeline;
import com.flair.utilities.FLAIRLogger;
import com.flair.utilities.JSONWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maria
 */
public class StringParsingTest {
    public static void main(String[] args)
    {
        String inputString = "An example, of 50$ sentence.";
        if (args.length > 0)
	    inputString = args[0];
	
	JSONWriter serializer = new JSONWriter();
	FLAIRLogger.get().trace("Sentence: " + inputString);
        
	List<AbstractDocumentSource> inputSource = new ArrayList<>();
	inputSource.add(new StringDocumentSource(inputString, Language.ENGLISH));
  
	FLAIRLogger.get().info("Begin job...");
	long startTime = System.currentTimeMillis();
	AbstractPipelineOperation op = MasterJobPipeline.get().performDocumentParsing(Language.ENGLISH, inputSource, null); // TODO: change the last parameter
	op.begin();
	DocumentCollection docCol = (DocumentCollection)op.getOutput();
	long endTime = System.currentTimeMillis();

        String frequencyTable = docCol.generateFrequencyTable();
        String[] outputHeader = frequencyTable.split("\n")[0].split(",");
        String[] outputBody = frequencyTable.split("\n")[1].split(",");
        String output = inputString + "\n\n";
        int numWords = Integer.valueOf(outputBody[outputBody.length-2]);
        if (outputBody.length != outputHeader.length) {
            FLAIRLogger.get().error("The number of constructions in the header is not equal to that in the body.");
        }
        for (int i = 0; i < outputHeader.length; i++) {
            // only show the non-zero constructions
            if (Float.valueOf(outputBody[i]) != 0.0)
                if (i < outputHeader.length-3)
                    output += outputHeader[i] + ": " + Float.valueOf(outputBody[i])*numWords + "\n";
                else 
                    output += outputHeader[i] + ": " + outputBody[i] + "\n";
        }
	System.out.println(output);
        


//	FLAIRLogger.get().trace(op.toString());
//	FLAIRLogger.get().trace("StringParsingTest parsed " + docCol.size() + " documents in " + (endTime - startTime) + " ms");
	System.exit(0);
     }
}
