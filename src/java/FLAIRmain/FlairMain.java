package FLAIRmain;


import HTMLprocessing.HttpUrlConnection_Boilerpipe;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import parsing.Constants;
import static parsing.Constants.NUM_OF_RESULTS;
import parsing.DeepParserCoreNLP;
import reranking.Document;
import reranking.Ranker;
import webSearch.BingSearch;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Maria
 */
public class FlairMain {

    private String query;
    private int noResults;
    private List<Document> docs;

    /**
     * Default constructor
     */
    public FlairMain() {
        this.query = "";
        this.noResults = 0;
        this.docs = new ArrayList<>();
    }

    /**
     *
     * @param query
     */
    public FlairMain(String query) {
        this();
        this.query = query;
        this.noResults = NUM_OF_RESULTS;
        
        
        
        // create a directory for this query
        String dirPath1 = Constants.PATH_TO_RESULTS;
        File dir1 = new File(dirPath1);
        dir1.mkdir();
        
        
        String dirPath = Constants.PATH_TO_RESULTS + this.query.replaceAll(" ", "_");
        File dir = new File(dirPath);
        dir.mkdir();
        
        System.out.println(dir.getAbsolutePath());

        // is called from the UI on click of "SEARCH"
        // crawl via Bing API (JSON in settings), get top 100 results, save each to a Document object
        // !!! ignore the pages from Blacklist: tmz.com???
        System.out.println(">>> Bing Search <<<");
        BingSearch search = new BingSearch(this.query, this.noResults);
        docs = search.getDocs();

        writeDocs(this.docs);
        
        
        // use HttpUrlConnection and Boilerplate to go to url and fetch the html and text 
        // and add it into the Document object
        System.out.println(">>> HTML/Text extraction <<<");
        HttpUrlConnection_Boilerpipe textExtractor = new HttpUrlConnection_Boilerpipe(this.query, this.docs);
        docs = textExtractor.getDocs();
        
        writeDocs(this.docs);
        
        // sort the docs based on their length (to start with the shortest)
        //Collections.sort(docs, new DocLengthComparator());


        // parse the text using DeepParserCoreNLP (and save)
        System.out.println(">>> Parsing <<<");
        DeepParserCoreNLP parser = new DeepParserCoreNLP(this.query, this.docs);
        docs = parser.getDocs();
        
        writeDocs(this.docs);

        // is called from the UI on click of "UPDATE"
        // setGramWeights();
        
        // calculate all the values based on the data in each Document and the query
        // assign postRank to each Document
        System.out.println(">>> Ranking <<<");
        Ranker ranker = new Ranker(this.query, this.docs);
        docs = ranker.getDocs(); // already sorted
        
        writeDocs(this.docs);
        
        writeIntoDocFile();
        
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
    
    
    private void writeIntoDocFile() {
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            System.out.println("\n\nWriting documents into file...\n");
            File pathToFile = new File(Constants.PATH_TO_RESULTS + this.query.replaceAll(" ", "_") + "/all_documents.csv");

            System.out.println(pathToFile.getAbsolutePath());

            fw = new FileWriter(pathToFile, true);//don't overwrite
            bw = new BufferedWriter(fw);
            // write all Constructions as lines into a file
            // header
            String header = "document,";
            for (int i = 0; i < docs.get(0).getConstructions().size(); i++) {
                header += docs.get(0).getConstructions().get(i) + ",";
            }
            header += "# of sentences,# of words,readability score";
//            header = header.substring(0, header.length() - 1); // get rid of the last comma
            // write the header into csv file
            bw.write(header);
            bw.newLine();
            System.out.println(header);
            for (Document doc : docs) {
                String toFile = doc.lineToFile() + "," + doc.getNumSents() + "," + doc.getNumDeps() + "," + doc.getReadabilityScore();
                // write into the csv file
                bw.write(toFile);
                bw.newLine();
                System.out.println(toFile);
                bw.flush();
            }

        } catch (IOException ex) {
            Logger.getLogger(DeepParserCoreNLP.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(DeepParserCoreNLP.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    /**
     * Saves the settings into a csv file: "structure,weight"
     */
    private void setGramWeights() {
        
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        //List<String> queries = Arrays.asList("Jennifer Lawrence");

        // load the list of queries into ArrayList<String>
        List<String> queries = Arrays.asList(
                "Jennifer Lawrence",
                "Tracy Morgan",
                "Ray Rice",
                "Tony Stewart",
                "Iggy Azalea",
                "Donald Sterling",
                "Rene Zellweger",
                "Jared Leto",
                "Paul Walker",
                "Cory Monteith",
                "Aaron Hernandez",
                "Adrian Peterson",
                "Miley Cyrus",
                "James Gandolfini",
                "Paula Deen",
                "Mindy McCready",
                "Trayvon Martin",
                "Amanda Bynes",
                "Whitney Houston",
                "Jeremy Lin",
                "Amanda Todd",
                "Michael Clark Duncan",
                "Kate Middleton",
                "One Direction",
                "Morgan Freeman",
                "Peyton Manning",
                "Joe Paterno",
                "Paul Ryan",
                "Rebecca Black",
                "Scotty McCreery",
                "Kate Upton",
                "Kreayshawn",
                "Tyler The Creator",
                "Ryan Dunn",
                "Pippa Middleton",
                "Hope Solo",
                "Troy Davis",
                "Justin Bieber",
                "Lady Gaga",
                "Lil Wayne",
                "Barack Obama",
                "Kim Kardashian",
                "Drake",
                "Eminem",
                "Miley Cyrus",
                "Nicki Minaj",
                "Taylor Swift",
                "Neil Patrick Harris",
                "Nicole Kidman",
                "James McAvoy");
        // for each query: creat FlairMain(name)
        for (String query : queries) {
            FlairMain flair = new FlairMain(query);
        }
    }

}

class DocLengthComparator implements Comparator<Document> {

    @Override
    public int compare(Document doc1, Document doc2) {
        return doc1.getText().length() - doc2.getText().length();
    }
}

class DocPreRankComparator implements Comparator<Document> {

    @Override
    public int compare(Document doc1, Document doc2) {
        return doc1.getPreRank() - doc2.getPreRank();
    }
}


