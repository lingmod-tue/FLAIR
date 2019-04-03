package com.flair.server;

import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;

import java.util.Map;

public class Playground {
	public static void main(String[] args) {
		// Create a document. No computation is done yet.
		Document doc = new Document("Barack Obama and Sarah were born in Hawaii.  He is the president. She was elected in 2008.");
		for (Sentence sent : doc.sentences()) {  // Will iterate over two sentences
			// We're only asking for words -- no need to load any models yet
			System.out.println("The second word of the sentence '" + sent + "' is " + sent.word(1));
			// When we ask for the lemma, it will load and run the part of speech tagger
			System.out.println("The third lemma of the sentence '" + sent + "' is " + sent.lemma(2));
		}

		Map<Integer, CorefChain> corefs = doc.coref();
		return;
	}
}
