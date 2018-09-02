
package com.flair.server.crawler.impl;

import java.util.List;

/**
 * Indirection interface b'ween search API implementations and the rest of FLAIR
 */
public interface AbstractSearchAgentImpl {
	public List<? extends AbstractSearchAgentImplResult> performSearch();
}
