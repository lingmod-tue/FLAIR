package arkref.parsestuff;

import arkref.sent.SentenceBreaker;
import com.flair.server.utilities.ServerLogger;
import com.flair.server.utilities.WordNetDictionary;
import edu.cmu.ark.ParseResult;
import edu.cmu.ark.TregexPatternFactory;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Label;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.trees.tregex.TregexMatcher;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

// import net.didion.jwnl.*;

/**
 * Various NL analysis utilities, including ones wrapping Stanford subsystems and other misc stuff
 **/
public class AnalysisUtilities {
	private static TreeFactory TREE_FACTORY;

	static {
		synchronized (AnalysisUtilities.class) {
			TREE_FACTORY = new LabeledScoredTreeFactory();
		}
	}

	public static int[] alignTokens(String rawText, List<arkref.data.Word> words) {
		String[] tokens = new String[words.size()];
		for (int i = 0; i < words.size(); i++) {
			tokens[i] = words.get(i).token;
		}
		return alignTokens(rawText, tokens);
	}

	public static int[] alignTokens(String rawText, String[] tokens) {
		int MAX_ALIGNMENT_SKIP = 100;
		int[] alignments = new int[tokens.length];
		int curPos = 0;

		tok_loop:

		for (int i = 0; i < tokens.length; i++) {
			String tok = tokens[i];
			//			U.pf("TOKEN [%s]  :  ", tok);
			for (int j = 0; j < MAX_ALIGNMENT_SKIP; j++) {
				boolean directMatch = rawText.regionMatches(curPos + j, tok, 0,
						tok.length());
				if (!directMatch)
					directMatch = rawText.toLowerCase().regionMatches(
							curPos + j, tok.toLowerCase(), 0, tok.length());
				boolean alternateMatch = false;
				if (!directMatch) {
					int roughLast = curPos + j + tok.length() * 2 + 10;
					String substr = StringUtils.substring(rawText, curPos + j,
							roughLast);
					Matcher m = tokenSurfaceMatches(tok).matcher(substr);
					//					U.pl("PATTERN "+ tokenSurfaceMatches(tok));
					alternateMatch = m.find() && m.start() == 0;
				}

				//				U.pl("MATCHES "+ directMatch + " " + alternateMatch);
				if (directMatch || alternateMatch) {
					alignments[i] = curPos + j;
					if (directMatch)
						curPos = curPos + j + tok.length();
					else
						curPos = curPos + j + 1;
					//					U.pf("\n  Aligned to pos=%d : [%s]\n", alignments[i], U.backslashEscape(StringUtils.substring(rawText, alignments[i], alignments[i]+10)));
					continue tok_loop;
				}
				//				U.pf("%s", U.backslashEscape(StringUtils.substring(rawText,curPos+j,curPos+j+1)));
			}
			U.pf("FAILED MATCH for token [%s]\n", tok);
			U.pl("sentence: " + rawText);
			U.pl("tokens: " + StringUtils.join(tokens, " "));
			alignments[i] = -1;
		}
		// TODO backoff for gaps .. at least guess the 2nd gap position or something (2nd char after previous token ends...)
		return alignments;
	}

	/**
	 * undo penn-treebankification of tokens. want to match raw original form if possible.
	 **/
	public static Pattern tokenSurfaceMatches(String tok) {
		if (tok.equals("-LRB-")) {
			return Pattern.compile("[(\\[]");
		} else if (tok.equals("-RRB-")) {
			return Pattern.compile("[)\\]]");
		} else if (tok.equals("``")) {
			return Pattern.compile("(\"|``)");
		} else if (tok.equals("''")) {
			return Pattern.compile("(\"|'')");
		} else if (tok.equals("`")) {
			return Pattern.compile("('|`)");
		}
		return Pattern.compile(Pattern.quote(tok));
	}

	public static String[] stanfordTokenize(String str) {
		DocumentPreprocessor dp = new DocumentPreprocessor(
				new StringReader(str));

		List<HasWord> wordToks = new ArrayList<HasWord>();
		for (List<? extends HasWord> l : dp) {
			wordToks.addAll(l);
		}
		String[] tokens = new String[wordToks.size()];
		for (int i = 0; i < wordToks.size(); i++)
			tokens[i] = wordToks.get(i).word();
		return tokens;
	}

