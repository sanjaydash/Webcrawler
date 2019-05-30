package com.webcrawler.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Json property represented by the below json "address": "page-01", "links": [
 * "page-02", "page-03" ]
 *
 */
public class Page {

	@JsonProperty("address")
	private String address;
	@JsonProperty("links")
	private List<String> links = new ArrayList<>();

	@JsonProperty("address")
	public String getAddress() {
		return address;
	}

	@JsonProperty("address")
	public void setAddress(String address) {
		this.address = address;
	}

	@JsonProperty("links")
	public List<String> getLinks() {
		return links;
	}

	@JsonProperty("links")
	public void setLinks(List<String> links) {
		this.links = links;
	}

}