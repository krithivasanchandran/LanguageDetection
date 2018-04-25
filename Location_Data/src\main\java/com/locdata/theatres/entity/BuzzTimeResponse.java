package com.locdata.theatres.entity;

import java.util.ArrayList;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonRootName;

import com.locdata.theatres.entity.BuzzTimeResponse.PowerPage;

@JsonIgnoreProperties(ignoreUnknown = true )
@JsonDeserialize(contentAs = BuzzTimeResponse.class)
public class BuzzTimeResponse
{
	  @JsonProperty("Id")
	  private int Id;

	  public int getId() { return this.Id; }

	  public void setId(int Id) { this.Id = Id; }

	  @JsonProperty("SalesType")
	  private String SalesType;

	  public String getSalesType() { return this.SalesType; }

	  public void setSalesType(String SalesType) { this.SalesType = SalesType; }

	  @JsonProperty("Phone")
	  private String Phone;

	  public String getPhone() { return this.Phone; }

	  public void setPhone(String Phone) { this.Phone = Phone; }

	  @JsonProperty("Distance")
	  private double Distance;

	  public double getDistance() { return this.Distance; }

	  public void setDistance(double Distance) { this.Distance = Distance; }

	  @JsonProperty("Accuracy")
	  private Object Accuracy;

	  public Object getAccuracy() { return this.Accuracy; }

	  public void setAccuracy(Object Accuracy) { this.Accuracy = Accuracy; }

	  @JsonProperty("PowerPage")
	  private PowerPage PowerPage;

	  public PowerPage getPowerPage() { return this.PowerPage; }

	  public void setPowerPage(PowerPage PowerPage) { this.PowerPage = PowerPage; }
	  
	  @JsonProperty("Location")
	  private Location location;

	  public Location getLocation() { return this.location; }

	  public void setLocation(Location location) { this.location = location; }

	  @JsonProperty("ReviewCount")
	  private int ReviewCount;

	  public int getReviewCount() { return this.ReviewCount; }

	  public void setReviewCount(int ReviewCount) { this.ReviewCount = ReviewCount; }

	  @JsonProperty("SiteName")
	  private String SiteName;

	  public String getSiteName() { return this.SiteName; }

	  public void setSiteName(String SiteName) { this.SiteName = SiteName; }

	  @JsonProperty("SiteEvents")
	  private SiteEvents SiteEvents;

	  public SiteEvents getSiteEvents() { return this.SiteEvents; }

	  public void setSiteEvents(SiteEvents SiteEvents) { this.SiteEvents = SiteEvents; }

	  @JsonProperty("Chain")
	  private String Chain;

	  public String getChain() { return this.Chain; }

	  public void setChain(String Chain) { this.Chain = Chain; }

	  @JsonProperty("Longitude")
	  private double Longitude;

	  public double getLongitude() { return this.Longitude; }

	  public void setLongitude(double Longitude) { this.Longitude = Longitude; }

	  @JsonProperty("Latitude")
	  private double Latitude;

	  public double getLatitude() { return this.Latitude; }

	  public void setLatitude(double Latitude) { this.Latitude = Latitude; }

	  @JsonProperty("IsTabletSite")
	  private boolean IsTabletSite;

	  public boolean getIsTabletSite() { return this.IsTabletSite; }

	  public void setIsTabletSite(boolean IsTabletSite) { this.IsTabletSite = IsTabletSite; }
	  
	 @JsonIgnoreProperties(ignoreUnknown = true )
	  public static class PowerPage
		{ 
		 @JsonProperty("Opt21AndOver")
		  private int Opt21AndOver;

		  public int getOpt21AndOver() { return this.Opt21AndOver; }

		  public void setOpt21AndOver(int Opt21AndOver) { this.Opt21AndOver = Opt21AndOver; }

		  @JsonProperty("PowerPage_Second_Image")
		  private String PowerPage_Second_Image;

		  public String getPowerPageSecondImage() { return this.PowerPage_Second_Image; }

		  public void setPowerPageSecondImage(String PowerPage_Second_Image) { this.PowerPage_Second_Image = PowerPage_Second_Image; }

