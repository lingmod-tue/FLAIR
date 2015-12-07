package jfreewebsearch;

import java.util.List;

/**
 * 
 * @author Simon Holthausen
 * 
 * This class holds information of a faroo result. Includes title of found result, a text snippet, the url of the result, and more.
 *
 */
public class FarooResult {

	private String title;
	private String kwic;
	private String url;
	private String iurl;
	private String domain;
	private String author;
	private boolean news;
	private String date;
	//private someObjectThatHasToBeCreated related; //TMP: only relevant for news, not included in this api yet
	//private String votes; //not listed in api but is in the json string - ??
	//private String content; //not listed in api but is in the json string - ??
	
	
	/**
	 * 
	 * @return title of the result
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * 
	 * @return snippet of the results text
	 */
	public String getTextSnippet() {
		return kwic;
	}
	
	/**
	 * 
	 * @return url of the result
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * 
	 * @return image url of the result
	 */
	public String getIurl() {
		return iurl;
	}
	
	/**
	 * 
	 * @return domain of the result
	 */
	public String getDomain() {
		return domain;
	}
	
	/**
	 * 
	 * @return author of the result
	 */
	public String getAuthor() {
		return author;
	}
	
	/**
	 * 
	 * @return true if it is a news item
	 */
	public boolean isNews() {
		return news;
	}
	
	/**
	 * 
	 * @return date of the result
	 */
	public String getDate() {
		return date;
	}
	
	/**
	 * 
	 * @return related results. only for news search
	 */
	//public List<String> getRelated() {
	//	return related;
	//}
	
	/**
	 * 
	 * @return ?
	 */
	//public String getVotes() {
	//	return votes;
	//}
	
	/**
	 * 
	 * @return ?
	 */
	//public String getContent() {
	//	return content;
	//}
	
	
}
