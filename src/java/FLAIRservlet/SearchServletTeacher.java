/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FLAIRservlet;

import FLAIRmain.FlairMain;
import HTMLprocessing.HttpUrlConnection_Boilerpipe;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logging.UserBean;
import parsing.Constants;
import parsing.DeepParserCoreNLP;
import reranking.Document;
import webSearch.BingSearch;

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

        //        System.out.println("???");
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
            Logger.getLogger(BingSearch.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Document> documents = new ArrayList<>();
        if (sess.getAttribute("docs") != null) {
            documents = (List<Document>) sess.getAttribute("docs");
        }

        String stage = (String) sess.getAttribute("stage");
        

        // compare to the previous query - reset to web search if it is a new one
        if (stage != null && (!((String) sess.getAttribute("query")).equalsIgnoreCase(encodedQuery))) {
            stage = "";
        } 
        // if the query is the same
        // check if the docs contain constructions -> "parsed"
        else if (documents.size() > 0 && documents.get(0).getConstructions().size() > 0) {
            stage = "parsed";
        } 
        // check if the docs contain text -> "extracted"
        else if (documents.size() > 0 && documents.get(0).getReadabilityLevel() != null && (!documents.get(0).getReadabilityLevel().isEmpty())) {
            stage = "extracted";
        }

        
        if (stage != null && !stage.isEmpty()) {

            System.out.println("Stage: " + stage);
            System.out.println("Docs to extract: " + documents.size());

            switch (stage) {
                case "crawled":
                    System.out.println("Extracting...");
                    HttpUrlConnection_Boilerpipe textExtractor = new HttpUrlConnection_Boilerpipe(encodedQuery, documents);
                    documents = textExtractor.getDocs();
                    sess.setAttribute("stage", "extracted");
                    sess.setAttribute("docs", documents);
                    break;
                case "extracted":
                    System.out.println("Parsing...");
                    DeepParserCoreNLP parser = new DeepParserCoreNLP(encodedQuery, documents);
                    documents = parser.getDocs();
                    sess.setAttribute("stage", "parsed");
                    sess.setAttribute("docs", documents);
                    break;
                case "parsed":
                    System.out.println("New query... Searching...");
                    BingSearch bs = new BingSearch(encodedQuery);
                    documents = bs.getDocs();
                    //sess.setAttribute("stage", "crawled");
                    sess.setAttribute("docs", documents);
                    break;
                default:
                    break;
            }
        } else {
            System.out.println("Searching...");
            BingSearch bs = new BingSearch(encodedQuery);
            documents = bs.getDocs();
            sess.setAttribute("stage", "crawled");
            sess.setAttribute("docs", documents);
        }

//        // read files as json
//        String directoryName = Constants.PATH_TO_RESULTS + request.getParameter("query") + "/weights";
//        //ArrayList<String> filepaths = new ArrayList<>();
//        List<Document> documents = new ArrayList<>();
//        File f = new File(directoryName);
//        System.out.println(directoryName);
//
//        if (f.isDirectory()) {
//
//            File[] allFiles = f.listFiles(new FilenameFilter() {
//                @Override
//                public boolean accept(File f, String name) {
//                    return !name.toLowerCase().startsWith(".");
//                }
//            });
//
//            Gson gson = new Gson();
//            for (File aFile : allFiles) {
//                System.out.println(aFile.getAbsolutePath());
//                if (!aFile.isDirectory()) {
//                    BufferedReader br = null;
//                    try {
//                        br = new BufferedReader(new FileReader(aFile));
//                        //convert the json string to object
//                        Document doc = gson.fromJson(br, Document.class);
//
//                        documents.add(doc);
//
//                    } catch (FileNotFoundException ex) {
//                        Logger.getLogger(DeepParserCoreNLP.class.getName()).log(Level.SEVERE, null, ex);
//                    } finally {
//                        try {
//                            br.close();
//                        } catch (IOException ex) {
//                            Logger.getLogger(DeepParserCoreNLP.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    }
//                }
//            }
//        }
        System.out.println("Docs size: " + documents.size());

        String output = "";
        for (int i = 0; i < documents.size(); i++) {
//            String out = "<tr><td class='num_cell' style='font-size:x-large;'>" + (i + 1) + "&nbsp;</td>"
//                    + "<td class='url_cell' style='width:90%'>"
//                    + "<a href='" + documents.get(i).getUrl() + "' target='_blank'><b>" + documents.get(i).getTitle() + "</b></a>"
//                    + "<br><span style='color:grey;font-size:smaller;'>" + documents.get(i).getUrlToDisplay() + "</span>"
//                    + "<br><span>" + documents.get(i).getSnippet() + "</span>"
//                    + "</td></tr>";

            String out = "<tr><td class='num_cell' style='font-size:x-large;'>"
                    + (i + 1) + "&nbsp;<span style='color:lightgrey;font-size:small' title='original position in the rank'>(" + documents.get(i).getPreRank() + ")</span><br>"
                    + "<div class='helpful' style='font-size:small'>"
                    + "<span id='star_" + (i + 1) + "' title='Relevant/helpful?\nLet us know!' class='star glyphicon glyphicon-star-empty' onclick='log_feedback(\"false-" + (i + 1) + "\")'></span>  "
                    + "<br><span id='thanks_" + (i + 1) + "' class='thanks' hidden>Thanks!</span"
                    + "</div></td><td  class='url_cell' style='width:40%'><div><a href='" + documents.get(i).getUrl() + "' target='_blank'><b>" + documents.get(i).getTitle() + "</b></a></div>"
                    + "<div id='show_text_cell' title='Click to show text' onclick='showText(" + i + ");'><span style='color:grey;font-size:smaller;'>" + documents.get(i).getUrlToDisplay() + "</span><br><span>" + documents.get(i).getSnippet() + "</span></div></td></tr>";

            output += out;
        }

        if (output.isEmpty()) {
            output = "No results found. Try searching for something else.";
        }

        UserBean user = (UserBean) sess.getAttribute("user");
        if (user != null) {
            // log the query
            user.addQueries(encodedQuery);
            sess.setAttribute("query", encodedQuery);
//            request.setAttribute("output", output);
            sess.setAttribute("user", user);
            sess.setAttribute("output", output);
            //sess.setAttribute("docs", documents);
            //response.sendRedirect("index.jsp");

            File path = new File(Constants.PATH_TO_RESULTS);

            sess.setAttribute("docs_path", path.getAbsolutePath() + "/");

            System.out.println("Abs path: " + path.getAbsolutePath() + "/");

            // get current time
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date now = new Date();
            String currentTime = sdf.format(now);

            // write log into file
            try {
                File f = new File("teacher/");
                f.mkdir();

                FileWriter fw_user = new FileWriter("teacher/log_" + user.getFirstName() + "-" + user.getLastName() + ".tsv", true);
                FileWriter fw_all = new FileWriter("teacher/log.tsv", true);

                BufferedWriter bw_user = new BufferedWriter(fw_user);
                BufferedWriter bw_all = new BufferedWriter(fw_all);

                bw_user.write(encodedQuery + "\t" + currentTime);
                bw_user.newLine();
                bw_all.write(user.getFirstName() + "\t" + user.getLastName() + "\t" + encodedQuery + "\t" + currentTime);
                bw_all.newLine();

                bw_user.close();
                bw_all.close();
            } catch (IOException e) {
                System.out.println("IOException: " + e.getLocalizedMessage());
            }
        }
//        request.getRequestDispatcher("visual.jsp").forward(request, response);
        response.sendRedirect("visual.jsp");
    }

    /**
     * Write Document objects into JSON file(s)
     */
    private void writeDocs(String query, List<Document> theseDocs) {
        int count = 1;
        for (Document d : theseDocs) {
            writeOneDoc(query, d, count);
            count++;
        }
    }

    private void writeOneDoc(String query, Document d, int count) {
        File f1 = new File(Constants.PATH_TO_RESULTS);
        f1.mkdir();

        System.out.println(f1.getAbsolutePath());

        File f2 = new File(Constants.PATH_TO_RESULTS + query.replaceAll(" ", "_"));
        f2.mkdir();

        File dir = new File(Constants.PATH_TO_RESULTS + query.replaceAll(" ", "_") + "/weights");
        dir.mkdir();

        String outfile = dir.getAbsolutePath() + "/" + String.format("%03d", count) + ".json";

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String JSONString = gson.toJson(d);

        FileWriter fw = null;
        try {
            System.out.println("> Writing into file: " + outfile);
            fw = new FileWriter(outfile);

            fw.write(JSONString);
            fw.close();

        } catch (IOException ex) {
            Logger.getLogger(FlairMain.class.getName()).log(Level.SEVERE, null, ex);
        }
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
