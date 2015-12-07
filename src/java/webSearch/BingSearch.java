/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webSearch;

/**
 *
 * @author Maria
 */
import java.util.ArrayList;
import java.util.List;
import net.billylieurance.azuresearch.AzureSearchResultSet;
import net.billylieurance.azuresearch.AzureSearchWebQuery;
import net.billylieurance.azuresearch.AzureSearchWebResult;
import static parsing.Constants.blacklistURLs;
import static parsing.Constants.NUM_OF_RESULTS;
import reranking.Document;
import FLAIRmain.FlairMain;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import parsing.Constants;
import parsing.DeepParserCoreNLP;

public class BingSearch {

    private List<Document> docs;
    private String query;
    private int noResults;

    /**
     * Default constructor.
     */
    public BingSearch() {
        query = "";
        noResults = NUM_OF_RESULTS;
        docs = new ArrayList<>();
    }

    public BingSearch(String query, int noResults) {
        this();
        this.query = query;
        this.noResults = noResults;
        crawl();
    }

    public BingSearch(String query) {
        this();
        this.query = query;
        crawl();
    }

    // getter and setter
    public List<Document> getDocs() {
        return docs;
    }

    public void setDocs(List<Document> docs) {
        this.docs = docs;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * Uses Bing API to crawl the web. Doesn't save files into .json.
     */
    private void crawl() {
        AzureSearchWebQuery aq = new AzureSearchWebQuery();
        int count = 1;

        ////// get JSON
        //aq.setAppid(AzureAppid.AZURE_APPID);
        //aq.setFormat(AZURESEARCH_FORMAT.JSON);
        String qPrefix = "about ";
        String qPostfix = " language:en";

        aq.setAppid("CV3dQG6gOI3fO9wOHdArFimFprbt1Q3ZjMzYGhJaTFA");
        aq.setQuery(qPrefix + query + qPostfix);
        // This example gets 15 results 
        for (int i = 1; i <= (noResults / 15); i++) {
//            FileWriter fileWriter = null;
//            BufferedWriter bw = null;
            aq.setPage(i);
            aq.doQuery();

            AzureSearchResultSet<AzureSearchWebResult> ars = aq.getQueryResult();

            // 
            for (AzureSearchWebResult anr : ars) {
                // create a Document object with preRank, title, url, urlToDisplay, snippet
                int preRank = count;
                String title = anr.getTitle();
                String url = anr.getUrl();
                String urlToDisplay = anr.getDisplayUrl();
                String snippet = anr.getDescription();

                boolean blacklist = false;
                for (String u : blacklistURLs) {
                    if (url.contains(u)) {
                        System.out.println("URL in Blacklist: " + url);
                        blacklist = true;
                    }
                }

                if (!blacklist) {
                    Document doc = new Document(this.query, preRank, title, url, urlToDisplay, snippet);
                    docs.add(doc);
                    
                    writeOneDoc(doc, count);

                    count++;
                }
            }

        }

        if (docs.size() < noResults) {
            aq.setPage((noResults / 15) + 1);
            aq.doQuery();

            AzureSearchResultSet<AzureSearchWebResult> ars = aq.getQueryResult();

            // 
            for (AzureSearchWebResult anr : ars) {
                // create a Document object with preRank, title, url, urlToDisplay, snippet
                int preRank = count;
                String title = anr.getTitle();
                String url = anr.getUrl();
                String urlToDisplay = anr.getDisplayUrl();
                String snippet = anr.getDescription();

                boolean blacklist = false;
                for (String u : blacklistURLs) {
                    if (url.contains(u)) {
                        System.out.println("URL in Blacklist: " + url);
                        blacklist = true;
                    }
                }

                if (!blacklist) {
                    Document doc = new Document(this.query, preRank, title, url, urlToDisplay, snippet);
                    docs.add(doc);
                    writeOneDoc(doc, count);

                    count++;
                }
            }
        }

//            for (Document doc : docs) {
//                System.out.println(doc.toString());
//            }
//            //Note that now, there is no aq.getRawResult() or aq.getQueryResult().
//            // They'll come back as null.
//            HttpEntity he = aq.getResEntity();
//
//            String JSONString = "";
//            try {
//                InputStream is = he.getContent();
//                InputStreamReader isr = new InputStreamReader(is);
//                StringBuilder sb = new StringBuilder();
//                BufferedReader br = new BufferedReader(isr);
//                String read = "";
//
//                while ((read = br.readLine()) != null) {
//                    sb.append(read);
//                }
//
//                JSONString = sb.toString();
//                String newJsonString = JSONString.substring(JSONString.indexOf("["), JSONString.indexOf("]")+1);
//                
//                
//
//                
//                Gson gson = new Gson();
//                Document[] docArray = gson.fromJson(newJsonString, Document[].class); // d -> results
//                docs = Arrays.asList(docArray);
//
//                for (Document doc : docs) {
//                    System.out.println(doc.lineToFile());
//                }
//                        
//                        
//                        
//            } catch (IllegalStateException e) {
//                //Do something with the exception
//                System.out.println("IllegalStateException");
//            } catch (IOException e) {
//                //Do something with the exception
//                System.out.println("IOException");
//            }
//                String filenameToWrite = Constants.PATH_TO_RESULTS + query.replaceAll(" ", "_") + ".json";
//                fileWriter = new FileWriter(filenameToWrite); // overwrite
//                bw = new BufferedWriter(fileWriter);
//                bw.write(JSONString);
//                bw.flush();
    }

    /**
     * Write Document objects into JSON file(s)
     */
    private void writeDocs(List<Document> theseDocs) {
        int count = 1;
        for (Document d : theseDocs) {
            writeOneDoc(d, count);
            count++;
        }
    }

    private void writeOneDoc(Document d, int count) {

        File dir1 = new File(Constants.PATH_TO_RESULTS);
        dir1.mkdir();

        File dir2 = new File(Constants.PATH_TO_RESULTS + this.query.replaceAll(" ", "_"));
        dir2.mkdir();

        File dir = new File(Constants.PATH_TO_RESULTS + this.query.replaceAll(" ", "_") + "/weights");
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

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(args[0]);

        BingSearch bs = new BingSearch(args[0]);

        String directoryName = Constants.PATH_TO_RESULTS + args[0].replaceAll(" ", "_") + "/weights";
        //ArrayList<String> filepaths = new ArrayList<>();
        List<Document> documents = new ArrayList<>();
        File f = new File(directoryName);
        System.out.println(directoryName);

        if (f.isDirectory()) {

            File[] allFiles = f.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File f, String name) {
                    return !name.toLowerCase().startsWith(".");
                }
            });

