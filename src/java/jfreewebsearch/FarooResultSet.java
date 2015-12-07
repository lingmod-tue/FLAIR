package jfreewebsearch;

import java.util.List;

/**
 * 
 * @author Simon Holthausen
 * 
 * A FarooResultSet is returned when the method query of the class FarooQueryPoint is invoked. Contains a list of search results to given query and other information (see methods for details)
 *
 */
public class FarooResultSet {
	
	private List<FarooResult> results;
	private String query;
	private int count;
	private int start;
	private String time;
	private int length;
	private List<String> suggestions;
	
	
	/**
	 * 
	 * @return list of result objects containing title, text snippet etc
	 */
	public List<FarooResult> getResults() {
		return results;
	}
	
	/**
	 * 
	 * @return actually used query. may be different from query by client if instant-search = true
	 */
	public String getQuery() {
		return query;
	}
	
	/**
	 * 
	 * @return number of results found
	 */
	public int getNrOfResults() {
		return count;
	}
	
	/**
	 * 
	 * @return start position of the results requested
	 */
	public int getStart() {
		return start;
	}
	
	/**
	 * 
	 * @return the search time
	 */
	public int getTime() {
		return Integer.valueOf(time);
	}
	
	/**
	 * 
	 * @return number of results requested by client
	 */
	public int getLength() {
		return length;
	}
	
	/**
	 * 
	 * @return query suggestions (autocomplete, spelling correction, ..). only used if instant-search = true
	 */
	public List<String> getSuggestions() {
		return suggestions;
	}

}
