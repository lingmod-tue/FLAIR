/*
 * This work is licensed under the Creative Commons Attribution-ShareAlike 4.0 International License. To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/4.0/.
 */
package org.arabidopsis.ahocorasick;

import java.util.Iterator;


public class TestBasic
{
    public static void main(String[] args) {
	 AhoCorasick tree = new AhoCorasick();
	 
	String text = "creation create creates created recreate recreated";
	String[] terms = {
	    "creat",
	    "recreate",
	    "recreated"
	};
	for (int i = 0; i < terms.length; i++) {
	    tree.add(terms[i].getBytes(), terms[i]);
	}
	tree.prepare();

	for (Iterator iter = tree.search(text.getBytes()); iter.hasNext(); ) {
	    SearchResult result = (SearchResult) iter.next();
	    String extr = text.substring(result.getLastIndex() - result.getOutputs().iterator().next().toString().length(), result.getLastIndex());
	    System.out.println("hit:" + result.getOutputs() + "\tindex:" + result.getLastIndex() +"\textract:" + extr);
	}
    }
}
