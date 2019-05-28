package com.webcrawler.action;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

import com.webcrawler.handler.LinkHandler;

/**
 * @author Sanjay
 * 
 *         This Class implements the RecursiveAction of the forkjoin pool which
 *         recursively evaluates the links that it crawls adds the link to
 *         action to visit pages in parallel.
 */
public class LinkFinderAction extends RecursiveAction {
	/* Computes the action recursively in parallel */
	private String link;
	/* Handler to hold the reference to webcrawler object */
	private LinkHandler linkHandler;;

	public LinkFinderAction(String link, LinkHandler linkHandler) {
		this.link = link;
		this.linkHandler = linkHandler;
	}

	/* computes the action recursively in parallel */
	@Override
	public void compute() {
		try {
			List<RecursiveAction> actions = new ArrayList<>();
			List<String> linkList = linkHandler.getPageLink().get(link);
			if (linkList != null || !linkList.isEmpty()) {
				for (String pageLink : linkList) {
					if (linkHandler.isVisited(pageLink)) {
						linkHandler.addSkippedLinks(pageLink);
					} else if (linkHandler.getPageLink().get(pageLink) != null) {
						actions.add(new LinkFinderAction(pageLink, linkHandler));
						linkHandler.addVisitedLinks(pageLink);
					} else {
						linkHandler.addErrorLinks(pageLink);
					}
				}
			} else {
				linkHandler.addErrorLinks(link);
			}

			// invoke recursively
			invokeAll(actions);
		} catch (Exception e) {

		}
	}
}
