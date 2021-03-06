package com.locdata.theatres.entity;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true )
public class ScotiaBankJson {
	
	@JsonProperty("Store")
	private Object Store;

	  public Object getStore() { return this.Store; }

	  public void setStore(Object Store) { this.Store = Store; }

	  @JsonProperty("Theatre")
	  private Object Theatre;

	  public Object getTheatre() { return this.Theatre; }

	  public void setTheatre(Object Theatre) { this.Theatre = Theatre; }

	  @JsonProperty("TheatreDetails")
	  private TheatreDetails TheatreDetails;

	  public TheatreDetails getTheatreDetails() { return this.TheatreDetails; }

	  public void setTheatreDetails(TheatreDetails TheatreDetails) { this.TheatreDetails = TheatreDetails; }
	  
	  @JsonProperty("Videos")
	  private Object Videos;

	  public Object getVideos() { return this.Videos; }

	  public void setVideos(Object Videos) { this.Videos = Videos; }
	  
	  @JsonProperty("Google")
	  private Object Google;

	  public Object getGoogle() { return this.Google; }

	  public void setGoogle(Object Google) { this.Google = Google; }

	  @JsonProperty("CustomSiteSearch")
	  private Object CustomSiteSearch;

	  public Object getCustomSiteSearch() { return this.CustomSiteSearch; }

	  public void setCustomSiteSearch(Object CustomSiteSearch) { this.CustomSiteSearch = CustomSiteSearch; }

	  @JsonProperty("Blogs")
	  private Object Blogs;

	  public Object getBlogs() { return this.Blogs; }

	  public void setBlogs(Object Blogs) { this.Blogs = Blogs; }
	
	@JsonIgnoreProperties(ignoreUnknown = true )
	public static class AllMeta
	{
	  @JsonProperty("id")
	  private int id;

	  public int getId() { return this.id; }

	  public void setId(int id) { this.id = id; }

	  @JsonProperty("title")
	  private String title;

	  public String getTitle() { return this.title; }

	  public void setTitle(String title) { this.title = title; }

	  @JsonProperty("TheatreAddress")
	  private String TheatreAddress;

	  public String getTheatreAddress() { return this.TheatreAddress; }

	  public void setTheatreAddress(String TheatreAddress) { this.TheatreAddress = TheatreAddress; }

	  @JsonProperty("TheatreLogoUrl")
	  private String TheatreLogoUrl;

	  public String getTheatreLogoUrl() { return this.TheatreLogoUrl; }

	  public void setTheatreLogoUrl(String TheatreLogoUrl) { this.TheatreLogoUrl = TheatreLogoUrl; }

	  @JsonProperty("TheatrePhone")
	  private String TheatrePhone;

	  public String getTheatrePhone() { return this.TheatrePhone; }

	  public void setTheatrePhone(String TheatrePhone) { this.TheatrePhone = TheatrePhone; }

	  @JsonProperty("TheatreDetailsUrl")
	  private String TheatreDetailsUrl;

	  public String getTheatreDetailsUrl() { return this.TheatreDetailsUrl; }

	  public void setTheatreDetailsUrl(String TheatreDetailsUrl) { this.TheatreDetailsUrl = TheatreDetailsUrl; }
	}

	@JsonIgnoreProperties(ignoreUnknown = true )
	public static class Record
	{
	  @JsonProperty("allMeta")
	  private AllMeta allMeta;

	  public AllMeta getAllMeta() { return this.allMeta; }

	  public void setAllMeta(AllMeta allMeta) { this.allMeta = allMeta; }
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true )
	public static class PageInfo
	{
	  @JsonProperty("recordStart")
	  private int recordStart;

	  public int getRecordStart() { return this.recordStart; }

	  public void setRecordStart(int recordStart) { this.recordStart = recordStart; }

	  @JsonProperty("recordEnd")
	  private int recordEnd;

	  public int getRecordEnd() { return this.recordEnd; }

	  public void setRecordEnd(int recordEnd) { this.recordEnd = recordEnd; }
	}

