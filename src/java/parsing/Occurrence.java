/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parsing;

/**
 *
 * @author Maria
 */
public class Occurrence {
    
    private int docNum;
    private int start;
    private int end;
    private String instance; // example
    private String construction;
    
    public Occurrence() {
        docNum = -1;
        start = -1;
        end = -1;
        instance = "";
        construction = "";
    }

    public Occurrence(String construction, int docNum, int start, int end, String instance) {
        this();
        this.start = start;
        this.end = end;
        this.instance = instance;
        this.docNum = docNum;
        this.construction = construction;
    }
    
    public Occurrence(String construction, int docNum, String instance){
        this();
        this.instance = instance;
        this.docNum = docNum;
        this.construction = construction;
    }
    
    // getter and setter

    public String getConstruction() {
        return construction;
    }

    public void setConstruction(String construction) {
        this.construction = construction;
    }

    public int getDocNum() {
        return docNum;
    }

    public void setDocNum(int docNum) {
        this.docNum = docNum;
    }
    
    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }
    
    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }
    
    
    
}
