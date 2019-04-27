package com.flair.server.interop;

import com.flair.server.document.AbstractDocument;
import com.flair.server.pipelines.PipelineOp;
import com.flair.shared.interop.dtos.DocumentDTO;

import java.util.ArrayList;
import java.util.List;

final class TemporaryClientData {
	static final class CustomCorpus {
		List<CustomCorpusFile> uploaded = new ArrayList<>();
	}

	static final class QuestionGen {
		String eagerParsingOpId = "";
		AbstractDocument eagerSourceDoc = null;
		DocumentDTO eagerLinkingDoc = null;
		PipelineOp.PipelineOpBuilder queuedOperation = null;

		QuestionGen(AbstractDocument eagerSourceDoc, DocumentDTO eagerLinkingDoc) {
			this.eagerSourceDoc = eagerSourceDoc;
			this.eagerLinkingDoc = eagerLinkingDoc;
		}
	}

	CustomCorpus customCorpusData = new CustomCorpus();
	QuestionGen questGenData = null;
}
