/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License. To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 */
package com.flair.crawler.impl;

import java.util.List;

/**
 * Indirection interface b'ween search API implementations and the rest of FLAIR
 * @author shadeMe
 */
public interface AbstractSearchAgentImpl
{
    public List<? extends AbstractSearchAgentImplResult>	    performSearch();
}
