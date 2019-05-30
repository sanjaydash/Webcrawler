package com.webcrawler.handler;

import java.util.List;
import java.util.Map;



/**
 * Interface Defines the LinkHanler that is used store VisitedLink,SkippedLinks,ErrorLinks. 
 * 
*/
public interface LinkHandler {

	/**
	 *  gets the pageLink map
	 * 
	 * @param 
	 * @return map containing address and the links
	 */

	Map<String, List<String>> getPageLink();
	
	/**
	 *  Add this link as VisitedLink
	 * 
	 * @param link
	 * @return
	 */

	void addVisitedLinks(String link);
	
	/**
	 *  Add this link as SkippedLink
	 * 
	 * @param link
	 * @return
	 */

	void addSkippedLinks(String link);
	
	/**
	 *  Add this link as ErrorLink
	 * 
	 * @param link
	 * @return
	 */

	void addErrorLinks(String link);

	/**
	 * Checks if the link was already visited
	 * 
	 * @param link
	 * @return
	 */

	boolean isVisited(String link);
}
