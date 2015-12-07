/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reranking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import parsing.Constants;

/**
 * Read each document from a .json file or get it from the List of Documents
 *
 * @author Maria
 */
public class Ranker {

    private String query;
    private HashMap<String, Integer> gramWeights; // read from file
    private List<Document> docs;
    private List<Document> emptyDocs; // add at the end of the list in the same order

    private double avDocLength;
    private double avWordLength;
    private double avSentLength;
    private double avTreeDepth;
    private double avReadability;

    /**
     * Default constructor.
     */
    public Ranker() {
        query = "";
        gramWeights = new HashMap<>();
        docs = new ArrayList<>();
        emptyDocs = new ArrayList<>();

        avDocLength = 0.0;
        avWordLength = 0.0;
        avSentLength = 0.0;
        avTreeDepth = 0.0;
        avReadability = 0.0;
    }

    /**
     * Set the docs on-the-go.
     *
     * @param query
     * @param allDocs
     */
    public Ranker(String query, List<Document> allDocs) {
        this();
        this.query = query;
        for (Document doc : allDocs) {
            if (doc.getDocLength() > 100) {
                this.docs.add(doc);
            } else {
                // add empty documents at the end of the list later
                this.emptyDocs.add(doc);
            }
        }

        // calculate the weights
        if (this.docs.size() > 0) {
            System.out.println("Docs size: " + this.docs.size());
            System.out.println("Empty Docs size: " + emptyDocs.size());

            for (Document d : this.docs) {
                // update the counts
                this.avDocLength += d.getDocLength();
                this.avSentLength += d.getAvSentLength();
                this.avWordLength += d.getAvWordLength();
                this.avTreeDepth += d.getAvTreeDepth();

            }
            // update the counts for the current num of docs processed
            this.avDocLength = this.avDocLength / this.docs.size();
            this.avSentLength = this.avSentLength / this.docs.size();
            this.avWordLength = this.avWordLength / this.docs.size();
            this.avTreeDepth = this.avTreeDepth / this.docs.size();

            for (int i = 0; i < this.docs.size(); i++) {
                //System.out.println("PRE >>> " + this.docs.get(i).getTotalWeight());
                calculateTotalWeight(this.docs.get(i), i);
                this.avReadability += this.docs.get(i).getReadabilityScore();

            }
            this.avReadability = this.avReadability / this.docs.size();

            System.out.println("Av readability score: " + avReadability);
            System.out.println("Av doc length: " + avDocLength);
            System.out.println("Av sent length: " + avSentLength);
            System.out.println("Av word length: " + avWordLength);
            System.out.println("Av tree depth: " + avTreeDepth);

            // sort the docs based on their weights
            Collections.sort(this.docs, new DocWeightComparator());

            // set postRanks for non-empty docs
            for (int j = 0; j < this.docs.size(); j++) {
                Document document = this.docs.get(j);
                document.setPostRank(j + 1);
                this.docs.set(j, document);
            }

            // add empty docs
            int lastRank = this.docs.size() + 1;
            for (int k = 0; k < emptyDocs.size(); k++) {
                this.docs.add(null);
                Document doc = emptyDocs.get(k);
                doc.setPostRank(k + lastRank);
                calculateTotalWeight(doc, k + lastRank - 1);

                //docs.add(doc);
            }

            // print out all docs
            for (int j = 0; j < this.docs.size(); j++) {
                Document document = this.docs.get(j);

                System.out.println("\n" + document.getTitle());
                System.out.println(document.getUrl());
                System.out.println(document.getSnippet());
                System.out.println(">>> Doc " + document.getPreRank());
                System.out.println("Rank weight >>> " + document.getRankWeight());
                System.out.println("Readability Score >>> " + document.getReadabilityScore());
                System.out.println("Readability Level >>> " + document.getReadabilityLevel());
                System.out.println("Text weight >>> " + document.getTextWeight());
                System.out.println("Post rank >>> " + document.getPostRank());

                // writeOneDoc(document);
            }

        } else {
            System.out.println("No docs!");
        }

    }

