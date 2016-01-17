/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FLAIRservlet;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
        if (sess == null) {
            response.sendRedirect("visual.jsp");
            return;
        }

        if (request.getParameter("query") == null || ((String) request.getParameter("query")).isEmpty()) {
            sess.setAttribute("stage", "");
            response.sendRedirect("visual.jsp");
            System.out.println("No query?");
            return;
        }

        String encodedQuery = request.getParameter("query");
        try {
            byte ptext[] = request.getParameter("query").getBytes("ISO-8859-1");
            encodedQuery = new String(ptext, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
           
        }
	String output = "";
/*
	WebSearchAgent agent = WebSearchAgentFactory.create(WebSearchAgentFactory.SearchAgent.BING, Language.ENGLISH, encodedQuery, 20);
	agent.performSearch();
	List<SearchResult> searchResults = agent.getResults();
	AbstractPipelineOperation operation = MasterJobPipeline.get().scheduleJob(Language.ENGLISH, searchResults);
	DocumentCollection docCol = operation.getOutput();
	String rootPath = "webapps/FLAIR/results/";
	File f1 = new File(rootPath);
        f1.mkdir();
        File f2 = new File(rootPath + encodedQuery.replaceAll(" ", "_"));
        f2.mkdir();
	File dir = new File(rootPath + encodedQuery.replaceAll(" ", "_") + "/weights");
        dir.mkdir();
	
	docCol.serialize(new JSONWriter(), dir.getAbsolutePath());
        
        for (int i = 0; i < searchResults.size(); i++) {

            String out = "<tr><td class='num_cell' style='font-size:x-large;'>"
                    + (i + 1) + "&nbsp;<span style='color:lightgrey;font-size:small' title='original position in the rank'>(" + i + ")</span><br>"
                    + "<div class='helpful' style='font-size:small'>"
                    + "<span id='star_" + (i + 1) + "' title='Relevant/helpful?\nLet us know!' class='star glyphicon glyphicon-star-empty' onclick='log_feedback(\"false-" + (i + 1) + "\")'></span>  "
                    + "<br><span id='thanks_" + (i + 1) + "' class='thanks' hidden>Thanks!</span"
                    + "</div></td><td  class='url_cell' style='width:40%'><div><a href='" + searchResults.get(i).getURL() + "' target='_blank'><b>" + searchResults.get(i).getTitle() + "</b></a></div>"
                    + "<div id='show_text_cell' title='Click to show text' onclick='showText(" + i + ");'><span style='color:grey;font-size:smaller;'>" + searchResults.get(i).getDisplayURL()+ "</span><br><span>" + searchResults.get(i).getSnippet() + "</span></div></td></tr>";

            output += out;
        }
*/	

        if (output.isEmpty()) {
            output = "No results found. Try searching for something else.";
        }
	
	sess.setAttribute("output", output);
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
