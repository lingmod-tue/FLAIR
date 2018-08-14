package com.flair.server.pipelines.gramparsing;

import com.flair.server.document.AbstractDocument;
import com.flair.server.parser.AbstractParser;
import com.flair.shared.grammar.Language;

final class ParserInput implements AbstractParser.Input {
	final AbstractDocument source;
	ParserInput(AbstractDocument source) {
		this.source = source;
	}

	@Override
	public Language language() {
		return source.getLanguage();
	}
}
