package com.flair.server.pipelines.gramparsing;

import com.flair.server.document.AbstractDocument;
import com.flair.server.parser.AbstractParser;

final class ParserOutput implements AbstractParser.Output {
	final AbstractDocument parsedDoc;
	ParserOutput(AbstractDocument parsedDoc) {this.parsedDoc = parsedDoc;}

	@Override
	public boolean valid() {
		return parsedDoc.isParsed();
	}
}
