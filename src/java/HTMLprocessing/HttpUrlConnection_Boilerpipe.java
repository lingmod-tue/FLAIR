/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HTMLprocessing;

/**
 *
 * @author Maria
 */
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.l3s.boilerpipe.extractors.DefaultExtractor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.xml.sax.InputSource;
import parsing.Constants;
import parsing.DeepParserCoreNLP;
import reranking.Document;

public class HttpUrlConnection_Boilerpipe {

    private String query;
    private List<Document> docs; // all top N documents collected for this query

    public HttpUrlConnection_Boilerpipe() {
        query = "";
        docs = new ArrayList<>();
    }

    public HttpUrlConnection_Boilerpipe(String query, List<Document> docs) {
        this();
        this.query = query;
        this.docs = docs;

        try {
            updateDocs(); // add text to Docs and write into files
        } catch (IOException ex) {
            Logger.getLogger(HttpUrlConnection_Boilerpipe.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // getter and setter
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<Document> getDocs() {
        return docs;
    }

    public void setDocs(List<Document> docs) {
        this.docs = docs;
    }

    /**
     * Get a rough estimate of the level.
     */
    private void setLevel(Document d) {
            
            String text = d.getText();
            int chars = text.replaceAll(" ", "").length();
            int words = text.split(" ").length;
            int sents = text.split("[.!?]").length;
            
            System.out.println("Set Level: ");
            System.out.println("Text: " + text);
            System.out.println("Text length: " + d.getDocLength());
            System.out.println("Chars: " + chars);
            System.out.println("Word: " + words);
            System.out.println("Sents: " + sents);
            
            
            d.setNumChars(chars);
            d.setNumDeps(words);
            d.setNumSents(sents);
            

            double score = -10.0;
        //int chars = d.getText().trim().replaceAll(" ", "").length(); // incl. punctuation
            //System.out.println("Chars/numChars: " + chars + "/" + d.getNumChars());

            if (d.getDocLength() > 100 && d.getNumDeps() != 0 && d.getNumSents() != 0 && d.getNumChars() != 0) {
                score = 4.71 * ((double) d.getNumChars() / (double) d.getNumDeps()) + 0.5 * ((double) d.getNumDeps() / (double) d.getNumSents()) - 21.43;
//            score = 4.71 * ((double) d.getNumChars() / (double) d.getNumDeps()) + 0.5 * ((double) d.getNumDeps() / (double) d.getNumSents());
                score = Math.ceil(score);
            }

            // set the readability level: LEVEL-a, LEVEL-b, LEVEL-c
            String level = "";
            if (score < 6) { // LEVEL-a
                level = "LEVEL-a";
            } else if (6 <= score && score <= 10) { // LEVEL-b
                level = "LEVEL-b";
            } else { // LEVEL-c
                level = "LEVEL-c";
            }
            
            System.out.println("Score: " + score);
            
            d.setReadabilityLevel(level);

    }

    private void updateDocs() throws IOException {
        int count = 1;

        for (Document doc : docs) {
            String link = doc.getUrl();

            URL url = null;
            try {
                url = new URL(link); // for boilerpipe
            } catch (MalformedURLException ex) {
                Logger.getLogger(HttpUrlConnection_Boilerpipe.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                URL obj = new URL(link);
                HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

                if (conn != null) {
                    conn.setReadTimeout(5000);
                    conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
                    conn.addRequestProperty("User-Agent", "Mozilla/4.76");
                    conn.addRequestProperty("Referer", "google.com");

                    System.out.println("Request URL ... " + link);

                    boolean redirect = false;

                    // normally, 3xx is redirect
                    int status = conn.getResponseCode();
                    if (status != HttpURLConnection.HTTP_OK) {
                        if (status == HttpURLConnection.HTTP_MOVED_TEMP
                                || status == HttpURLConnection.HTTP_MOVED_PERM
                                || status == HttpURLConnection.HTTP_SEE_OTHER) {
                            redirect = true;
                        }
                    }

                    if (redirect) {

                        // get redirect url from "location" header field
                        String newUrl = conn.getHeaderField("Location");

                        // get the cookie if need, for login
                        String cookies = conn.getHeaderField("Set-Cookie");

                        // open the new connnection again
                        conn = (HttpURLConnection) new URL(newUrl).openConnection();
                        conn.setRequestProperty("Cookie", cookies);
                        conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
                        conn.addRequestProperty("User-Agent", "Mozilla/4.76");
                        conn.addRequestProperty("Referer", "google.com");

                        System.out.println("Redirect to URL : " + newUrl);

                        url = new URL(newUrl); // for boilerpipe
                    }
//
//                    BufferedReader in = new BufferedReader(
//                            new InputStreamReader(conn.getInputStream()));
//                    String inputLine;
//                    StringBuilder html = new StringBuilder(); // the whole document in html
//
//                    while ((inputLine = in.readLine()) != null) {
//                        // if inputLine does not finish with a ".", "?" or "!"
//                        html.append(inputLine);
//                    }
//                    in.close();
//
//                    // update the doc
//                    doc.setHtml(html.toString()); // html from HttpUrlConnection

                    // do not fetch html
                    doc.setHtml("");

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Boilerpipe
            StringBuilder newText = new StringBuilder();
            try {

                // TODO: check for encoding!!!
                InputSource is = new InputSource();
                is.setEncoding("UTF-8");
                is.setByteStream(url.openStream());

                // using Default Extractor from Boilerpipe
                String text = DefaultExtractor.INSTANCE.getText(is);
                // Facilitate parsing by putting a full stop at the end of the line
                String[] sents = text.split("\n");
                for (String s : sents) {
                    newText.append(s);
                    if (!(s.endsWith(".") || s.endsWith("!") || s.endsWith("?") || s.endsWith("\""))) {
                        newText.append(".\n");
                    } else {
                        newText.append("\n");
                    }
                }
                doc.setText(newText.toString()); // text from Boilerpipe

                // set doc length
                if (!text.isEmpty()) {
                    int text_len = text.split(" ").length;
                    doc.setDocLength(text_len);
                }
                // set level
                setLevel(doc);

            } catch (Exception ex) {
                Logger.getLogger(HttpUrlConnection_Boilerpipe.class.getName()).log(Level.SEVERE, null, ex);
            }

            writeOneDoc(doc, count);
            count++;
        }
    }

    private void writeOneDoc(Document d, int count) {

        File dir0 = new File(Constants.PATH_TO_RESULTS);
        dir0.mkdir();

        File dir1 = new File(Constants.PATH_TO_RESULTS + this.query.replaceAll(" ", "_"));
        dir1.mkdir();

        File dirPath = new File(Constants.PATH_TO_RESULTS + this.query.replaceAll(" ", "_") + "/weights");
        dirPath.mkdir();

        String outfile = dirPath.getAbsolutePath() + "/" + String.format("%03d", count) + ".json";

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String JSONString = gson.toJson(d);

        FileWriter fw = null;
        try {
            System.out.println("> Writing into file: " + outfile);
            fw = new FileWriter(outfile);

            fw.write(JSONString);
            fw.close();

        } catch (IOException ex) {
            Logger.getLogger(HttpUrlConnection_Boilerpipe.class.getName()).log(Level.SEVERE, null, ex);
        }

//        // write text into .txt files 
//        String dirPath2 = Constants.PATH_TO_RESULTS + this.query.replaceAll(" ", "_") + "/text";
//        File dir2 = new File(dirPath2);
//        dir2.mkdir();
//
//        String outfile2 = dirPath2 + "/" + String.format("%03d", count) + ".txt";
//
//        String stringToWrite2 = d.getText();
//
//        FileWriter fw2 = null;
//        try {
//            System.out.println("> Writing into file: " + outfile2);
//            fw2 = new FileWriter(outfile2);
//
//            fw2.write(stringToWrite2);
//            fw2.close();
//
//        } catch (IOException ex) {
//            Logger.getLogger(HttpUrlConnection_Boilerpipe.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public static void main(String[] args) {

        String directoryName = Constants.PATH_TO_RESULTS + "Jennifer_Lawrence/parsed";
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
        System.out.println(documents.size() + " documents.");

        HttpUrlConnection_Boilerpipe httpText = new HttpUrlConnection_Boilerpipe("Jennifer Lawrence", documents);
    }

}
