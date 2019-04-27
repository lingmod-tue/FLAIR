/*
 * Copyright 2003 Jayson Falkner (jayson@jspinsider.com)
 * This code is from "Servlets and JavaServer pages; the J2EE Web Tier",
 * http://www.jspbook.com. You may freely use the code both commercially
 * and non-commercially. If you like the code, please pick up a copy of
 * the book and help support the authors, development of more free code,
 * and the JSP/Servlet/J2EE community.
 */
package com.flair.server.filters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GzipFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		if (req instanceof HttpServletRequest) {
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) res;
			String ae = request.getHeader("accept-encoding");
			if (ae != null && ae.contains("gzip")) {
				GzipResponseWrapper wrappedResponse = new GzipResponseWrapper(response);
				chain.doFilter(req, wrappedResponse);
				wrappedResponse.finishResponse();
				return;
			}
			chain.doFilter(req, res);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) {
		// noop
	}

	@Override
	public void destroy() {
		// noop
	}
}
