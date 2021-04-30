package com.flair.server.exerciseGeneration.downloadManagement;

import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.CSSParseException;
import org.w3c.css.sac.ErrorHandler;

/**
 * Error handler for CSSOMParser which does not do anything with errors.
 * Used to suppress console output
 */
public class CssParseErrorHandler implements ErrorHandler {
    @Override
    public void warning(CSSParseException exception) throws CSSException {}

    @Override
    public void error(CSSParseException exception) throws CSSException {}

    @Override
    public void fatalError(CSSParseException exception) throws CSSException {}
}