            Gson gson = new Gson();
            for (File aFile : allFiles) {
                System.out.println(aFile.getAbsolutePath());
                if (!aFile.isDirectory()) {
                    BufferedReader br = null;
                    try {
                        br = new BufferedReader(new FileReader(aFile));
                        //convert the json string to object
                        Document doc = gson.fromJson(br, Document.class);

                        documents.add(doc);

                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(DeepParserCoreNLP.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            br.close();
                        } catch (IOException ex) {
                            Logger.getLogger(DeepParserCoreNLP.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }

        System.out.println(documents.size());

        String output = "";
        for (int i = 0; i < documents.size(); i++) {
//        out += '<tr onclick="showSnapshot(' + i + ');"><td class="num_cell" style="width:2%;text-align:center;font-size:x-large;">' + jsonList[i].postRank + '<br><span style="color:lightgrey;font-size:small">(' + jsonList[i].preRank + ')</span></td><td class="text_cell" style="width:10%;"><div style="font-size:4pt;height:100px;overflow:scroll;color:lightblue;border:solid 1px lightgrey;border-radius:5px;padding:5px;">' + text + '</div></td><td  class="url_cell" style="width:50%"><a href="' + jsonList[i].url + '" target="_blank"><b>' + jsonList[i].title + '</b></a><br><span style="color:grey;font-size:smaller;">' + jsonList[i].urlToDisplay + '</span><br><span>' + jsonList[i].snippet + '</span></td></tr>';
            String out = "<tr onclick='log(\"click\",\"" + (i + 1) + "\")'><td class='num_cell' style='font-size:x-large;'>" + (i + 1) + "&nbsp;</td><td class='url_cell' style='width:40%'><a href='" + documents.get(i).getUrl() + "' target='_blank'><b>" + documents.get(i).getTitle() + "</b></a><br><span style='color:grey;font-size:smaller;'>" + documents.get(i).getUrlToDisplay() + "</span><br><span>" + documents.get(i).getSnippet() + "</span></td></tr>";

            output += out;
        }

//        List<String> names = Arrays.asList("Jennifer Lawrence");
//
////        List<String> names = Arrays.asList("Jennifer Lawrence",
////                "Tracy Morgan",
////                "Ray Rice",
////                "Tony Stewart",
////                "Iggy Azalea",
////                "Donald Sterling",
////                "Rene Zellweger",
////                "Jared Leto",
////                "Paul Walker",
////                "Cory Monteith",
////                "Aaron Hernandez",
////                "Adrian Peterson",
////                "Miley Cyrus",
////                "James Gandolfini",
////                "Paula Deen",
////                "Mindy McCready",
////                "Trayvon Martin",
////                "Amanda Bynes",
////                "Whitney Houston",
////                "Jeremy Lin",
////                "Amanda Todd",
////                "Michael Clark Duncan",
////                "Kate Middleton",
////                "One Direction",
////                "Morgan Freeman",
////                "Peyton Manning",
////                "Joe Paterno",
////                "Paul Ryan",
////                "Rebecca Black",
////                "Scotty McCreery",
////                "Kate Upton",
////                "Kreayshawn",
////                "Tyler The Creator",
////                "Ryan Dunn",
////                "Pippa Middleton",
////                "Hope Solo",
////                "Troy Davis",
////                "Justin Bieber",
////                "Lady Gaga",
////                "Lil Wayne",
////                "Barack Obama",
////                "Kim Kardashian",
////                "Drake",
////                "Eminem",
////                "Miley Cyrus",
////                "Nicki Minaj",
////                "Taylor Swift",
////                "Neil Patrick Harris",
////                "Nicole Kidman",
////                "James McAvoy");
//        for (String name : names) {
//
//            BingSearch search = new BingSearch(name, NUM_OF_RESULTS);
//
//        }
    }
}
