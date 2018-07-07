/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License.
 * To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.

 */
package com.flair.server.taskmanager;

import com.flair.server.parser.AbstractDocumentParser;
import com.flair.server.parser.AbstractDocumentParserFactory;
import com.flair.server.utilities.SimpleObjectPool;
import com.flair.server.utilities.SimpleObjectPoolResource;

/**
 * Provides a pool of document parsers for executing parsing tasks
 *
 * @author shadeMe
 */
class DocumentParserPool {
	private final AbstractDocumentParserFactory parserFactory;
	private final SimpleObjectPool<AbstractDocumentParser> resourcePool;

	public DocumentParserPool(AbstractDocumentParserFactory factory) {
		parserFactory = factory;
		assert Constants.PARSER_INSTANCEPOOL_SIZE >= Constants.PARSER_THREADPOOL_SIZE;

		AbstractDocumentParser[] parsers = new AbstractDocumentParser[Constants.PARSER_INSTANCEPOOL_SIZE];
		for (int i = 0; i < Constants.PARSER_INSTANCEPOOL_SIZE; i++)
			parsers[i] = parserFactory.create();

		resourcePool = new SimpleObjectPool<>(Constants.PARSER_INSTANCEPOOL_SIZE, parsers);
	}

	public SimpleObjectPoolResource<AbstractDocumentParser> get() throws InterruptedException {
		return resourcePool.get();
	}
}