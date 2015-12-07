/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parsing;

import java.util.ArrayList;
import java.util.List;
import static parsing.Constants.NUM_OF_RESULTS;
import reranking.Document;

/**
 *
 * @author Maria
 */
public class Construction {

    private String name;
    private int count; // count in the current document (reset after each doc)
    private int[] docFrequencies; // or tfDoc // number of occurrences in the top N documents 
    private double[] docRelFrequencies; // number of occurrences in the top N documents
    private double avRelFrequency; // average of all values in docFrequencies - can help in 'scaling' the constructions
    private ArrayList<OccurrencesInDoc> occurrences; // ordered list of occurrences in all top N documents

    private ArrayList<String> examples;

    private double weight; // frequency in query (1,2,3,4 or 5) // TODO get from the user!

    // for query??
    private int tf; // frequency in query (1,2,3,4 or 5) // TODO get from the user!
    private double tfWeighted; // 1 + log10(tf)

    private double[] docWeightedTf; // calculate from docFrequencies: 1 + log(docFreq) // normalize and update by: square root of sum_over(docFrew[i]-to-power-2)

    // calculate in the end
    private int df; // num of docs where this construction occurs (go through docFrequencies, ++ if >0)
    private double idf; // inverted doc frequency: log10(1 + N/df) (N=60) (smoothed!!!)
    private double tfidf; // final weight of this construction - is calculated in the end, based on the current corpus (not normalized! - not necessary)

    private boolean found; // for all docs for this query

    /**
     * Default constructor.
     */
    public Construction() {
        name = "";
        tf = 0; // 0 at first // TODO change when scaling constructs
        tfWeighted = 0;
        df = 0; // calculate later from docFrequencies
        //idf = Math.log10((double) NUM_OF_RESULTS / (double) df);
        idf = 0.0;
        tfidf = 0.0;
        count = 0;
        weight = 0.0;
        docFrequencies = new int[NUM_OF_RESULTS];
        docRelFrequencies = new double[NUM_OF_RESULTS];
        docWeightedTf = new double[NUM_OF_RESULTS];
        avRelFrequency = 0.0;
        examples = new ArrayList<>();
        found = false;
        occurrences = new ArrayList<>();

        // populate occurrences
        for (int i = 0; i < NUM_OF_RESULTS; i++) {
            occurrences.add(new OccurrencesInDoc(i));
        }
    }

    /**
     * Use this method for adding a new construction.
     *
     * @param name
     */
    public Construction(String name) {
        this();
        this.name = name;
    }

    public Construction(String name, int tf) {
        this();
        this.name = name;
        this.tf = tf;
        this.tfWeighted = 1 + Math.log(this.tf);
    }

    public Construction(String name, double tfidf) {
        this();
        this.name = name;
        this.tfidf = tfidf;
    }

    // getter and setter
    public double[] getDocWeightedTf() {
        return docWeightedTf;
    }

    public void setDocWeightedTf(double[] docWeightedTf) {
        this.docWeightedTf = docWeightedTf;
    }

    public ArrayList<String> getExamples() {
        return examples;
    }

