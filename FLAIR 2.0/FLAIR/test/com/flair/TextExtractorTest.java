package com.flair;

import com.flair.server.utilities.ServerLogger;
import java.io.File;
import org.apache.tika.Tika;

class TextExtractorTest
{
	public static void main(String[] args) {
		String rootInPath = System.getProperty("user.home") + "/FLAIRLocalTest";
		String rootOutPath = rootInPath + "/FLAIROutput";
		if (args.length != 0)
		{
			rootInPath = args[0];
			rootOutPath = rootInPath + "/FLAIROutput";
		}

		ServerLogger.get().trace("Root Input Path: " + rootInPath + "\nRoot Output Path: " + rootOutPath);
		File rootDir = new File(rootInPath);
		if (rootDir.isDirectory() == false)
		{
			ServerLogger.get().error("Root input path is not a directory");
			System.exit(0);
		}

		File[] inputFiles = rootDir.listFiles();
		Tika pipeline = new Tika();
		pipeline.setMaxStringLength(-1);

		for (File itr : inputFiles)
		{
			if (itr.isDirectory())
				continue;

			try
			{
				ServerLogger.get().info("File: " + itr.getPath() + " | Type: " + pipeline.detect(itr));
				String text = pipeline.parseToString(itr);
				ServerLogger.get().info("Text parsed! Length: " + text.length());
			} catch (Throwable ex)
			{
				ServerLogger.get().error("Exception: " + ex.getMessage());
			}
		}

		System.exit(0);
	}
}
