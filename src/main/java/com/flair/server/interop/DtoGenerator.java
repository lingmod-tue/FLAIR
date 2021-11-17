package com.flair.server.interop;

import com.flair.server.crawler.SearchResult;
import com.flair.server.document.*;
import com.flair.server.parser.KeywordSearcherOutput;
import com.flair.server.utilities.TextSegment;
import com.flair.shared.grammar.GrammaticalConstruction;
import com.flair.shared.interop.dtos.RankableDocumentImpl;
import com.flair.shared.interop.dtos.RankableWebSearchResultImpl;
import com.flair.shared.interop.dtos.UploadedDocument;
import com.flair.shared.interop.dtos.UploadedDocumentImpl;

import java.util.ArrayList;

class DtoGenerator {
	static RankableDocumentImpl rankableDocument(AbstractDocument source, String opId) {
		RankableDocumentImpl out = new RankableDocumentImpl();
		final int snippetMaxLen = 100;

		if (!source.isParsed())
			throw new IllegalStateException("Document not flagged as parsed");

		out.setLanguage(source.getLanguage());
		out.setOperationId(opId);
		if (source.getDocumentSource() instanceof SearchResultDocumentSource) {
			SearchResultDocumentSource searchSource = (SearchResultDocumentSource) source.getDocumentSource();
			SearchResult searchResult = searchSource.getSearchResult();

			out.setTitle(searchResult.getTitle());
			out.setUrl(searchResult.getURL());
			out.setDisplayUrl(searchResult.getDisplayURL());
			out.setSnippet(searchResult.getSnippet());
			out.setRank(searchResult.getRank());
			out.setLinkingId(searchResult.getRank());        // ranks don't overlap, so we can use them as ids
		} else if (source.getDocumentSource() instanceof UploadedFileDocumentSource) {
			UploadedFileDocumentSource localSource = (UploadedFileDocumentSource) source.getDocumentSource();

			out.setTitle(localSource.getName());

			String textSnip = source.getText();
			if (textSnip.length() > snippetMaxLen)
				out.setSnippet(textSnip.substring(0, snippetMaxLen) + "...");
			else
				out.setSnippet(textSnip);

			out.setLinkingId(localSource.getId());        // use the id generated earlier
			out.setRank(localSource.getId());            // in the same order the files were uploaded to the server
		}

		out.setText(source.getText());

		for (GrammaticalConstruction itr : source.getSupportedConstructions()) {
			DocumentConstructionData data = source.getConstructionData(itr);
			if (data.hasConstruction()) {
				out.getConstructions().add(itr);
				out.getRelFrequencies().put(itr, data.getRelativeFrequency(source.getLength()));
				out.getFrequencies().put(itr, data.getFrequency());

				ArrayList<RankableDocumentImpl.ConstructionOccurrence> highlights = new ArrayList<>();
				for (ConstructionOccurrence occr : data.getOccurrences())
					highlights.add(new RankableDocumentImpl.ConstructionOccurrence(occr.getStart(), occr.getEnd(), itr));

				out.getConstOccurrences().put(itr, highlights);
			}
		}

		KeywordSearcherOutput keywordData = source.getKeywordData();
		if (keywordData != null) {
			for (String itr : keywordData.getKeywords()) {
				for (TextSegment hit : keywordData.getHits(itr))
					out.getKeywordOccurrences().add(new RankableDocumentImpl.KeywordOccurrence(hit.getStart(), hit.getEnd(), itr));
			}

			out.setKeywordCount(keywordData.getTotalHitCount());
			out.setKeywordRelFreq(keywordData.getTotalHitCount() / source.getNumWords());
		}

		out.setRawTextLength(source.getText().length());
		out.setNumWords(source.getNumWords());
		out.setNumSentences(source.getNumSentences());
		out.setNumDependencies(source.getNumDependencies());
		out.setReadabilityLevel(source.getReadabilityLevel());
		out.setReadabilityScore(source.getReadabilityScore());

		return out;
	}

	static RankableWebSearchResultImpl rankableWebSearchResult(SearchResult sr, String opId) {
		RankableWebSearchResultImpl out = new RankableWebSearchResultImpl();

		out.setOperationId(opId);
		out.setRank(sr.getRank());
		out.setTitle(sr.getTitle());
		out.setLang(sr.getLanguage());
		out.setUrl(sr.getURL());
		out.setDisplayUrl(sr.getDisplayURL());
		out.setSnippet(sr.getSnippet());
		out.setText(sr.getPageText());
		out.setLinkingId(sr.getRank());

		return out;
	}

	static ArrayList<UploadedDocument> uploadedDocs(Iterable<AbstractDocumentSource> source, String opId) {
		ArrayList<UploadedDocument> out = new ArrayList<>();

		for (AbstractDocumentSource itr : source) {
			UploadedFileDocumentSource sdr = (UploadedFileDocumentSource) itr;
			UploadedDocumentImpl u = new UploadedDocumentImpl();
			String snippet = itr.getSourceText();
			if (snippet.length() > 100)
				snippet = snippet.substring(0, 100);

			u.setOperationId(opId);
			u.setLanguage(itr.getLanguage());
			u.setTitle(sdr.getName());
			u.setSnippet(snippet);
			u.setText(itr.getSourceText());
			u.setLinkingId(sdr.getId());

			out.add(u);
		}

		return out;
	}
}
