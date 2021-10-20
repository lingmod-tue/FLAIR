package com.flair.server.pipelines.gramparsing;

import com.flair.server.parser.CoreNlpParser;

interface ParsingLogic {
	void apply(CoreNlpParser parser);
	
	/**
	 * Stops the execution if the task is interrupted.
	 */
	public void stopExecution();
}
