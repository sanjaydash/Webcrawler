package com.webcrawler.task;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webcrawler.action.LinkFinderAction;
import com.webcrawler.handler.LinkHandler;
import com.webcrawler.model.Page;
import com.webcrawler.model.PageStatus;
import com.webcrawler.model.Pages;

/**
 * @author Sanjay 
 * The class used to store which store
 * VisitedLink,SkippedLinks,ErrorLinks. It invokes the forkjoin pool
 * with the starting page, prints the output of the result.
 */
public class WebCrawler implements LinkHandler {
	/* Synchronized set of all the visited links */
	private static Collection<String> visitedLinks = Collections.synchronizedSet(new HashSet<String>());
	/* Synchronized set of all the skipped links */
	private static Collection<String> skippedLinks = Collections.synchronizedSet(new HashSet<String>());
	/* Synchronized set of all the error links */
	private static Collection<String> errorLinks = Collections.synchronizedSet(new HashSet<String>());
	/* Synchronized map of all the page and the links */
	private Map<String, List<String>> pageMap = new ConcurrentHashMap<>();
	/* List of all the pages */
	private final Pages pages;
	/* Starting page */
	private final String startPage;
	/* Forkjoin pool which runs the recursive task */
	private ForkJoinPool mainPool;

	public WebCrawler(Pages pages, String startPage, int maxThreads) {
		this.pages = pages;
		this.startPage = startPage;
		mainPool = new ForkJoinPool(maxThreads);
	}

	/**
	 * Start the Crawling operation with the starting page
	 */
	private void beginCrawling() {
		mainPool.invoke(new LinkFinderAction(startPage, this));
	}

	/**
	 * Add this link as VisitedLink
	 * 
	 * @param link
	 * @return Collection
	 */

	@Override
	public void addVisitedLinks(String link) {
		visitedLinks.add(link);

	}

	/**
	 * Add this link as SkippedLink
	 * 
	 * @param link
	 * @return Collection
	 */

	@Override
	public void addSkippedLinks(String link) {
		skippedLinks.add(link);

	}

	/**
	 * Add this link as Errorlinks
	 * 
	 * @param link
	 * @return Collection
	 */

	@Override
	public void addErrorLinks(String link) {
		errorLinks.add(link);

	}

	@Override
	public boolean isVisited(String link) {
		return visitedLinks.contains(link);
	}

	/**
	 * Prints the Pages that are successful, skipped ,Error
	 */
	public static void getPageStatus() {
		System.out.println(PageStatus.SUCCESS.getStatus() + ":" + visitedLinks.toString());
		System.out.println(PageStatus.SKIPPED.getStatus() + ":" + skippedLinks.toString());
		System.out.println(PageStatus.ERROR.getStatus() + ":" + errorLinks.toString());

	}

	/**
	 * Returns a map of individual pages which has links to other pages
	 */
	@Override
	public Map<String, List<String>> getPageLink() {

		for (Page page : pages.getPages()) {

			pageMap.put(page.getAddress(), page.getLinks());
		}
		return pageMap;
	}

	/**
	 * @param args the command line arguments page source, starting page and the
	 *             maxThreads
	 */
	public static void main(String[] args) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		Pages readValue = objectMapper.readValue(new File("internet.json"), Pages.class);
		new WebCrawler(readValue, "page-01", 64).beginCrawling();
		getPageStatus();
	}

}