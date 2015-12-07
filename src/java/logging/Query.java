/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logging;

/**
 *
 * @author Maria
 */
public class Query {
    
    private String query;
    
    // constructors
    public Query() {
        query = "";
    }
    
    public Query (String query) {
        this.query = query;
    }
    
    // getter and setter 

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
    
    
}
