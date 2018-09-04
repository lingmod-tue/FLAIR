package arkref.analysis;

import arkref.sent.SentenceBreaker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.List;

public class Preprocess {
	public static boolean alreadyPreprocessed(String path) {
		String shortpath = shortPath(path);
		return
				new File(shortpath + ".sst").exists() &&
						new File(shortpath + ".parse").exists();
	}

	public static String shortPath(String path) {
		return path.replace(".txt", "").replace(".sent", "");
	}

	public static void writeOffsetSentenceFile(List<SentenceBreaker.Sentence> sentences, String shortpath, boolean useTempFiles) throws FileNotFoundException {
		File osentOutputFile = new File(shortpath + ".osent");
		if (useTempFiles) osentOutputFile.deleteOnExit();
		PrintWriter pwOSent = new PrintWriter(new FileOutputStream(osentOutputFile));

		for (SentenceBreaker.Sentence s : sentences) {
			pwOSent.printf("%d\t%d\t%s\n", s.charStart, s.charEnd, s.cleanText);
		}
		pwOSent.close();
	}
}
