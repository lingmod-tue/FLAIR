
package com.flair.server.document;

/**
 * Interface used to create AbstractDocument objects
 */
public interface AbstractDocumentFactory {
	public AbstractDocument create(AbstractDocumentSource source);
}
