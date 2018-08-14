package com.flair.server.pipelines.gramparsing;

import com.flair.server.parser.CoreNlpParser;

interface ParsingLogic {
	void apply(CoreNlpParser parser);
}
