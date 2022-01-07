package com.flair.server.exerciseGeneration.exerciseManagement.InputParsing.DocumentParsing.WebpageParsing;

import java.util.ArrayList;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import com.flair.shared.exerciseGeneration.Pair;

public class HtmlManipulator {
	
	public static void manipulateDom(Element document, Pair<Node, Node> textBoundaries, boolean isUnderlineTask) {
		removeNonText(textBoundaries);
        removeNotDisplayedElements(document);
        if(isUnderlineTask) {
        	HtmlManipulator.removeLinks(document);
        }
	}

	/**
     * Removes any elements before and after the FLAIR plain text.
     * @param pair The first and last element of the plain text
     */
    private static void removeNonText(com.flair.shared.exerciseGeneration.Pair<Node,Node> pair) {
        removeSiblings(pair.first, true);
        removeSiblings(pair.second, false);
    }

    /**
     * Removes any previous or succeeding siblings of the boundary node, depending on the isStart flag.
     * @param boundaryNode	The start or end node representing the first or last element contained in the plain text
     * @param isStart <code>true</code> if previous elements need to be deleted; <code>false</code> if succeeding elements are deleted
     */
    private static void removeSiblings(Node boundaryNode, boolean isStart) {
        if(boundaryNode != null) {
            // Get the node's ancestors
            ArrayList<Node> ancestors = new ArrayList<>();
            Node parentNode = boundaryNode.parent();
            while (parentNode != null) {
                ancestors.add(parentNode);
                parentNode = parentNode.parent();
            }

            // Check for relevant siblings in boundary node and ancestors
            Node parent = boundaryNode;
            while (parent != null) {
                // Remove relevant siblings
                Node sibling = isStart ? parent.previousSibling() : parent.nextSibling();
                while (sibling != null) {
                    // Check if sibling's parent can also be removed
                    Node siblingParent = sibling.parent();
                    while (siblingParent != null && !ancestors.contains(siblingParent)) {
                        sibling = siblingParent;
                        siblingParent = siblingParent.parent();
                    }

                    Node nextRelevantSibling = isStart ? sibling.previousSibling() : sibling.nextSibling();
                    sibling.remove();
                    sibling = nextRelevantSibling;
                }
                parent = parent.parent();
            }
        }
    }
    
    /**
     * Replace anchor elements with spans.
     * Needed for Mark the Words exercises where words cannot be links.
     * @param doc	The HTML document
     */
    private static void removeLinks(Element doc) {
    	for(Element link : doc.select("a")) {
    		String content = link.html();
			if(content.contains("<span data-plaintextplaceholder>")) {
				link.tagName("span");
			}
    	}
    }
    
    /**
     * Removes text elements which were removed along with any enclosing elements which don't contain a not removed text element.
     */
    private static void removeNotDisplayedElements(Element doc) {
    	Elements elements = doc.select("span[data-remove]");
    	while(elements.size() > 0) {
    		Element element = elements.get(0);
    		Element parent = element.parent();
    		Element currentElementToRemove = element;
    		while(parent != null) {
    			String parentText = parent.outerHtml();
    			if(!parentText.contains("<span data-plaintextplaceholder>")) {
    				currentElementToRemove = parent;
    				parent = parent.parent();
    			} else {
    				parent = null;
    			}
    		}
    		if(currentElementToRemove.parent() != null) {
    			currentElementToRemove.remove();
    		}
    		elements = doc.select("span[data-remove]");
    	}    	
    }
    
}
