package arkref.sent;

import arkref.parsestuff.AnalysisUtilities;
import arkref.parsestuff.U;

import java.io.FileNotFoundException;


public class StanfordSent {
	public static void main(String[] args) throws FileNotFoundException {
		String text = U.readFile(args[0]);
		for (String s : AnalysisUtilities.getInstance().getSentencesStanford(text)) {
			U.pl(s);
		}
	}
}
