package com.webcrawler.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json property represented by the below json "pages": [{ "address": "page-01",
 * "links": [ "page-02", "page-03" ] }, { "address": "page-02", "links": [
 * "page-01" ] },
 *
 */
public class Pages {

	@JsonProperty("pages")
	private List<Page> pages = null;

	@JsonProperty("pages")
	public List<Page> getPages() {
		return pages;
	}

	@JsonProperty("pages")
	public void setPages(List<Page> pages) {
		this.pages = pages;
	}

}