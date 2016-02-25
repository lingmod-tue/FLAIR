/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FLAIRservlet;


import com.flair.crawler.SearchResult;
import com.flair.crawler.WebSearchAgent;
import com.flair.crawler.WebSearchAgentFactory;
import com.flair.grammar.GrammaticalConstruction;
import com.flair.grammar.Language;
import com.flair.parser.AbstractDocument;
import com.flair.parser.DocumentCollection;
import com.flair.parser.DocumentConstructionData;
import com.flair.taskmanager.AbstractPipelineOperation;
import com.flair.taskmanager.MasterJobPipeline;
import com.flair.utilities.FLAIRLogger;
import com.flair.utilities.JSONWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 *
 * @author Maria
 */
public class SearchServletTeacher extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        HttpSession sess = request.getSession();
        if (sess == null) 
	{
            response.sendRedirect("visual.jsp");
            return;
        }
	
	sess.setAttribute("stage", "");
	sess.setAttribute("docs_path", "");
	String currQuery = request.getParameter("query");

        if (currQuery == null || currQuery.isEmpty())
	{
            response.sendRedirect("visual.jsp");
            return;
        }

        String encodedQuery = request.getParameter("query");
        try {
            byte ptext[] = request.getParameter("query").getBytes("ISO-8859-1");
            encodedQuery = new String(ptext, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
        }

	sess.setAttribute("query", encodedQuery);
	String rootPath = "./" + request.getContextPath() + "/results/";
	String pathToQuery = rootPath + "/" + encodedQuery.replace(" ", "_") + "/";
	File dataDir = new File(pathToQuery);
	File weightsDir = new File(pathToQuery + "weights/");
	String pathToCSV = dataDir.getPath() + "/all_documents_" + encodedQuery.replace(" ", "_") + ".csv";
	
	if (dataDir.exists() && weightsDir.exists())
	{
	    int numFiles = weightsDir.listFiles().length;
	    sess.setAttribute("stage", "parsed");
	    sess.setAttribute("docs_path", weightsDir.getPath().replace('/', '\\').replace("\\", "\\\\"));
	    sess.setAttribute("csv_path", pathToCSV.replace('/', '\\').replace("\\", "\\\\"));
	    sess.setAttribute("num_files", numFiles);
            response.sendRedirect("visual.jsp");
            return;
	}

	WebSearchAgent agent = WebSearchAgentFactory.create(WebSearchAgentFactory.SearchAgent.BING, Language.ENGLISH, encodedQuery, 25);
	agent.performSearch();
	List<SearchResult> searchResults = agent.getResults();
	
	long startTime = System.currentTimeMillis();
	AbstractPipelineOperation op = MasterJobPipeline.get().parseSearchResults(Language.ENGLISH, searchResults);
	DocumentCollection docCol = op.getOutput();
	long endTime = System.currentTimeMillis();
	
	JSONWriter outputPrinter = new JSONWriter();
	int counter = 1;
	for (AbstractDocument itr1 : docCol)
	{
	    outputPrinter.writeObject(itr1.getSerializable(counter), String.format("%03d", counter), weightsDir.getPath());
	    counter++;
	}
	
	try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathToCSV))) 
	{
	     String header = "document,";
	     for (GrammaticalConstruction itr1 : GrammaticalConstruction.values())
		 header += itr1.getLegacyID()+ ",";

	     header += "# of sentences,# of words,readability score";
	     bw.write(header);
	     bw.newLine();

	     int i = 1;
	     for (AbstractDocument itr1 : docCol)
	     {
		 String outString = "" + i++ + ",";
		 for (GrammaticalConstruction itr2 : GrammaticalConstruction.values())
		 {
		     DocumentConstructionData data = itr1.getConstructionData(itr2);
		     outString += data.getRelativeFrequency() + ",";
		 }

		 outString += itr1.getNumSentences() + "," + itr1.getNumDependencies() + "," + itr1.getReadabilityScore();
		 bw.write(outString);
		 bw.newLine();
	     }
	} catch (IOException ex) {
	}

	FLAIRLogger.get().trace(op.toString());
	FLAIRLogger.get().trace("Docs parsed for query '"+ encodedQuery + "' completed in " + (endTime - startTime) + " ms");
        
	
	
	int numFiles = docCol.size();
	sess.setAttribute("stage", "parsed");
	sess.setAttribute("docs_path", weightsDir.getPath().replace('/', '\\').replace("\\", "\\\\"));
	sess.setAttribute("csv_path", pathToCSV.replace('/', '\\').replace("\\", "\\\\"));
	sess.setAttribute("num_files", numFiles);
        response.sendRedirect("visual.jsp");
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);

//        String pathToJavascript = "/Users/Maria/Sites/FLAIR/web/js/functions_logger.js";
//        
//
//        ScriptEngineManager manager = new ScriptEngineManager();
//        ScriptEngine engine = manager.getEngineByName("JavaScript");
//        try {
//            // read script file
//            engine.eval(Files.newBufferedReader(Paths.get(pathToJavascript), StandardCharsets.UTF_8));        
////        String script = "var document = new Object (); ";
////        engine.eval(script);
//            Invocable inv = (Invocable) engine;
//// call function from script file
//        inv.invokeFunction("search_experiment",request.getParameter("query"));
//        } catch (ScriptException ex) {
//            Logger.getLogger(BingSearch.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NoSuchMethodException ex) {
//            Logger.getLogger(BingSearch.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(BingSearch.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
