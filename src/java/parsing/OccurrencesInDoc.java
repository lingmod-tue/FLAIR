/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parsing;

import java.util.ArrayList;

/**
 *
 * @author Maria
 */
public class OccurrencesInDoc {
    
    private ArrayList<Occurrence> occurrences;
    private int docNumber;

    /**
     * Default constructor.
     */
    public OccurrencesInDoc() {
        occurrences = new ArrayList<>();
        docNumber = 0;
    }
    
    public OccurrencesInDoc(ArrayList<Occurrence> occurrences) {
        this();
        this.occurrences = occurrences;
    }
    
    public OccurrencesInDoc(int docNumber) {
        this();
        this.docNumber = docNumber;
    }
    
    // setter and getter

    public int getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(int docNumber) {
        this.docNumber = docNumber;
    }
    
    

    public ArrayList<Occurrence> getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(ArrayList<Occurrence> occurrences) {
        this.occurrences = occurrences;
    }
    
    /**
     * Add an occurrence to this document.
     * @param oc 
     */
    public void addOccurrence(Occurrence oc) {
        this.occurrences.add(oc);
    }
    
    
}
