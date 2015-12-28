/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reranking;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import parsing.Construction;
import parsing.Occurrence;

/**
 * Represents the 
 * 
 * @author Maria
 */
public class Document implements java.io.Serializable {

    private String query;

    private int preRank; // should be > 0!
    private int postRank;

    private String title; // from Bing API
    private String url; // from Bing API
    private String urlToDisplay; // from Bing API
    private String snippet;  // from Bing API
    private String html; // from HttpUrlConnection
    private String text; // from Boilerpipe

    // distribution of grammatical structures in this doc
    // !!! all of the 
    private ArrayList<String> constructions; // the order will be taken from DeepParserCoreNLP initializeConstructions()
    private ArrayList<Double> relFrequencies; // normalised by the non-fancy doc length
    private ArrayList<Integer> frequencies; // or raw tf
    private ArrayList<Double> tfNorm;  // 1 + log(frequencies[i]) // or weighted tf (only for this doc)

    private ArrayList<Occurrence> highlights;

    // normalised by doc length (calculated: divided by square root from the sum of all "terms" (each tf to 2nd power))
    private ArrayList<Double> tfIdf; // "term" across the corpus // _increases_ with the rarety of the term
    private double docLenTfIdf; // the fancy length : square root from the sum of all constructions (each tf to 2nd power)

    private double docLength; // num of tokens 

    private double readabilityScore;
    private String readabilityLevel;
    private double readabilityARI;
    private double readabilityBennoehr;
    private double textWeight;
    private double rankWeight;
    private double gramScore; // overall query score! (the Score)
    private double totalWeight; // idf (not normalized!)

    private int numChars;
    private int numSents;
    private int numDeps; // dependencies

    private double avWordLength; // do not include punctuation
    private double avSentLength;
    private double avTreeDepth;

    // constructors
    /**
     * Default constructor.
     */
    public Document() {

        query = "";
        title = "";
        url = "";
        urlToDisplay = "";
        text = "";
        snippet = "";
        html = "";

        constructions = new ArrayList<>();
        frequencies = new ArrayList<>();
        relFrequencies = new ArrayList<>();

        highlights = new ArrayList<>();

        preRank = 0;
        postRank = 0;
        tfNorm = new ArrayList<>(); // depends on the query?
        tfIdf = new ArrayList<>();
        docLenTfIdf = 0.0;

        docLength = 0.0;

        readabilityScore = 0.0;
        readabilityLevel = "";
        readabilityARI = 0.0; // use a simple index
        readabilityBennoehr = 0.0; // takes conjunctions into account
        textWeight = 0.0;
        rankWeight = 0.0;
        gramScore = 0.0; // depends on the query
        totalWeight = 0.0;

        numChars = 0;
        numSents = 0;
        numDeps = 0; // dependencies

        avWordLength = 0.0; // do not include punctuation
        avSentLength = 0.0;
        avTreeDepth = 0.0;

    }

    /**
     * Constructor.
     *
     * @param query
     * @param preRank
     */
    public Document(String query, int preRank) {
        this();
        this.query = query;
        this.preRank = preRank;
    }

    /**
     * To initialize when web-searching
     *
     * @param query
     * @param preRank
     * @param title
     * @param url
     * @param urlToDisplay
     * @param snippet
     */
    public Document(String query, int preRank, String title, String url, String urlToDisplay, String snippet) {
        this();
        this.query = query;
        this.preRank = preRank;
        this.title = title;
        this.url = url;
        this.urlToDisplay = urlToDisplay;
        this.snippet = snippet;
    }

    /**
     * To initialize when boilerplating.
     *
     * @param query
     * @param preRank
     * @param title
     * @param url
     * @param urlToDisplay
     * @param text
     * @param html
     * @param snippet
     */
    public Document(String query, int preRank, String title, String url, String urlToDisplay, String text, String html, String snippet) {
        this();
        this.query = query;
        this.preRank = preRank;
        this.title = title;
        this.url = url;
        this.urlToDisplay = urlToDisplay;
        this.snippet = snippet;
        this.text = text;
        this.html = html;
    }

    // <editor-fold desc="getter and setter methods">
  



