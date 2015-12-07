/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logging;

import java.util.ArrayList;

/**
 *
 * @author Maria
 */
public class UserBean {
    
    private String firstName;
    private String lastName;
    private ArrayList<Query> queries;

    // constructors
    public UserBean() {
        firstName = "";
        lastName = "";
        queries = new ArrayList<>();
    }
    
    public UserBean(String firstName, String lastName) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    // getter and setter 
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public ArrayList<Query> getQueries() {
        return queries;
    }

    public void setQueries(ArrayList<Query> queries) {
        this.queries = queries;
    }
    
    public void addQueries(String query) {
        this.queries.add(new Query(query));
    }
}
