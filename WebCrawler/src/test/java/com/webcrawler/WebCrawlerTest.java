
package com.webcrawler;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webcrawler.model.Pages;
import com.webcrawler.task.WebCrawler;
import com.webcrawler.util.FileUtility;

/**
 * @author Sanjay Test Case to verify the behavior of the crawler
 */
public class WebCrawlerTest {

	private Pages readValue1, readValue2;
	private WebCrawler webCrawler1, webCrawler2;

	@Before
	public void setUp() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();

		readValue1 = objectMapper.readValue(FileUtility.getFileFromResources("internet.json"), Pages.class);
		readValue2 = objectMapper.readValue(FileUtility.getFileFromResources("internet_1.json"), Pages.class);
	}

	/**
	 * Test Case to verify all the pages crawled and are mapped
	 */
	@Test
	public void verifyAllLinks() {
		webCrawler1 = new WebCrawler(readValue1, "page-01", 64);
		webCrawler1.beginCrawling();

		HashSet<String> h1 = new HashSet<>();
		h1.add("page-99");
		h1.add("page-01");
		h1.add("page-04");
		h1.add("page-05");
		h1.add("page-02");
		h1.add("page-03");
		h1.add("page-08");
		h1.add("page-09");
		h1.add("page-06");
		h1.add("page-07");

		assertArrayEquals(webCrawler1.getVisitedLinks().toArray(), h1.toArray());

		HashSet h2 = new HashSet<String>();
		h2.add("page-01");
		h2.add("page-04");
		h2.add("page-05");
		h2.add("page-02");
		h2.add("page-03");
		h2.add("page-08");
		h2.add("page-09");

		assertArrayEquals(webCrawler1.getSkippedLinks().toArray(), h2.toArray());
		HashSet h3 = new HashSet<String>();
		h3.add("page-11");
		h3.add("page-00");
		h3.add("page-12");
		h3.add("page-10");
		h3.add("page-13");
		assertArrayEquals(webCrawler1.getErrorLinks().toArray(), h3.toArray());
	}

	/**
	 * Test Case to verify pages without links are verified
	 */
	@Test
	public void verifynoLinks() {
		webCrawler2 = new WebCrawler(readValue2, "page-02", 64);
		webCrawler2.beginCrawling();
		assertEquals(webCrawler2.getVisitedLinks().toString(), "[page-02]");
		assertEquals(webCrawler2.getSkippedLinks().toString(), "[]");
		assertEquals(webCrawler2.getErrorLinks().toString(), "[]");
	}

}