		  @JsonProperty("PowerPage_Third_Image")
		  private String PowerPage_Third_Image;

		  public String getPowerPageThirdImage() { return this.PowerPage_Third_Image; }

		  public void setPowerPageThirdImage(String PowerPage_Third_Image) { this.PowerPage_Third_Image = PowerPage_Third_Image; }

		  @JsonProperty("OptFreeWifi")
		  private int OptFreeWifi;

		  public int getOptFreeWifi() { return this.OptFreeWifi; }

		  public void setOptFreeWifi(int OptFreeWifi) { this.OptFreeWifi = OptFreeWifi; }

		  @JsonProperty("Description")
		  private String Description;

		  public String getDescription() { return this.Description; }

		  public void setDescription(String Description) { this.Description = Description; }
		  
		  @JsonProperty("URLMyspace")
		  private String URLMyspace;

		  public String getURLMyspace() { return this.URLMyspace; }

		  public void setURLMyspace(String URLMyspace) { this.URLMyspace = URLMyspace; }

		  @JsonProperty("URLFacebook")
		  private String URLFacebook;

		  public String getURLFacebook() { return this.URLFacebook; }

		  public void setURLFacebook(String URLFacebook) { this.URLFacebook = URLFacebook; }

		  @JsonProperty("OptLiveMusic")
		  private int OptLiveMusic;

		  public int getOptLiveMusic() { return this.OptLiveMusic; }

		  public void setOptLiveMusic(int OptLiveMusic) { this.OptLiveMusic = OptLiveMusic; }

		  @JsonProperty("PowerPage_First_Image")
		  private String PowerPage_First_Image;

		  public String getPowerPageFirstImage() { return this.PowerPage_First_Image; }

		  public void setPowerPageFirstImage(String PowerPage_First_Image) { this.PowerPage_First_Image = PowerPage_First_Image; }

		  @JsonProperty("OptFullBar")
		  private int OptFullBar;

		  public int getOptFullBar() { return this.OptFullBar; }

		  public void setOptFullBar(int OptFullBar) { this.OptFullBar = OptFullBar; }

		  @JsonProperty("OptKidFriendly")
		  private int OptKidFriendly;

		  public int getOptKidFriendly() { return this.OptKidFriendly; }

		  public void setOptKidFriendly(int OptKidFriendly) { this.OptKidFriendly = OptKidFriendly; }

		  @JsonProperty("PowerPageID")
		  private Object PowerPageID;

		  public Object getPowerPageID() { return this.PowerPageID; }

		  public void setPowerPageID(Object PowerPageID) { this.PowerPageID = PowerPageID; }

		  @JsonProperty("Logo")
		  private String Logo;

		  public String getLogo() { return this.Logo; }

		  public void setLogo(String Logo) { this.Logo = Logo; }

		  @JsonProperty("URLTwitter")
		  private String URLTwitter;

		  public String getURLTwitter() { return this.URLTwitter; }

		  public void setURLTwitter(String URLTwitter) { this.URLTwitter = URLTwitter; }

		  @JsonProperty("URL")
		  private String URL;

		  public String getURL() { return this.URL; }

		  public void setURL(String URL) { this.URL = URL; }

		  @JsonProperty("OptFullMenu")
		  private int OptFullMenu;

		  public int getOptFullMenu() { return this.OptFullMenu; }

		  public void setOptFullMenu(int OptFullMenu) { this.OptFullMenu = OptFullMenu; }}

	 @JsonIgnoreProperties(ignoreUnknown = true )
		public static class City
		{
			@JsonProperty("Name")
		  private String Name;

		  public String getName() { return this.Name; }

		  public void setName(String Name) { this.Name = Name; }
		}
	 
	 @JsonIgnoreProperties(ignoreUnknown = true )
		public static class ZipCode
		{
			
			@JsonProperty("ZipCodeValue")
		  private String ZipCodeValue;

		  public String getZipCodeValue() { return this.ZipCodeValue; }

		  public void setZipCodeValue(String ZipCodeValue) { this.ZipCodeValue = ZipCodeValue; }
		}

