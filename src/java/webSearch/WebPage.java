///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package webSearch;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.Set;
//
///**
// *
// * @author Maria
// */
//public class WebPage {
//
//    private String title;
//    private String www;
//    private String text;
//    private int numOfTokens;
//    private int numOfSents;
//    
//    private ArrayList<String> modals;
//    private int countOfModals;
//    private int countOfPossessives;
//    private int countOfRelativePronouns;
//
//    public WebPage() {
//        title = "";
//        www = "";
//        text = "";
//        numOfTokens = 0;
//        numOfSents = 0;
//        
//        modals = new ArrayList<>();
//        countOfModals = 0;
//        countOfPossessives = 0;
//        countOfRelativePronouns = 0;
//    }
//
//    public WebPage(String title, String www, String text) {
//        this();
//        this.title = title;
//        this.www = www;
//        this.text = text;
//        //countStructures(text);
//        //countStructuresEfficiently(text);
//    }
//
//    // setter and getter
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getWww() {
//        return www;
//    }
//
//    public void setWww(String www) {
//        this.www = www;
//    }
//
//    public String getText() {
//        return text;
//    }
//
//    public void setText(String text) {
//        this.text = text;
//    }
//
//    public ArrayList<String> getModals() {
//        return modals;
//    }
//
//    public void setModals(ArrayList<String> modals) {
//        this.modals = modals;
//    }
//
//    public int getCountOfModals() {
//        return countOfModals;
//    }
//
//    public void setCountOfModals(int countOfModals) {
//        this.countOfModals = countOfModals;
//    }
//
//    public int getCountOfPossessives() {
//        return countOfPossessives;
//    }
//
//    public void setCountOfPossessives(int countOfPossessives) {
//        this.countOfPossessives = countOfPossessives;
//    }
//
//    public int getCountOfRelativePronouns() {
//        return countOfRelativePronouns;
//    }
//
//    public void setCountOfRelativePronouns(int countOfRelativePronouns) {
//        this.countOfRelativePronouns = countOfRelativePronouns;
//    }
//
//    public int getNumOfTokens() {
//        return numOfTokens;
//    }
//
//    public void setNumOfTokens(int numOfTokens) {
//        this.numOfTokens = numOfTokens;
//    }
//
//    public int getNumOfSents() {
//        return numOfSents;
//    }
//
//    public void setNumOfSents(int numOfSents) {
//        this.numOfSents = numOfSents;
//    }
//
//    
//    
//    
//    // private methods 
//    private void countStructures(String text) {
//        // measure execution time of: MODALS
//        long startTime = System.nanoTime();
//        
//        
//        ArrayList<String> allModals = new ArrayList<>();
//
//        text = text.toLowerCase();
//
//        Set<String> set = new HashSet<>(Arrays.asList("can", "could", "might", "should", "have to", "has to", "had to", "must", "may", "ought to", "able to"));
//
//        for (String s : set) {
//            if (text.contains(s.toLowerCase())) {
//                modals.add(s);
//            }
//        }
//        
//        long endTime = System.nanoTime();
//        long duration = (endTime - startTime);
//        //System.out.println("countStructures(): " + duration/1000000.0);
//    }
//    
//    private void countStructuresEfficiently(String text) {
//        // measure execution time
//        long startTime = System.nanoTime();
//        
//        String[] tokens = text.split("\\s+");
//        numOfTokens = tokens.length;
//        
//        String[] sents = text.toLowerCase().split("[\\.\\?\\!]"); // splitting by whitespace increases the time
//        numOfSents = sents.length;
//        
//        for (int i = 0; i < numOfSents; i++) {
//            // MODALS
//            if (sents[i].contains("can")
//                    || sents[i].contains("could")
//                    || sents[i].contains("might")
//                    || sents[i].contains("may")
//                    || sents[i].contains("should")
//                    || sents[i].contains("have to")
//                    || sents[i].contains("has to")
//                    || sents[i].contains("had to")
//                    || sents[i].contains("must")
//                    || sents[i].contains("ought to")
//                    || sents[i].contains("able to")) {
//                countOfModals++;
//            }
//            
//            // POSSESSIVES
//            if (sents[i].contains("'s")
//                    || sents[i].contains("s'")) {
//                countOfPossessives++;
//            }
//            
//            
//            // question words and relative pronouns
//            if ((sents[i].contains("what") && !sents[i].trim().startsWith("what"))
//                    || (sents[i].contains("who") && !sents[i].trim().startsWith("who"))
//                    || (sents[i].contains("where") && !sents[i].trim().startsWith("where"))
//                    || (sents[i].contains("when") && !sents[i].trim().startsWith("when"))
//                    || (sents[i].contains("why") && !sents[i].trim().startsWith("why"))
//                    || (sents[i].contains("how") && !sents[i].trim().startsWith("how"))) {
//                countOfRelativePronouns++;
//            }
//            
//            
//            
//        }
//
//        long endTime = System.nanoTime();
//        long duration = (endTime - startTime);
//        //System.out.println("countStructuresEfficiently(): " + duration/1000000.0);
//
//    }
//
//    @Override
//    public String toString() {
//        return getTitle() + "<br> URL: " + getWww() + "<br> Tokens: " + getNumOfTokens() + "<br> Sentences: " + getNumOfSents() + "<br> Modals:" + getCountOfModals() + "<br> Possessives: " + getCountOfPossessives() + "<br> Relative pronouns: " + getCountOfRelativePronouns() + "<br><hr>";
//    }
//    
//    
//
//}
