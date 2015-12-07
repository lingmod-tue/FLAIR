///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package webSearch;
//
//import com.google.gson.Gson;
//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.Reader;
//import java.io.UnsupportedEncodingException;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import webSearch.GoogleResults.Result;
//
///**
// *
// * @author Maria
// */
//public class Site {
//
//    private String address;
//    private String query;
//    private String charset;
//    private String start;
//    private String name;
//    private String elementID;
//    private ArrayList<WebPage> webpages;
//
//    public Site() {
//        address = "";
//        query = "";
//        charset = "";
//        start = "";
//        name = "";
//        elementID = "";
//        webpages = new ArrayList<>();
//    }
//
//    public Site(String name) {
//        this();
//        this.name = name;
//        this.elementID = name.split("\\s+")[0].split("\\.")[0]; // "ted.com videos" -> "ted.com" -> "ted"
//        webpages = crawlWebPages();
//    }
//
//    public Site(String name, String address, String query, String charset, String start) {
//        this();
//
//        this.address = address;
//        this.charset = charset;
//        this.start = start;
//        this.name = name;
//        this.elementID = name.split("\\s+")[0].split("\\.")[0]; // "ted.com videos" -> "ted.com" -> "ted"
//        this.query = name + " " + query;
//        webpages = crawlWebPages();
//        toFiles();
//    }
//
//    // getter and setter
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getQuery() {
//        return query;
//    }
//
//    public void setQuery(String query) {
//        this.query = query;
//    }
//
//    public String getCharset() {
//        return charset;
//    }
//
//    public void setCharset(String charset) {
//        this.charset = charset;
//    }
//
//    public String getStart() {
//        return start;
//    }
//
//    public void setStart(String start) {
//        this.start = start;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getElementID() {
//        return elementID;
//    }
//
//    public void setElementID(String elementID) {
//        this.elementID = elementID;
//    }
//
//    public ArrayList<WebPage> getWebpages() {
//        return webpages;
//    }
//
//    public void setWebpages(ArrayList<WebPage> webpages) {
//        this.webpages = webpages;
//    }
//
//    // private methods
//    private ArrayList<WebPage> crawlWebPages() {
//        System.out.println(this.query);
//        ArrayList<WebPage> pages = new ArrayList<>();
//
//        try {
//            URL url = new URL(address + URLEncoder.encode(query, charset));
//            Reader reader = new InputStreamReader(url.openStream(), charset);
//            GoogleResults results = new Gson().fromJson(reader, GoogleResults.class);
//            List<Result> rd = results.getResponseData().getResults();
//
//            //System.out.println("!!! Size: " + rd.size());
//            // Show title and URL of each results
//            for (int m = 0; m < 4; m++) {
//
//                // TODO: filter out the websites that don't allow crawling! 
//                String title = rd.get(m).getTitle();
//                String www = rd.get(m).getUrl();
//
//                String text = "";
//                WebPage page = null;
//                Document doc = null;
//                //Document flairPage = Jsoup.connect("").post();
//
//                if (query.toLowerCase().contains("ted.com")) {
//
//                    if (www.contains("ted.com/talks")) {
//
//                        // TED.com : try to get a transcript
//                        try {
//                            if (!www.endsWith("/transcript")) {
//                                www = www + "/transcript";
//                            }
//                            doc = Jsoup.connect(www).get();
//                            text = doc.text().trim();
//                            page = new WebPage(title, www, text);
//                            pages.add(page);
//                        } catch (Exception ex) {
//                            System.out.println("! TED: No transcript: " + www);
//                        }
//                    }
//
//                } // BigThink.com
//                else if (query.toLowerCase().contains("bigthink.com")) {
//                    if (www.contains("bigthink.com/videos")) {
//                        try {
//                            doc = Jsoup.connect(www).get();
//                            Element transcript = doc.select("#transcriptModal").first();
//                            page = new WebPage(title, www, transcript.html()); // get the text of the transcript
//                            pages.add(page);
//                        } catch (Exception ex) {
//                            System.out.println("! BigThink: No transcript: " + www);
//                        }
//                    }
//                } // WikiHow.com
//                //                else if (query.toLowerCase().contains("wikihow.com")) {
//                //                    page = new WebPage(title, www, text);
//                //                } // LifeHack.org
//                //                else if (query.toLowerCase().contains("lifehack.org")) {
//                //                    page = new WebPage(title, www, text);
//                //                } // HuffingtonPost.com // uk // ca
//                //                // huffingtonpost.com - huffingtonpost.co.uk  - huffingtonpost.ca
//                //                else if (query.toLowerCase().contains("huffingtonpost")) {
//                //                    page = new WebPage(title, www, text);
//                //                } // Reuters.com // uk // ca
//                //                // reuters.com - uk.reuters.com - ca.reuters.com
//                //                else if (query.toLowerCase().contains("reuters.com")) {
//                //                    page = new WebPage(title, www, text);
//                //                }
//                else { // search all sites
//                    try {
//                        doc = Jsoup.connect(www).get();
//                        text = doc.text().trim();
//                        page = new WebPage(title, www, text);
//                        pages.add(page);
//                    } catch (Exception ex) {
//                        System.out.println("! All: Couldn't fetch the page: " + www);
//                    }
//                }
//
//                // TODO: store every url in a database along with with the title
////                System.out.println("Title: " + title);
////                System.out.println("URL: " + www);
////                //System.out.println(text);
////                System.out.println("Tokens: " + page.getNumOfTokens());
////                System.out.println("Sentences: " + page.getNumOfSents());
////                System.out.println("Modals: " + page.getCountOfModals());
////                System.out.println("Possessives: " + page.getCountOfPossessives());
////                System.out.println("Relative pronouns: " + page.getCountOfRelativePronouns());
////                System.out.println();
//            }
//        } catch (UnsupportedEncodingException ex) {
//            Logger.getLogger(GoogleSearch.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (MalformedURLException ex) {
//            Logger.getLogger(GoogleSearch.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(GoogleSearch.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        return pages;
//    }
//
//    @Override
//    public String toString() {
//
//        //System.out.println("Num of webpages: " + getWebpages().size());
//        String output = "";
//
//        output += "<p>Query: " + getQuery() + "</p>";
//        output += "<p>Results: " + (Integer.valueOf(getStart()) + 1) + "-" + (Integer.valueOf(getStart()) + 4) + "</p><hr><br>";
//
//        int num = Integer.valueOf(getStart()) + 1;
//        for (WebPage p : getWebpages()) {
//            output += num + ". " + p.toString();
//            //output += "???";
//            num++;
//        }
//
//        return output;
//    }
//
//    private String toFiles() {
//
//        //System.out.println("Num of webpages: " + getWebpages().size());
//        String output = "";
//        String filenameToWrite = "";
//        FileWriter fileWriter = null;
//        BufferedWriter bw = null;
//
//        int num = Integer.valueOf(getStart()) + 1;
//        
//        
//        try {
//            for (WebPage p : getWebpages()) {
//                output = p.getText();
//            //output += "???";
//
//                // write the page into a separate file
//                String filenameToWriteURL = System.getProperty("user.dir") + "/top50peopleURLs/" + this.query.replaceAll(" ", "_") + ".txt";
//                filenameToWrite = System.getProperty("user.dir") + "/top50people/Jennifer_Lawrence/" + String.format("%03d", num) + ".txt";
//
//                // create an instance of FileWriter
//                fileWriter = new FileWriter(filenameToWrite);
//                FileWriter fileWriterURL = new FileWriter(filenameToWriteURL, true); // don't overwrite
//                
//                
//                // wrap FileWriter in BufferedWriter.
//                bw  = new BufferedWriter(fileWriter);
//                BufferedWriter bwURL  = new BufferedWriter(fileWriterURL);
//                
//                // write newLine into output file
//                bw.write(p.getWww() + "\n\n");
//                bw.write(output);
//                bw.close();
//                
//                bwURL.write(p.getWww());
//                bwURL.newLine();
//                bwURL.close();
//
//                num++;
//            }
//            
//        } catch (IOException ex) {
//            System.out.println("Error writing to file '"
//                    + filenameToWrite);
//        }
//
//        return output;
//    }
//
//}
