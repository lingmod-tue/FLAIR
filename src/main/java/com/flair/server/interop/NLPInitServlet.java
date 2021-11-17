
package com.flair.server.interop;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.flair.server.grammar.DefaultVocabularyList;
import com.flair.server.parser.KeywordSearcherInput;
import com.flair.server.pipelines.common.PipelineOp;
import com.flair.server.pipelines.gramparsing.GramParsingPipeline;
import com.flair.server.utilities.ServerLogger;
import com.flair.shared.grammar.Language;
import com.flair.shared.interop.ClientIdToken;

/**
 * Initializes performance-intensive NLP components
 */
public class NLPInitServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	private void initNLP() throws ServletException, IOException {		
		GramParsingPipeline.SearchCrawlParseOpBuilder builder = GramParsingPipeline.get().searchCrawlParse();
		builder.lang(Language.ENGLISH)
				.query("dance")
				.results(1)
				.keywords(new KeywordSearcherInput(DefaultVocabularyList.get(Language.ENGLISH)));

		PipelineOp<?, ?> newOp = builder.build();
		newOp.launch();
		ServerLogger.get().info("Pipelines initialized");
	} 

	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		initNLP();
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request  servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException      if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		initNLP();
	}
}