    public String getQuery() {
        return query;
    }



    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }


    public String getUrlToDisplay() {
        return urlToDisplay;
    }

    public String getText() {
        return text;
    }


    public String getSnippet() {
        return snippet;
    }

    public String getReadabilityLevel() {
        return readabilityLevel;
    }

    public void setReadabilityLevel(String readabilityLevel) {
        this.readabilityLevel = readabilityLevel;
    }
    


    public double getDocLength() {
        return docLength;
    }

    public void setDocLength(double docLength) {
        this.docLength = docLength;
    }


    public int getNumChars() {
        return numChars;
    }

    public void setNumChars(int numChars) {
        this.numChars = numChars;
    }

    public int getNumSents() {
        return numSents;
    }

    public void setNumSents(int numSents) {
        this.numSents = numSents;
    }

    public int getNumDeps() {
        return numDeps;
    }

    public void setNumDeps(int numDeps) {
        this.numDeps = numDeps;
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
// </editor-fold>
    
    // other public methods
    @Override
    public String toString() {
	return "";
      //  return "Document{" + "query=" + this.getQuery() + ", preRank=" + this.getPreRank() + ", postRank=" + this.getPostRank() + ", title=" + this.getTitle() + ", url=" + this.getUrl() + ", urlToDisplay=" + this.getUrlToDisplay() + ", snippet=" + this.getSnippet() + ", html=" + this.getHtml() + ", text=" + this.getText() + ", constructions=" + this.getConstructions() + ", relFrequencies=" + this.getRelFrequencies() + ", occurrences=" + this.getFrequencies() + ", tfsNorm=" + this.getTfNorm() + ", docLength=" + this.getDocLength() + ", readabilityScore=" + this.getReadabilityARI() + ", rankWeight=" + this.getRankWeight() + ", gramScore=" + this.getGramScore() + ", totalWeight=" + this.getTotalWeight() + ", numChars=" + this.getNumChars() + ", numSents=" + this.getNumSents() + ", numDeps=" + this.getNumDeps() + ", avWordLength=" + this.getAvWordLength() + ", avSentLength=" + this.getAvSentLength() + ", avTreeDepth=" + this.getAvTreeDepth() + '}';
    }

    public Document addConstructionsToDoc(HashMap<String, Construction> allConstructs, int docNum) {

        System.out.println(this.constructions.toString());

        ArrayList<ArrayList<Integer>> tmpHighlight = new ArrayList<>();

//        for (int i = 0 ; i < allConstructs.size(); i++) {
//            this.frequencies.add(0);
//            this.relFrequencies.add(0.0);
//        }
        for (Map.Entry<String, Construction> entry : allConstructs.entrySet()) {

            if (entry.getKey().equalsIgnoreCase("goingTo")) {
                System.out.println("--- Adding GOING_TO to doc: line 429 --- ");
            }

            // add the highlight indices
            ArrayList<Occurrence> occs = entry.getValue().getOccurrences().get(docNum).getOccurrences(); // occurrences of this construction in this doc
            for (Occurrence oc : occs) {
                highlights.add(oc);
//                ArrayList<Integer> curItem = new ArrayList<>();
//                curItem.add(oc.getStart());
//                curItem.add(oc.getEnd());
//                tmpHighlight.add(curItem);
            }

            // check if it is already there
            if (!this.constructions.contains(entry.getKey())) {
                this.constructions.add(entry.getKey());
                this.frequencies.add(entry.getValue().getDocFrequencies()[docNum]);
                this.relFrequencies.add(entry.getValue().getDocRelFrequencies()[docNum]);
            } else {
                System.out.print(entry.getKey());
                int ind = this.constructions.indexOf(entry.getKey());
                System.out.print(ind);
                this.frequencies.set(ind, entry.getValue().getDocFrequencies()[docNum]);
                this.relFrequencies.set(ind, entry.getValue().getDocRelFrequencies()[docNum]);
            }

        }

        for (int i = 0; i < this.frequencies.size(); i++) {
            Double tmp = 0.0;
            if (this.frequencies.get(i) > 0) {
                tmp = 1 + Math.log(this.frequencies.get(i));
            }
            this.tfNorm.add(tmp);
        }
        // tfIdf
        double sumOfPowers = 0.0;
        double squareRoot = 0.0;
        for (int j = 0; j < this.tfNorm.size(); j++) {
            if (this.tfNorm.get(j) != 0) { // Math.pow will be 0 anyway
                sumOfPowers += Math.pow(this.tfNorm.get(j), 2);
            }
        }
        if (sumOfPowers > 0) {
            squareRoot = Math.sqrt(sumOfPowers);
        }
        this.docLenTfIdf = squareRoot; // the fancy doc length
        // populate tfIdf normalized
        for (int k = 0; k < this.tfNorm.size(); k++) {
            if (this.docLenTfIdf > 0) {
                this.tfIdf.add(tfNorm.get(k) / this.docLenTfIdf);
            } else {
                this.tfIdf.add(0.0);
            }
        }

        return this;
    }

    /**
     * Highlight the text given a set of constructions
     *
     * @param constructs
     * @return
     */
    public String highlightText(ArrayList<String> constructs) {
        String newText = "";

        ArrayList<Occurrence> occs = new ArrayList<>(); // occurrences of THESE constructionS in this doc

        for (String name : constructs) {

            for (Occurrence o : this.highlights) {
                if (o.getConstruction().equalsIgnoreCase(name) && (!occs.contains(o))) {
                    occs.add(o);
                }
            }
        }

        // // sort the occurrences based on their (end) indices
        String[][] allIndices = new String[occs.size() * 2][occs.size() * 2];
        for (int i = 0; i < occs.size(); i++) {
            Occurrence o = occs.get(i);
            int start = o.getStart();
            int end = o.getEnd();

            String spanStart = "<span style='background-color:lightyellow;' title='" + o.getConstruction() + "'>";
            String spanEnd = "</span>";

            allIndices[i] = new String[]{spanStart, String.valueOf(start) + "-" + String.valueOf(end)};
            allIndices[occs.size() * 2 - 1 - i] = new String[]{spanEnd, String.valueOf(end)};

        }
        // sort allIndices - start and end in descending order 
        Arrays.sort(allIndices, new Comparator<String[]>() {
            @Override
            public int compare(final String[] ind1, final String[] ind2) {

                String indexSt1 = ind1[1];
                String indexSt2 = ind2[1];
                String indexEnd1 = "";
                String indexEnd2 = "";

                Integer s1 = -5;
                Integer s2 = -5;
                Integer e1 = -5;
                Integer e2 = -5;

                // indexSt1 can be either of form start-end or just end
                if (indexSt1.contains("-")) {
                    indexEnd1 = indexSt1.substring(indexSt1.indexOf("-") + 1);
                    indexSt1 = indexSt1.substring(0, indexSt1.indexOf("-"));

                }
                if (indexSt2.contains("-")) {
                    indexEnd2 = indexSt2.substring(indexSt2.indexOf("-") + 1);
                    indexSt2 = indexSt2.substring(0, indexSt2.indexOf("-"));

                }

                s1 = Integer.valueOf(indexSt1);
                s2 = Integer.valueOf(indexSt2);

                int sComp = s2.compareTo(s1);

                if (sComp != 0) {
                    return sComp;
                } else {
                    if (!indexEnd1.isEmpty()) {

                        if (!indexEnd2.isEmpty()) {
                            e1 = Integer.valueOf(indexEnd1);
                            e2 = Integer.valueOf(indexEnd2);

                            Integer l1 = Math.abs(e1 - s1);
                            Integer l2 = Math.abs(e2 - s2);

                            return l1.compareTo(l2);
                        } else {
                            return sComp;
                        }
                    } else if (!indexEnd2.isEmpty()) {
                        if (!indexEnd1.isEmpty()) {
                            e1 = Integer.valueOf(indexEnd1);
                            e2 = Integer.valueOf(indexEnd2);

                            Integer l1 = Math.abs(e1 - s1);
                            Integer l2 = Math.abs(e2 - s2);

                            return l1.compareTo(l2);
                        } else {
                            return sComp;
                        }
                    } else {
                        return sComp;
                    }
                }
            }
        });
        // insert span tags into the text : start from the end 
        String docText = this.text;
        int prevStartInd = -1; // prev ind of a start-tag
        int prevEndInd = -1; // prev ind of a start-tag
        String prevConstruct = "";
        String prevStartTag = "";

        for (String[] ind : allIndices) {

            int insertHere = -10;

            String curItem = ind[1];
            int tmpEnd = -1;
            int tmpStart = -1;
            // indexSt1 can be either of form start-end or just end
            if (curItem.contains("-")) {
                tmpStart = Integer.valueOf(curItem.substring(0, curItem.indexOf("-")));
                tmpEnd = Integer.valueOf(curItem.substring(curItem.indexOf("-") + 1));
                insertHere = tmpStart;
            } else {
                insertHere = Integer.valueOf(curItem);
            }

            String tag = ind[0];

            // show several constructions on mouseover, ONLY if they fully overlap (e.g., complex sentence, direct question)
            if (tag.contains("<span")) {

                String toInsertBefore = ""; // to take care in more than 2 overlapping constructions

                if (prevStartInd == insertHere && prevEndInd == tmpEnd) {
                    tag = tag.substring(0, tag.indexOf("'>")) + ", " + prevConstruct + "'>";
                    insertHere += prevStartTag.length();
                    toInsertBefore = prevStartTag;
                }

                prevStartInd = Integer.valueOf(curItem.substring(0, curItem.indexOf("-")));
                prevEndInd = Integer.valueOf(curItem.substring(curItem.indexOf("-") + 1));
                prevStartTag = toInsertBefore + tag;
                prevConstruct = tag.substring(tag.indexOf("title='") + 7, tag.indexOf("'>"));

            } else if (tag.contains("</span")) {
                //prevEndInd = insertHere;
            }

            docText = docText.substring(0, insertHere) + tag + docText.substring(insertHere);

        }
        newText = docText;

        return newText;
    }

    public String lineToFile() {
        String out = String.valueOf(this.getPreRank()) + ",";
        for (int i = 0; i < this.getConstructions().size(); i++) {
            out += String.valueOf(this.getRelFrequencies().get(i)) + ",";
        }
        out = out.substring(0, out.length() - 1); // remove the last comma
        return out;
    }

    public String lineToJsonFile() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(this);
        return json;
    }
}

class IndexComparator implements Comparator<Occurrence> {

    @Override
    public int compare(Occurrence occ1, Occurrence occ2) {
        return occ2.getEnd() - occ1.getEnd();
    }
}