	@JsonIgnoreProperties(ignoreUnknown = true )
	public static class Zones
	{}

	
	@JsonIgnoreProperties(ignoreUnknown = true )
	public static class Template
	{
	  @JsonProperty("name")
	  private String name;

	  public String getName() { return this.name; }

	  public void setName(String name) { this.name = name; }

	  @JsonProperty("ruleName")
	  private Object ruleName;

	  public Object getRuleName() { return this.ruleName; }

	  public void setRuleName(Object ruleName) { this.ruleName = ruleName; }

	  @JsonProperty("zones")
	  private Zones zones;

	  public Zones getZones() { return this.zones; }

	  public void setZones(Zones zones) { this.zones = zones; }
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true )
	public static class TheatreDetails
	{
	  @JsonProperty("Records")
	  private ArrayList<Record> Records;

	  public ArrayList<Record> getRecords() { return this.Records; }

	  public void setRecords(ArrayList<Record> Records) { this.Records = Records; }

	  @JsonProperty("availableNavigation")
	  private ArrayList<Object> availableNavigation;

	  public ArrayList<Object> getAvailableNavigation() { return this.availableNavigation; }

	  public void setAvailableNavigation(ArrayList<Object> availableNavigation) { this.availableNavigation = availableNavigation; }

	  @JsonProperty("didYouMean")
	  private ArrayList<Object> didYouMean;

	  public ArrayList<Object> getDidYouMean() { return this.didYouMean; }

	  public void setDidYouMean(ArrayList<Object> didYouMean) { this.didYouMean = didYouMean; }

	  @JsonProperty("originalQuery")
	  private String originalQuery;

	  public String getOriginalQuery() { return this.originalQuery; }

	  public void setOriginalQuery(String originalQuery) { this.originalQuery = originalQuery; }

	  @JsonProperty("query")
	  private String query;

	  public String getQuery() { return this.query; }

	  public void setQuery(String query) { this.query = query; }

	  @JsonProperty("pageInfo")
	  private PageInfo pageInfo;

	  public PageInfo getPageInfo() { return this.pageInfo; }

	  public void setPageInfo(PageInfo pageInfo) { this.pageInfo = pageInfo; }

	  @JsonProperty("rewrites")
	  private ArrayList<Object> rewrites;

	  public ArrayList<Object> getRewrites() { return this.rewrites; }

	  public void setRewrites(ArrayList<Object> rewrites) { this.rewrites = rewrites; }

	  @JsonProperty("selectedNavigation")
	  private ArrayList<Object> selectedNavigation;

	  public ArrayList<Object> getSelectedNavigation() { return this.selectedNavigation; }

	  public void setSelectedNavigation(ArrayList<Object> selectedNavigation) { this.selectedNavigation = selectedNavigation; }

	  @JsonProperty("siteParams")
	  private ArrayList<Object> siteParams;

	  public ArrayList<Object> getSiteParams() { return this.siteParams; }

	  public void setSiteParams(ArrayList<Object> siteParams) { this.siteParams = siteParams; }

	  @JsonProperty("template")
	  private Template template;

	  public Template getTemplate() { return this.template; }

	  public void setTemplate(Template template) { this.template = template; }

	  @JsonProperty("totalRecordCount")
	  private int totalRecordCount;

	  public int getTotalRecordCount() { return this.totalRecordCount; }

	  public void setTotalRecordCount(int totalRecordCount) { this.totalRecordCount = totalRecordCount; }

	  @JsonProperty("pages")
	  private ArrayList<Integer> pages;

	  public ArrayList<Integer> getPages() { return this.pages; }

	  public void setPages(ArrayList<Integer> pages) { this.pages = pages; }

	  @JsonProperty("relatedQueries")
	  private ArrayList<Object> relatedQueries;

	  public ArrayList<Object> getRelatedQueries() { return this.relatedQueries; }

	  public void setRelatedQueries(ArrayList<Object> relatedQueries) { this.relatedQueries = relatedQueries; }

	  @JsonProperty("redirect")
	  private Object redirect;

	  public Object getRedirect() { return this.redirect; }

	  public void setRedirect(Object redirect) { this.redirect = redirect; }
	}

}