	 @JsonIgnoreProperties(ignoreUnknown = true )
		public static class State
		{
			@JsonProperty("Name")
		  private String Name;

		  public String getName() { return this.Name; }

		  public void setName(String Name) { this.Name = Name; }
		}
	
	 @JsonIgnoreProperties(ignoreUnknown = true )
		public static class Address
		{
			@JsonProperty("StreetAddress1")
		  private String StreetAddress1;

		  public String getStreetAddress1() { return this.StreetAddress1; }

		  public void setStreetAddress1(String StreetAddress1) { this.StreetAddress1 = StreetAddress1; }

		  @JsonProperty("City")
		  private City City;

		  public City getCity() { return this.City; }

		  public void setCity(City City) { this.City = City; }

		  @JsonProperty("ZipCode")
		  private ZipCode ZipCode;

		  public ZipCode getZipCode() { return this.ZipCode; }

		  public void setZipCode(ZipCode ZipCode) { this.ZipCode = ZipCode; }
		 
		  @JsonProperty("State")
		  private State State;

		  public State getState() { return this.State; }

		  public void setState(State State) { this.State = State; }
		}

	 @JsonIgnoreProperties(ignoreUnknown = true )
		public static class Location
		{
		  @JsonProperty("Address")
		  private Address Address;

		  public Address getAddress() { return this.Address; }

		  public void setAddress(Address Address) { this.Address = Address; }

		  @JsonProperty("Name")
		  private String Name;

		  public String getName() { return this.Name; }

		  public void setName(String Name) { this.Name = Name; }

		  @JsonProperty("Id")
		  private int Id;

		  public int getId() { return this.Id; }

		  public void setId(int Id) { this.Id = Id; }
		}
	 
	 @JsonIgnoreProperties(ignoreUnknown = true )

		public static class SiteEvent
		{
		 public SiteEvent() {
		        super();
		   }
		 
		  @JsonProperty("EventType")
		  private String EventType;

		  public String getEventType() { return this.EventType; }

		  public void setEventType(String EventType) { this.EventType = EventType; }

		  @JsonProperty("EventTypeId")
		  private Object EventTypeId;

		  public Object getEventTypeId() { return this.EventTypeId; }

		  public void setEventTypeId(Object EventTypeId) { this.EventTypeId = EventTypeId; }

		  @JsonProperty("EventStatusId")
		  private Object EventStatusId;

		  public Object getEventStatusId() { return this.EventStatusId; }

		  public void setEventStatusId(Object EventStatusId) { this.EventStatusId = EventStatusId; }
		  
		  @JsonProperty("EventDayId")
		  private Object EventDayId;

		  public Object getEventDayId() { return this.EventDayId; }

		  public void setEventDayId(Object EventDayId) { this.EventDayId = EventDayId; }

		  @JsonProperty("SiteId")
		  private int SiteId;

		  public int getSiteId() { return this.SiteId; }

		  public void setSiteId(int SiteId) { this.SiteId = SiteId; }

		  @JsonProperty("EventEndTime")
		  private Object EventEndTime;

		  public Object getEventEndTime() { return this.EventEndTime; }

		  public void setEventEndTime(Object EventEndTime) { this.EventEndTime = EventEndTime; }

		  @JsonProperty("EventId")
		  private Object EventId;

		  public Object getEventId() { return this.EventId; }

		  public void setEventId(Object EventId) { this.EventId = EventId; }

		  @JsonProperty("EventStartTime")
		  private Object EventStartTime;

		  public Object getEventStartTime() { return this.EventStartTime; }

		  public void setEventStartTime(Object EventStartTime) { this.EventStartTime = EventStartTime; }
		}
	 
		@JsonIgnoreProperties(ignoreUnknown = true )
		public static class SiteEvents
		{
		public SiteEvents() {
		        super();
		   }
			
		  @JsonProperty("SiteEvent")
		  private ArrayList<SiteEvent> SiteEvent;

		  public ArrayList<SiteEvent> getSiteEvent() { return this.SiteEvent; }

		  public void setSiteEvent(ArrayList<SiteEvent> SiteEvent) { this.SiteEvent = SiteEvent; }
		}

	}