    /**
     * Set the docs from json files.
     *
     * @param query
     * @param pathname The path to the directory with all the results for the
     * current query
     */
    public Ranker(String query, String pathname) {
        this();
        this.query = query;

        Gson gson = new Gson();

        File directory = new File(pathname);
        if (directory.isDirectory()) {
            File[] allFiles = directory.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File f, String name) {
                    return ((!name.toLowerCase().startsWith(".")) && (!name.equalsIgnoreCase("weights")));
                }
            });

            for (File aFile : allFiles) {
                try {
                    BufferedReader br = new BufferedReader(
                            new FileReader(aFile));
                    //convert the json string to object
                    Document doc = gson.fromJson(br, Document.class);
                    // only add documents longer than 100 words (for statistics)
                    if (doc.getDocLength() > 100) {
                        this.docs.add(doc);
                    } else {
                        // add empty documents at the end of the list later
                        this.emptyDocs.add(doc);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        // calculate the weights
        if (this.docs.size() > 0) {
            System.out.println("Docs size: " + this.docs.size());
            System.out.println("Empty Docs size: " + emptyDocs.size());

            for (Document d : this.docs) {
                // update the counts
                this.avDocLength += d.getDocLength();
                this.avSentLength += d.getAvSentLength();
                this.avWordLength += d.getAvWordLength();
                this.avTreeDepth += d.getAvTreeDepth();

            }
            // update the counts for the current num of docs processed
            this.avDocLength = this.avDocLength / this.docs.size();
            this.avSentLength = this.avSentLength / this.docs.size();
            this.avWordLength = this.avWordLength / this.docs.size();
            this.avTreeDepth = this.avTreeDepth / this.docs.size();

            for (int i = 0; i < this.docs.size(); i++) {
                //System.out.println("PRE >>> " + this.docs.get(i).getTotalWeight());
                calculateTotalWeight(this.docs.get(i), i);
                this.avReadability += this.docs.get(i).getReadabilityScore();

            }
            this.avReadability = this.avReadability / this.docs.size();

            System.out.println("Av readability score: " + avReadability);
            System.out.println("Av doc length: " + avDocLength);
            System.out.println("Av sent length: " + avSentLength);
            System.out.println("Av word length: " + avWordLength);
            System.out.println("Av tree depth: " + avTreeDepth);

            // sort the docs based on their weights
            Collections.sort(this.docs, new DocWeightComparator());

            // set postRanks for non-empty docs
            for (int j = 0; j < this.docs.size(); j++) {
                Document document = this.docs.get(j);
                document.setPostRank(j + 1);
                this.docs.set(j, document);
            }

            // add empty docs
            int lastRank = this.docs.size() + 1;
            for (int k = 0; k < emptyDocs.size(); k++) {
                this.docs.add(null);
                Document doc = emptyDocs.get(k);
                doc.setPostRank(k + lastRank);
                calculateTotalWeight(doc, k + lastRank - 1);

                //this.docs.add(doc);
            }

            // print out all docs
            for (int j = 0; j < this.docs.size(); j++) {
                Document document = this.docs.get(j);

                System.out.println("\n" + document.getTitle());
                System.out.println(document.getUrl());
                System.out.println(document.getSnippet());
                System.out.println(">>> Doc " + document.getPreRank());
                System.out.println("Rank weight >>> " + document.getRankWeight());
                System.out.println("Bennoehr    >>> " + document.getReadabilityBennoehr());
                System.out.println("ARI index   >>> " + document.getReadabilityARI());
                System.out.println("Readability Score >>> " + document.getReadabilityScore());
                System.out.println("Readability Level >>> " + document.getReadabilityLevel());
                System.out.println("Text weight >>> " + document.getTextWeight());
                System.out.println("TOTAL    > " + document.getTextWeight());
                System.out.println("PostRank > " + document.getPostRank());

                writeOneDoc(document);
            }

        } else {
            System.out.println("No docs!");
        }

    }

    // getter and setter
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public HashMap<String, Integer> getGramWeights() {
        return gramWeights;
    }

    public void setGramWeights(HashMap<String, Integer> gramWeights) {
        this.gramWeights = gramWeights;
    }

    public List<Document> getDocs() {
        return docs;
    }

    public void setDocs(List<Document> docs) {
        this.docs = docs;
    }

    public List<Document> getEmptyDocs() {
        return emptyDocs;
    }

    public void setEmptyDocs(List<Document> emptyDocs) {
        this.emptyDocs = emptyDocs;
    }

    public double getAvDocLength() {
        return avDocLength;
    }

    public void setAvDocLength(double avDocLength) {
        this.avDocLength = avDocLength;
    }

    public double getAvWordLength() {
        return avWordLength;
    }

    public void setAvWordLength(double avWordLength) {
        this.avWordLength = avWordLength;
    }

    public double getAvSentLength() {
        return avSentLength;
    }

    public void setAvSentLength(double avSentLength) {
        this.avSentLength = avSentLength;
    }

    public double getAvTreeDepth() {
        return avTreeDepth;
    }

    public void setAvTreeDepth(double avTreeDepth) {
        this.avTreeDepth = avTreeDepth;
    }

    public double getAvReadability() {
        return avReadability;
    }

    public void setAvReadability(double avReadability) {
        this.avReadability = avReadability;
    }

    // private methods
    /**
     * Write into a .json file
     *
     * @param d
     */
    private void writeOneDoc(Document d) {
        
        (new File(Constants.PATH_TO_RESULTS + this.query.replaceAll(" ", "_") + "/weights/")).mkdir();
        
        String outfile = Constants.PATH_TO_RESULTS + this.query.replaceAll(" ", "_") + "/weights/" + String.format("%03d", d.getPostRank()) + ".json";

        
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String JSONString = gson.toJson(d);

        FileWriter fw = null;
        try {
            //System.out.println("> Writing into file: " + outfile);
            fw = new FileWriter(outfile);

            fw.write(JSONString);
            fw.close();

        } catch (IOException ex) {
            Logger.getLogger(Ranker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param d
     * @param ind
     */
    private void calculateTotalWeight(Document d, int ind) {

        double readBasic = calculateReadabilityScoreBasic(d);
        
        double bennoehr = calculateBennoehrReadability(d);
        d.setReadabilityBennoehr(bennoehr);

        double ari = calculateARIReadability(d);
        d.setReadabilityARI(ari);

        double rank = calculateRankWeight(d);
        d.setRankWeight(rank);

//        double gram = calculateGrammarScore(d);
//        d.setGramScore(gram); // depends on the settings (read them from file inside the method)

        double textWeight = calculateTextWeight(d);
        d.setTextWeight(textWeight);

        // set the main readability score
        double read = ari; // change to Sowmya's!!!
        if (read < 0) {
            read = 0.0;
        }
        d.setReadabilityScore(read / 100); // normalised by average readability score?
        // set the readability level: LEVEL-a, LEVEL-b, LEVEL-c
        String level = "";
        if (read < 6) { // LEVEL-a
            level = "LEVEL-a";
        } else if (6 <= read && read <= 10) { // LEVEL-b
            level = "LEVEL-b";
        } else { // LEVEL-c
            level = "LEVEL-c";
        }
        d.setReadabilityLevel(level);

        ///// the reranking formula!
        // readability: no weight for sentence length: unless we are sure we've only retrieved an informative text (othersie too short or too long sentences mess it up)
        // rank: 1 - log(rank) yields a positive number between 1 and 0 for the top 10 results, a negative for the others
        // text length: second to biggest weight: not smaller than 100 words, lengths from 100 up: the bigger, the better (Readability index takes care of sentence length)
        // grammar score: depends on the query, final number 
        double weight = textWeight; // TODO change to Bennoehr
//        double weight = d.getReadabilityScore() + rank + gram; // removed textWeight

        d.setTotalWeight(weight);

        this.docs.set(ind, d);

    }

    private void calculateTfNorm() {
        // TODO
    }

    private double calculateTextWeight(Document d) {
        double textWeight = -0.5;
        if (d.getDocLength() > 100) {
            textWeight = d.getDocLength() / this.avDocLength; // bigger texts -> more informative
        }
        return textWeight;
    }

    /**
     * Do not use sentence length as a parameter. Use Bennoehr score without
     * sentence length?
     *
     * @param d
     * @return
     */
    private double calculateReadabilityScoreBasic(Document d) {
        return (1 - (d.getAvWordLength() / this.avWordLength));
    }

    /**
     * ARI: the Automated Readability Index: Senter, R. J., & Smith, E. A.
     * (1967). Excluding punctuation marks. No weight for sentence length!
     * (messed up in web texts)
     *
     * @return
     */
    private double calculateARIReadability(Document d) {
        double score = -10.0;
        //int chars = d.getText().trim().replaceAll(" ", "").length(); // incl. punctuation
        //System.out.println("Chars/numChars: " + chars + "/" + d.getNumChars());

        if (d.getDocLength() > 100 && d.getNumDeps() != 0 && d.getNumSents() != 0 && d.getNumChars() != 0) {
            score = 4.71 * ((double) d.getNumChars() / (double) d.getNumDeps()) + 0.5 * ((double) d.getNumDeps() / (double) d.getNumSents()) - 21.43;
//            score = 4.71 * ((double) d.getNumChars() / (double) d.getNumDeps()) + 0.5 * ((double) d.getNumDeps() / (double) d.getNumSents());
            score = Math.ceil(score);
        }

        return score;
    }

    /**
     * Bennoehr's thesis: Readability Score for language learners
     *
     * @return
     */
    private double calculateBennoehrReadability(Document d) {
        double score = 0.0;
        if (d.getNumDeps() != 0 && d.getNumSents() != 0 && d.getNumChars() != 0) {
            double conjEasyDiff = ((double) (d.getFrequencies().get(d.getConstructions().indexOf("simpleConjunctions")) - d.getFrequencies().get(d.getConstructions().indexOf("advancedConjunctions")))) / (double) d.getNumDeps();
            score = 137.6 + 47.3 * Math.log((double) d.getNumDeps() / (double) d.getNumSents()) + 19.2 * (d.getNumChars() / d.getNumDeps()) - 447.3 * conjEasyDiff;
            //score = (d.getNumChars() / d.getNumDeps()) - conjEasyDiff;

        }
        return score;
    }

    /**
     * Assign a weight based on this doc's initial rank. The top 10 results get
     * a positive weight, all the rest get a negative weight. However, the
     * numbers are very small.
     *
     * @return
     */
    private double calculateRankWeight(Document d) {
        double weight = 0.0;
        // preRank should be > 0!
        weight = 1 - Math.log10(d.getPreRank()); // the first 10 results get weights 1-...-0, others get a negative score
        return weight;
    }

    /**
     *
     * @param d
     * @return grammatical weight
     */
    private double calculateGrammarScore(Document d) {
        double score = 0.0;

        // get the settings from a file
        BufferedReader br = null;
        try {
            // read the current settings from file
            br = new BufferedReader(
                    new FileReader(Constants.PATH_TO_SETTINGS));
            String line = "";
            while ((line = br.readLine()) != null) {
                String name = line.split(",")[0];
                Integer weight = Integer.valueOf(line.split(",")[1]);
                double relFreq = d.getConstructions().indexOf(name);
                score += weight * relFreq; // update the grammarScore
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Ranker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Ranker.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(Ranker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return score;
    }

    public static void main(String[] args) {
        String query = "Jennifer Lawrence";
        String path = Constants.PATH_TO_RESULTS + "Jennifer_Lawrence/parsed";
        Ranker ranker = new Ranker(query, path);

    }
}

class DocWeightComparator implements Comparator<Document> {

    @Override
    public int compare(Document doc1, Document doc2) {
        if (Double.isNaN(doc1.getTotalWeight()) || Double.isNaN(doc2.getTotalWeight())) {
            return 0;  // handle NaN
        }
        return Double.compare(doc2.getTotalWeight(), doc1.getTotalWeight());
    }
}
