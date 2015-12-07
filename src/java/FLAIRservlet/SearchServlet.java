/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FLAIRservlet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import logging.UserBean;
import reranking.Document;
import webSearch.BingSearch;

/**
 *
 * @author Maria
 */
//@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {

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

        String encodedQuery = "";
        try {
            byte ptext[] = request.getParameter("query").getBytes("ISO-8859-1");
            encodedQuery = new String(ptext, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(BingSearch.class.getName()).log(Level.SEVERE, null, ex);
        }

//        System.out.println("???");
        BingSearch bs = new BingSearch(encodedQuery);
        List<Document> documents = bs.getDocs();

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
        System.out.println(documents.size());

        String output = "";
        for (int i = 0; i < documents.size(); i++) {
//        out += '<tr onclick="showSnapshot(' + i + ');"><td class="num_cell" style="width:2%;text-align:center;font-size:x-large;">' + jsonList[i].postRank + '<br><span style="color:lightgrey;font-size:small">(' + jsonList[i].preRank + ')</span></td><td class="text_cell" style="width:10%;"><div style="font-size:4pt;height:100px;overflow:scroll;color:lightblue;border:solid 1px lightgrey;border-radius:5px;padding:5px;">' + text + '</div></td><td  class="url_cell" style="width:50%"><a href="' + jsonList[i].url + '" target="_blank"><b>' + jsonList[i].title + '</b></a><br><span style="color:grey;font-size:smaller;">' + jsonList[i].urlToDisplay + '</span><br><span>' + jsonList[i].snippet + '</span></td></tr>';
            String out = "<tr><td class='num_cell' style='font-size:x-large;'>" 
//                    + (i + 1) + "&nbsp;"
                    + "<div class='helpful' style='font-size:small'>"
                    + "<span id='star_" + (i + 1) + "' title='Relevant/helpful?\nLet us know!' class='star glyphicon glyphicon-star-empty' onclick='log(\"false-" + (i + 1) + "\")'></span>  "
                    + "</td><td class='url_cell' style='width:90%'>"
                    + "<a href='" + documents.get(i).getUrl() + "' target='_blank'><b>" + documents.get(i).getTitle() + "</b></a>"
                    + "<br><span style='color:grey;font-size:smaller;'>" + documents.get(i).getUrlToDisplay() + "</span>"
                    + "<br><span>" + documents.get(i).getSnippet() + "</span>"
                    + "</td></tr>";

            output += out;
        }

        if (output.isEmpty()) {
            output = "No results found. Try searching for something else.";
        }

        HttpSession sess = request.getSession();
        UserBean user = (UserBean) sess.getAttribute("user");
        if (user == null) {
            System.out.println("id: " + sess.getId());
            System.out.println("user: " + sess.getAttribute("user"));
            System.out.println("username: " + sess.getAttribute("hello_name"));
            System.out.println("output: " + sess.getAttribute("output"));
            //response.sendRedirect("index.jsp");
        } else {
            // log the query
            user.addQueries(encodedQuery);
            sess.setAttribute("query", encodedQuery);
            request.setAttribute("output", output);
            sess.setAttribute("user", user);
            sess.setAttribute("output", output);
            //response.sendRedirect("index.jsp");

            // write into file
            try {
                File f = new File("learner/");
                f.mkdir();

                FileWriter fw_user = new FileWriter("learner/log_" + user.getFirstName() + "-" + user.getLastName() + ".tsv", true);
                FileWriter fw_all = new FileWriter("learner/log.tsv", true);

                BufferedWriter bw_user = new BufferedWriter(fw_user);
                BufferedWriter bw_all = new BufferedWriter(fw_all);

                bw_user.write(encodedQuery);
                bw_user.newLine();
                bw_all.write(user.getFirstName() + "\t" + user.getLastName() + "\t" + encodedQuery);
                bw_all.newLine();

                bw_user.close();
                bw_all.close();
            } catch (IOException e) {
                System.out.println("IOException: " + e.getLocalizedMessage());
            }
        }
        request.getRequestDispatcher("index.jsp").forward(request, response);
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
