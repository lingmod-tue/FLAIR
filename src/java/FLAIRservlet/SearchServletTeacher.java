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
import com.flair.parser.SearchResultDocumentSource;
import com.flair.taskmanager.AbstractPipelineOperation;
import com.flair.taskmanager.MasterJobPipeline;
import com.flair.utilities.FLAIRLogger;
import com.flair.utilities.JSONWriter;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
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
	
	Object prevQuery = sess.getAttribute("query");
	String currQuery = request.getParameter("query");

        if (currQuery == null || currQuery.isEmpty() ||
	    (prevQuery != null && currQuery.equals((String)prevQuery)))
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
	String rootPath = request.getContextPath() + "/results/";
	String pathToQuery = rootPath + "/" + encodedQuery.replace(" ", "_") + "/";
	File dataDir = new File(pathToQuery);
	File weightsDir = new File(pathToQuery + "weights/");

	if (dataDir.exists() && weightsDir.exists())
	{
	    File[] jsonFiles = weightsDir.listFiles();
	    JsonParser builder = new JsonParser();
	    String displayResults = "";
	    int i = 0;
	    for (File itr : jsonFiles)
	    {
		try (BufferedReader br = new BufferedReader(new FileReader(itr))) 
		{
		    JsonElement root = builder.parse(br);
		    String title = root.getAsJsonObject().get("title").getAsString();
		    String url = root.getAsJsonObject().get("url").getAsString();
		    String urlDisp = root.getAsJsonObject().get("urlToDisplay").getAsString();
		    String snippet = root.getAsJsonObject().get("snippet").getAsString();

		    String out = "<tr><td class='num_cell' style='font-size:x-large;'>"
		    + (i + 1) + "&nbsp;<span style='color:lightgrey;font-size:small' title='original position in the rank'>(" + i + ")</span><br>"
		    + "<div class='helpful' style='font-size:small'>"
		    + "<span id='star_" + (i + 1) + "' title='Relevant/helpful?\nLet us know!' class='star glyphicon glyphicon-star-empty' onclick='log_feedback(\"false-" + (i + 1) + "\")'></span>  "
		    + "<br><span id='thanks_" + (i + 1) + "' class='thanks' hidden>Thanks!</span"
		    + "</div></td><td  class='url_cell' style='width:40%'><div><a href='" + url + "' target='_blank'><b>" + title + "</b></a></div>"
		    + "<div id='show_text_cell' title='Click to show text' onclick='showText(" + i + ");'><span style='color:grey;font-size:smaller;'>" + urlDisp + "</span><br><span>" + snippet + "</span></div></td></tr>";

		    displayResults += out;
		    i++;
		} catch (IOException ex) {
		}
	    }
	    
	    sess.setAttribute("output", displayResults);
	    sess.setAttribute("stage", "parsed");
	    sess.setAttribute("docs_path", dataDir.getPath().replace('/', '\\').replace("\\", "\\\\"));
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
	
	docCol.serialize(new JSONWriter(), weightsDir.getPath());
	try (BufferedWriter bw = new BufferedWriter(new FileWriter(dataDir.getPath() + "/all_documents_" + encodedQuery.replace(" ", "_") + ".csv"))) 
	{
	     String header = "document,";
	     for (GrammaticalConstruction itr1 : GrammaticalConstruction.values())
		 header += itr1.toString() + ",";

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
        
	String output = "";
	int i = 0;
        for (AbstractDocument itr : docCol)
	{
	    SearchResult source = ((SearchResultDocumentSource)itr.getDocumentSource()).getSearchResult();
            String out = "<tr><td class='num_cell' style='font-size:x-large;'>"
                    + (i + 1) + "&nbsp;<span style='color:lightgrey;font-size:small' title='original position in the rank'>(" + i + ")</span><br>"
                    + "<div class='helpful' style='font-size:small'>"
                    + "<span id='star_" + (i + 1) + "' title='Relevant/helpful?\nLet us know!' class='star glyphicon glyphicon-star-empty' onclick='log_feedback(\"false-" + (i + 1) + "\")'></span>  "
                    + "<br><span id='thanks_" + (i + 1) + "' class='thanks' hidden>Thanks!</span"
                    + "</div></td><td  class='url_cell' style='width:40%'><div><a href='" + source.getURL() + "' target='_blank'><b>" + source.getTitle() + "</b></a></div>"
                    + "<div id='show_text_cell' title='Click to show text' onclick='showText(" + i + ");'><span style='color:grey;font-size:smaller;'>" + source.getDisplayURL()+ "</span><br><span>" + source.getSnippet() + "</span></div></td></tr>";

            output += out;
	    i++;
        }
	

        if (output.isEmpty()) {
            output = "No results found. Try searching for something else.";
        }
	
	sess.setAttribute("output", output);
	sess.setAttribute("stage", "parsed");
	sess.setAttribute("docs_path", dataDir.getPath().replace('/', '\\').replace("\\", "\\\\"));
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