	public static List<SentenceBreaker.Sentence> cleanAndBreakSentences(
			String docText) {
		// ACE IS EVIL
		docText = docText.replaceAll("<\\S+>", "");
		AlignedSub cleaner = AnalysisUtilities.cleanupDocument(docText);
		List<SentenceBreaker.Sentence> sentences = SentenceBreaker.getSentences(cleaner);
		return sentences;
	}

	/**
	 * uses stanford library for document cleaning and sentence breaking
	 **/
	public static List<String> getSentencesStanford(String document) {
		List<String> res = new ArrayList<String>();
		String sentence;
		StringReader reader = new StringReader(cleanupDocument(document).text);

		List<List<? extends HasWord>> sentences = new ArrayList<List<? extends HasWord>>();
		Iterator<List<? extends HasWord>> iter1;
		Iterator<? extends HasWord> iter2;

		try {
			DocumentPreprocessor dp = new DocumentPreprocessor(reader);
			for (List<? extends HasWord> l : dp) {
				sentences.add(l);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		iter1 = sentences.iterator();
		while (iter1.hasNext()) {
			iter2 = iter1.next().iterator();
			sentence = "";
			while (iter2.hasNext()) {
				String tmp = iter2.next().word().toString();
				sentence += tmp;
				if (iter2.hasNext()) {
					sentence += " ";
				}
			}
			res.add(sentence);
		}

		return res;
	}

	static Pattern leadingWhitespace = Pattern.compile("^\\s+");

	/**
	 * some ACE docs have weird markup in them that serve as paragraph-ish markers
	 **/
	public static AlignedSub cleanupDocument(String document) {
		AlignedSub ret = new AlignedSub(document);
		ret = ret.replaceAll("<\\S+>", "");
		ret = ret.replaceAll(leadingWhitespace, ""); // sentence breaker char offset correctness sensitive to this
		return ret;
	}

	public static AlignedSub moreCleanup(String str) {
		AlignedSub ret = new AlignedSub(str);
		ret = ret.replaceAll("&(amp|AMP);", "&");
		ret = ret.replaceAll("&(lt|LT);", "<");
		ret = ret.replaceAll("&(gt|GT);", ">");
		return ret;
	}

	public static AnalysisUtilities getInstance() {
		if (instance == null) {
			synchronized (AnalysisUtilities.class) {
				if (instance == null)
					instance = new AnalysisUtilities();
			}
		}
		return instance;
	}

	public HeadFinder getHeadFinder() {
		return headFinder;
	}


	public static Tree readTreeFromString(String parseStr) {
		//read in the input into a Tree data structure
		TreeReader treeReader = new PennTreeReader(new StringReader(parseStr), TREE_FACTORY);
		Tree inputTree = null;
		try {
			inputTree = treeReader.readTree();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputTree;
	}

	/**
	 * terse representation of a (sub-)tree: NP[the white dog] -vs- (NP (DT the) (JJ white) (NN
	 * dog))
	 **/
	public static String abbrevTree(Tree tree) {
		ArrayList<String> toks = new ArrayList();
		for (Tree L : tree.getLeaves()) {
			toks.add(L.label().toString());
		}
		return tree.label().toString() + "[" + StringUtils.join(toks, " ")
				+ "]";
	}

	public static int getNumberOfMatchesInTree(String tregexExpression, Tree t) {
		int res = 0;
		TregexMatcher m = TregexPatternFactory.getPattern(tregexExpression).matcher(t);
		while (m.find()) {
			res++;
		}
		return res;
	}

	public static void downcaseFirstToken(Tree inputTree) {
		Tree firstWordTree = inputTree.getLeaves().get(0);
		if (firstWordTree == null) return;
		Tree preterm = firstWordTree.parent(inputTree);
		String firstWord = firstWordTree.yield().toString();
		if (!preterm.label().toString().matches("^NNP.*") && !firstWord.equals("I")) {
			//if(firstWord.indexOf('-') == -1 && !firstWord.equals("I")){
			firstWord = firstWord.substring(0, 1).toLowerCase() + firstWord.substring(1);
			firstWordTree.label().setValue(firstWord);
		}
	}


	public static void upcaseFirstToken(Tree inputTree) {
		Tree firstWordTree = inputTree.getLeaves().get(0);
		if (firstWordTree == null) return;

		String firstWord = firstWordTree.yield().toString();
		firstWord = firstWord.substring(0, 1).toUpperCase() + firstWord.substring(1);
		firstWordTree.label().setValue(firstWord);

		//if(QuestionTransducer.DEBUG) ServerLogger.get().info("upcaseFirstToken: "+inputTree.toString());
	}

	public static ParseResult parseSentence(String sentence) {
		String result = "";
		Tree parse = null;
		double parseScore = Double.MIN_VALUE;

		ServerLogger.get().info("parsing:" + sentence);

		Sentence wrapper = new Sentence(sentence);
		parse = wrapper.parse();
		//remove all the parent annotations (this is a hacky way to do it)
		String ps = parse.toString().replaceAll("\\[[^\\]]+/[^\\]]+\\]", "");
		parse = AnalysisUtilities.getInstance().readTreeFromString(ps);

		parseScore = parse.score();
		return new ParseResult(true, parse, parseScore);
	}

	public static String[] stringArrayFromLabels(List<Label> l) {
		List<String> values = new ArrayList<String>();
		for (Label lb : l) {
			values.add(lb.value());
		}
		return values.toArray(new String[0]);
	}


	public static String orginialSentence(List<Label> l) {
		String text = StringUtils.join(Arrays.asList(stringArrayFromLabels(l)), " ");
		text = text.replaceAll("\\s(?=\\p{Punct})", "");
		return text;
	}


	public static String getLemma(String word, String pos) {
		String lemma = WordNetDictionary.defaultInstance().lemma(word, pos);
		if (lemma.isEmpty())
			lemma = word.toLowerCase();
		return lemma;
	}


	public static String getCleanedUpYield(Tree inputTree) {
		Tree copyTree = inputTree.deepCopy();

		//if(GlobalProperties.getDebug()) ServerLogger.get().info("yield:"+copyTree.toString());

		return cleanUpSentenceString(copyTree.yield().toString());
	}


	public static String cleanUpSentenceString(String s) {
		String res = s;
		//if(res.length() > 1){
		//	res = res.substring(0,1).toUpperCase() + res.substring(1);
		//}

		res = res.replaceAll("\\s([\\.,!\\?\\-;:])", "$1");
		res = res.replaceAll("(\\$)\\s", "$1");
		res = res.replaceAll("can not", "cannot");
		res = res.replaceAll("\\s*-LRB-\\s*", " (");
		res = res.replaceAll("\\s*-RRB-\\s*", ") ");
		res = res.replaceAll("\\s*([\\.,?!])\\s*", "$1 ");
		res = res.replaceAll("\\s+''", "''");
		//res = res.replaceAll("\"", "");
		res = res.replaceAll("``\\s+", "``");
		res = res.replaceAll("\\-[LR]CB\\-", ""); //brackets, e.g., [sic]
		res = res.replaceAll("\\. \\?", ".?");
		res = res.replaceAll(" 's(\\W)", "'s$1");
		res = res.replaceAll("(\\d,) (\\d)", "$1$2"); //e.g., "5, 000, 000" -> "5,000,000"
		res = res.replaceAll("``''", "");

		//remove extra spaces
		res = res.replaceAll("\\s\\s+", " ");
		res = res.trim();

		return res;
	}

	public static boolean filterOutSentenceByPunctuation(String sentence) {
		return sentence.contains("*");
	}


	public static String getSurfaceForm(String lemma, String pos) {
		String result = lemma;
		if (pos.equals("VBD") || pos.equals("VBZ")) {
			if (!lemma.matches("^.*[aieou]$")) {
				result += "e";
			}
			if (pos.equals("VBD")) {
				result += "d";
			} else if (pos.equals("VBZ")) {
				result += "s";
			}
		}

		return result;
	}
	public static List<String> getSentences(String doc) {
		return new edu.stanford.nlp.simple.Document(doc).sentences().stream().map(Sentence::text).collect(Collectors.toList());
	}

	private HeadFinder headFinder = new CollinsHeadFinder();
	private static AnalysisUtilities instance;
}