    public void setExamples(ArrayList<String> examples) {
        this.examples = examples;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public double[] getDocRelFrequencies() {
        return docRelFrequencies;
    }

    public void setDocRelFrequencies(double[] docRelFrequencies) {
        this.docRelFrequencies = docRelFrequencies;
    }

    public int getTf() {
        return tf;
    }

    public void setTf(int tf) {
        this.tf = tf;
    }

    public double getTfWeighted() {
        return tfWeighted;
    }

    public void setTfWeighted(double tfWeighted) {
        this.tfWeighted = tfWeighted;
    }

    public int getDf() {
        return df;
    }

    public void setDf(int df) {
        this.df = df;
    }

    public double getIdf() {
        return idf;
    }

    public void setIdf(double idf) {
        this.idf = idf;
    }

    public double getTfidf() {
        return tfidf;
    }

    public void setTfidf(double tfidf) {
        this.tfidf = tfidf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int[] getDocFrequencies() {
        return docFrequencies;
    }

    public void setDocFrequencies(int[] docFrequencies) {
        this.docFrequencies = docFrequencies;
    }

    public double getAvRelFrequency() {
        return avRelFrequency;
    }

    public void setAvRelFrequency(double avRelFrequency) {
        this.avRelFrequency = avRelFrequency;
    }

    public ArrayList<OccurrencesInDoc> getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(ArrayList<OccurrencesInDoc> occurrences) {
        this.occurrences = occurrences;
    }

    // other public methods
    public void addDocOccurrence(OccurrencesInDoc ocInDoc) {
        this.occurrences.add(ocInDoc);
    }

    public void updateConstruction(int docNumber, double docLength) {
        // put it into doc frequencies
        docFrequencies[docNumber] = count;
        docWeightedTf[docNumber] = 1 + Math.log(count); // !!!
        if (docLength == 0) {
            docRelFrequencies[docNumber] = 0;
            docWeightedTf[docNumber] = 0;
        } else {
            docRelFrequencies[docNumber] = count / (double) docLength;
        }
    }

    public void calculateDocRelFrequencies(List<Document> docs) {

        double[] tmp = new double[NUM_OF_RESULTS];
        for (int i = 0; i < NUM_OF_RESULTS; i++) {
            if (docs.get(i).getNumDeps() == 0) {
                tmp[i] = 0;
            } else {
                tmp[i] = docFrequencies[i] / (double) docs.get(i).getNumDeps();
            }
        }
        docRelFrequencies = tmp;
    }

//    /**
//     * across ALL documents
//     */
//    public void calculateAvRelFrequency() {
//        int freq = 0;
//        for (int i = 0; i < this.getDocFrequencies().length; i++) {
//            int d = this.getDocFrequencies()[i];
//            double docL;
//            freq += d;
//        }
//        this.setAvRelFrequency(freq / (double) NUM_OF_RESULTS);
//    } 
    public void calculateWeight() {
        double w = 0.0;

        this.setTfidf(w);
    }

    // TODO add public methods for calculating df, idf, tfidf
    @Override
    public String toString() {
        String output = "Construction{" + "name = " + name + "; docFrequencies = ";
        for (int i = 0; i < docFrequencies.length; i++) {
            output += docFrequencies[i] + ", ";
        }
        output += "; relFrequencies = ";
        for (int i = 0; i < docRelFrequencies.length; i++) {
            output += docRelFrequencies[i] + ", ";
        }
        output += '}';
        return output;
    }

    public String lineToFile() {
        String out = this.name + ",";
        for (int i = 0; i < docRelFrequencies.length; i++) {
            out += docRelFrequencies[i] + ",";
        }
        out = out.substring(0, out.length() - 1); // get rid of the last comma
        return out;
    }

    // private methods
    /**
     * Add an occurrence: example, start, end.
     *
     * @param docNum
     * @param word
     * @param start
     * @param end
     */
    public void incrementCount(int docNum, String word, int start, int end) {

        // especially in case of dependencies (gov/dep): make sure start is smaller than end
        if (end < start) {
            int tmp = end;
            end = start;
            start = tmp;
        }

        Occurrence oc = new Occurrence(this.getName(), docNum, start, end, word);
        OccurrencesInDoc docOccs = this.occurrences.get(docNum); // pre-populated with NUM_OF_RESULTS results
        if (docOccs != null && (!docOccs.getOccurrences().contains(oc))) {

            boolean isFound = false;
            for (Occurrence o : docOccs.getOccurrences()) {
                // "instance"(example) might be different: root(ROOT, appeared) VS. nsubj(appeared,she)
                // but the indices & construction name will match
                // => don't duplicate!
                if (o.getStart() == oc.getStart() && o.getEnd() == oc.getEnd() && o.getConstruction().equalsIgnoreCase(oc.getConstruction())) {
                    isFound = true;
                }
            }

            if (!isFound) {

                if (this.name.equalsIgnoreCase("goingTo")) {
                    System.out.println("--- Going to increment count line 320; count : " + this.count + " --- ");

                    System.out.println("--- PRE docOccs size: " + docOccs.getOccurrences().size() + " --- ");
                }

                docOccs.addOccurrence(oc);
                this.occurrences.set(docNum, docOccs);

                this.count++;
                this.found = true;
                this.examples.add(word); // compare to occurrences to see if they work fine

                if (this.name.equalsIgnoreCase("goingTo")) {
                    System.out.println("--- Incremented count: line 332; count : " + this.count + " --- ");

                    System.out.println("--- POST docOccs size: " + docOccs.getOccurrences().size() + " --- ");
                }
            }
        } else {
            System.out.println("List of OccurrencesInDocs has not been populated.");
        }
    }

//    /**
//     * Add an occurrence: only example
//     * @param docNum
//     * @param word 
//     */
//    public void incrementCount(int docNum, String word) {
//        this.count++;
//        this.isFound = true;
//        this.examples.add(word); // compare to occurrences to see if they work fine
//        
//        Occurrence oc = new Occurrence(this.getName(), docNum, word);
//        OccurrencesInDoc docOccs = this.occurrences.get(docNum); // pre-populated with NUM_OF_RESULTS results
//        if (docOccs != null) {  
//            docOccs.addOccurrence(oc);
//            this.occurrences.set(docNum, docOccs);
//        } else {
//            System.out.println("List of OccurrencesInDocs has not been populated.");
//        }
//    }
}
