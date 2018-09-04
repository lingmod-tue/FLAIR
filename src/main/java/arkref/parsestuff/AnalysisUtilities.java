package arkref.parsestuff;

import arkref.sent.SentenceBreaker;
import com.flair.server.utilities.ServerLogger;
import com.flair.server.utilities.WordNetDictionary;
import edu.cmu.ark.*;
import edu.cmu.ark.TregexPatternFactory;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Label;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.trees.tregex.TregexMatcher;
import edu.stanford.nlp.trees.tregex.TregexPattern;
import edu.stanford.nlp.trees.tregex.tsurgeon.Tsurgeon;
import edu.stanford.nlp.trees.tregex.tsurgeon.TsurgeonPattern;
import edu.stanford.nlp.util.Pair;
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
	// ==================== ARKref API ====================================== //

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


	// ==================== QG API ====================================== //

	public static void addPeriodIfNeeded(Tree input) {
		String tregexOpStr = "ROOT < (S=mainclause !< /\\./)";
		TregexPattern matchPattern = TregexPatternFactory.getPattern(tregexOpStr);
		TregexMatcher matcher = matchPattern.matcher(input);

		if (matcher.find()) {
			TsurgeonPattern p;
			List<TsurgeonPattern> ps = new ArrayList<TsurgeonPattern>();
			List<Pair<TregexPattern, TsurgeonPattern>> ops = new ArrayList<Pair<TregexPattern, TsurgeonPattern>>();

			ps.add(Tsurgeon.parseOperation("insert (. .) >-1 mainclause"));
			p = Tsurgeon.collectOperations(ps);
			ops.add(new Pair<TregexPattern, TsurgeonPattern>(matchPattern, p));
			Tsurgeon.processPatternsOnTree(ops, input);
		}
	}

	public static int getNumberOfMatchesInTree(String tregexExpression, Tree t) {
		int res = 0;
		TregexMatcher m = TregexPatternFactory.getPattern(tregexExpression).matcher(
				t);
		while (m.find()) {
			res++;
		}
		return res;
	}

	public static List<String> getSentences(String document) {
		List<String> res = new ArrayList<String>();
		String sentence;

		document = preprocess(document);

		String[] paragraphs = document.split("\\n");

		for (int i = 0; i < paragraphs.length; i++) {
			StringReader reader = new StringReader(paragraphs[i]);
			List<List<? extends HasWord>> sents = new ArrayList<List<? extends HasWord>>();
			DocumentPreprocessor dp = new DocumentPreprocessor(reader);

			try {
				for (List<? extends HasWord> l : dp) {
					sents.add(l);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			for (List<? extends HasWord> tmp1 : sents) {
				sentence = "";
				for (HasWord tmp2 : tmp1) {
					String tmp = tmp2.word().toString();
					sentence += tmp + " ";
				}
				sentence = sentence.trim();
				res.add(sentence);
			}
		}

		return res;
	}

	public static String abbrevTree(Tree tree) {
		ArrayList<String> toks = new ArrayList<String>();
		for (Tree L : tree.getLeaves()) {
			toks.add(L.label().toString());
		}
		return tree.label().toString() + "[" + StringUtils.join(toks, " ")
				+ "]";
	}

	public static void downcaseFirstToken(Tree inputTree) {
		Tree firstWordTree = inputTree.getLeaves().get(0);
		if (firstWordTree == null) return;
		Tree preterm = firstWordTree.parent(inputTree);
		String firstWord = firstWordTree.yield().get(0).value(); //FIXME: is this change correct?
		if (!preterm.label().value().matches("^NNP.*")
				&& !firstWord.equals("I")) {
			//if(firstWord.indexOf('-') == -1 && !firstWord.equals("I")){
			firstWord = firstWord.substring(0, 1).toLowerCase()
					+ firstWord.substring(1);
			firstWordTree.label().setValue(firstWord);
		}

		//if(QuestionTransducer.DEBUG) System.err.println("downcaseFirstToken: "+inputTree.toString());
	}

	public static void upcaseFirstToken(Tree inputTree) {
		Tree firstWordTree = inputTree.getLeaves().get(0);
		if (firstWordTree == null) return;

		String firstWord = firstWordTree.yield().get(0).value();
		firstWord = firstWord.substring(0, 1).toUpperCase()
				+ firstWord.substring(1);
		firstWordTree.label().setValue(firstWord);

		//if(QuestionTransducer.DEBUG) System.err.println("upcaseFirstToken: "+inputTree.toString());
	}

	public static String preprocess(String sentence) {
		//remove trailing whitespace
		sentence = sentence.trim();

		//remove single words in parentheses.
		//the stanford parser api messed up on these
		//by removing the parentheses but not the word in them
		sentence = sentence.replaceAll("\\(\\S*\\)", "");
		sentence = sentence.replaceAll("\\(\\s*\\)", "");

		//some common unicode characters that the tokenizer throws out otherwise
		sentence = sentence.replaceAll("â€”", "--");
		sentence = sentence.replaceAll("â€™", "'");
		sentence = sentence.replaceAll("â€�", "\"");
		sentence = sentence.replaceAll("â€œ", "\"");
		sentence = sentence.replaceAll("Ã©|Ã¨|Ã«|Ãª", "e");
		sentence = sentence.replaceAll("Ã‰|Ãˆ|ÃŠ|Ã‹", "E");
		sentence = sentence.replaceAll("Ã¬|Ã­|Ã®|Ã¯", "i");
		sentence = sentence.replaceAll("ÃŒ|Ã�|ÃŽ|Ã�", "I");
		sentence = sentence.replaceAll("Ã |Ã¡|Ã¢|Ã£|Ã¤|Ã¦|Ã¥", "a");
		sentence = sentence.replaceAll("Ã€|Ã�|Ã‚|Ãƒ|Ã„|Ã…|Ã†", "A");
		sentence = sentence.replaceAll("Ã²|Ã³|Ã´|Ãµ|Ã¶", "o");
		sentence = sentence.replaceAll("Ã’|Ã“|Ã”|Ã•|Ã–", "O");
		sentence = sentence.replaceAll("Ã¹|Ãº|Ã»|Ã¼", "u");
		sentence = sentence.replaceAll("Ã™|Ãš|Ã›|Ãœ", "U");
		sentence = sentence.replaceAll("Ã±", "n");

		//contractions
		sentence = sentence.replaceAll("can't", "can not");
		sentence = sentence.replaceAll("won't", "will not");
		sentence = sentence.replaceAll("n't", " not"); //aren't shouldn't don't isn't
		sentence = sentence.replaceAll("are n't", "are not");

		//simply remove other unicode characters
		//if not, the tokenizer replaces them with spaces,
		//which wreaks havoc on the final parse sometimes
		for (int i = 0; i < sentence.length(); i++) {
			if (sentence.charAt(i) > 'z') {
				sentence = sentence.substring(0, i) + sentence.substring(i + 1);
			}
		}

		//add punctuation to the end if necessary
		/*Matcher matcher = Pattern.compile(".*\\.['\"\n ]*$", Pattern.DOTALL).matcher(sentence);
		if(!matcher.matches()){
			sentence += ".";
		}*/

		return sentence;
	}

	public static String preprocessTreeString(String sentence) {
		sentence = sentence.replaceAll(" n't", " not");
		sentence = sentence.replaceAll("\\(MD ca\\)", "(MD can)");
		sentence = sentence.replaceAll("\\(MD wo\\)", "(MD will)");
		sentence = sentence.replaceAll("\\(MD 'd\\)", "(MD would)");
		sentence = sentence.replaceAll("\\(VBD 'd\\)", "(VBD had)");
		sentence = sentence.replaceAll("\\(VBZ 's\\)", "(VBZ is)");
		sentence = sentence.replaceAll("\\(VBZ 's\\)", "(VBZ is)");
		sentence = sentence.replaceAll("\\(VBZ 's\\)", "(VBZ is)");
		sentence = sentence.replaceAll("\\(VBP 're\\)", "(VBP are)");

		return sentence;
	}

	public static String getCleanedUpYield(Tree inputTree) {
		Tree copyTree = inputTree.deepCopy();

		//if(GlobalProperties.getDebug()) System.err.println("yield:"+copyTree.toString());

		return cleanUpSentenceString(copyTree.yield().stream().map(Label::value).collect(Collectors.toList()));
	}

	public static String cleanUpSentenceString(List<String> s) {
		String res = String.join(" ", s);
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

	public static boolean cCommands(Tree root, Tree n1, Tree n2) {
		if (n1.dominates(n2)) return false;

		Tree n1Parent = n1.parent(root);
		while (n1Parent != null && n1Parent.numChildren() == 1) {
			n1Parent = n1Parent.parent(root);
		}

		if (n1Parent != null && n1Parent.dominates(n2)) return true;

		return false;
	}

	public static boolean filterOutSentenceByPunctuation(String sentence) {
		//return (sentence.indexOf("\"") != -1
		//|| sentence.indexOf("''") != -1
		//|| sentence.indexOf("``") != -1
		//|| sentence.indexOf("*") != -1);
		if (sentence.indexOf("*") != -1) {
			return true;
		}

		//if(sentence.matches("[^\\w\\-\\/\\?\\.,;:\\$\\#\\&\\(\\) ]")){
		//	return true;
		//}

		return false;
	}

	public static void removeExtraQuotes(Tree input) {
		List<Pair<TregexPattern, TsurgeonPattern>> ops = new ArrayList<Pair<TregexPattern, TsurgeonPattern>>();
		String tregexOpStr;
		TregexPattern matchPattern;
		TsurgeonPattern p;
		List<TsurgeonPattern> ps;

		ps = new ArrayList<TsurgeonPattern>();
		tregexOpStr = "ROOT [ << (``=quote < `` !.. ('' < '')) | << (''=quote < '' !,, (`` < ``)) ] ";
		matchPattern = TregexPatternFactory.getPattern(tregexOpStr);
		ps.add(Tsurgeon.parseOperation("prune quote"));
		p = Tsurgeon.collectOperations(ps);
		ops.add(new Pair<TregexPattern, TsurgeonPattern>(matchPattern, p));
		Tsurgeon.processPatternsOnTree(ops, input);

	}

	/**
	 * @author Michael Kutschke 9/2012
	 */
	public static String[] stringArrayFromLabels(List<Label> l) {
		List<String> values = new ArrayList<String>();
		for (Label lb : l) {
			values.add(lb.value());
		}
		return values.toArray(new String[0]);
	}

	/**
	 * outputs the label values. This is used to replace the .yield().toString() construct that was
	 * commonly found in the original source code, but is outdated and does not work as expected anymore with newer
	 * versions of the StanfordNLP tools.
	 *
	 * @author Michael Kutschke 9/2012
	 */
	public static String orginialSentence(List<Label> l) {
		String text = StringUtils.join(Arrays.asList(stringArrayFromLabels(l)), " ");
		text = text.replaceAll("\\s(?=\\p{Punct})", "");
		return text;
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

	public static String getLemma(String word, String pos) {
		String lemma = WordNetDictionary.defaultInstance().lemma(word, pos);
		if (lemma.isEmpty())
			lemma = word.toLowerCase();
		return lemma;
	}

	public VerbConjugator getConjugator() {
		return conjugator;
	}

	public CollinsHeadFinder getHeadFinder() {
		return headfinder;
	}

	public Tree readTreeFromString(String parseStr) {
		//read in the input into a Tree data structure
		TreeReader treeReader = new PennTreeReader(new StringReader(parseStr),
				tree_factory);
		Tree inputTree = null;
		try {
			inputTree = treeReader.readTree();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputTree;
	}

	public String getSurfaceForm(String lemma, String pos) {
		return conjugator.getSurfaceForm(lemma, pos);
	}

	private static AnalysisUtilities instance;
	private VerbConjugator conjugator;
	private CollinsHeadFinder headfinder;
	private LabeledScoredTreeFactory tree_factory;

	private AnalysisUtilities() {
		conjugator = new VerbConjugator();
		conjugator.load(ResourceLoader.path(GlobalProperties.getProperties().getProperty(
				"verbConjugationsFile",
				"config/verbConjugations.txt")));
		headfinder = new CollinsHeadFinder();
		tree_factory = new LabeledScoredTreeFactory();
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
}
