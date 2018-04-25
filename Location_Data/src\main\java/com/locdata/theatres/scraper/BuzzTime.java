package com.locdata.theatres.scraper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.locdata.Legal.TaxPrep.HRCanadaEntity;
import com.locdata.ajax.request.response.builder.AjaxResponseBuilder;
import com.locdata.geocoding.google.service.GeoCodeEntityCarrierToExcelWriter;
import com.locdata.geocoding.google.service.GeoCodingApi;
import com.locdata.google.api.sheetsWriter.SheetLocalWriter;
import com.locdata.ingest.excel.ExcelReader;
import com.locdata.theatres.entity.BuzzTimeResponse;

/*
 * API to hit all the locations :
 * 
 * https://ntnservices.buzztime.com/players/events/sitefinder/?count=100&return_type=all&latitude=31.9685988&longitude=-99.90181310000003
 *  
 * Input GeoNames from the file stored. MASTERDATA 10,11 th column
 * 
 * Json -> Response Type is a List --> http://www.baeldung.com/jackson-exception
 * 
 * Comments - Tricky JSON return type : [{]] where it is expecting Object[] array but it returing only plain Object
 * 
 * So added this : TypeReference<List<BuzzTimeResponse>> typeRef = new TypeReference<List<BuzzTimeResponse>>() { };


    "Id": 38124,
    "SalesType": "DITV",
    "Phone": "(907) 563-9464",
    "Distance": 760.4013562634882,
    "Accuracy": null,
    "PowerPage": {
      "OptFreeWifi": 1,
      "Description": "The Buffalo Wild Wings? restaurants features a variety of boldly flavored, made-to-order menu items including its namesake Buffalo, New York-style chicken wings. The Buffalo Wild Wings menu specializes in mouth-watering signature sauces and seasonings with flavors ranging from Sweet BBQ? to Blazin'?. Guests enjoy a welcoming neighborhood atmosphere that includes an extensive multi-media system for watching their favorite sporting events. Buffalo Wild Wings is the recipient of hundreds of \"Best Wings\" and \"Best Sports Bar\" awards across the country.",
      "Opt21AndOver": 0,
      "URL": "http://www.buffalowildwings.com",
      "PowerPage_First_Image": "bww_image1.jpg",
      "Logo": "bww_logo.png",
      "URLFacebook": "http://www.facebook.com/BuffaloWildWings",
      "OptKidFriendly": 1,
      "URLMyspace": "http://www.myspace.com/buffalowildwings",
      "URLTwitter": "http://www.twitter.com/BWWings",
      "PowerPage_Second_Image": "bww_image2.jpg",
      "OptLiveMusic": 0,
      "PowerPageID": 4910,
      "OptFullBar": 1,
      "PowerPage_Third_Image": "bww_image3.jpg",
      "OptFullMenu": 1
    },
    "Location": {
      "Address": {
        "StreetAddress1": "3400 C Street",
        "City": {
          "Name": "Anchorage"
        },
        "ZipCode": {
          "ZipCodeValue": "99503"
        },
        "State": {
          "Name": "AK"
        }
      },
      "Name": "Buffalo Wild Wings Anchorage",
      "Id": 38124
    },
    "ReviewCount": 2,
    "SiteName": "Buffalo Wild Wings Anchorage",
    "SiteEvents": {
      "SiteEvent": [
        {
          "EventType": "Special Event",
          "EventTypeId": null,
          "EventStatusId": null,
          "EventDayId": null,
          "SiteId": 38124,
          "EventEndTime": null,
          "EventId": null,
          "EventStartTime": null
        }
      ]
    },
    "Chain": "Bufwldwngs",
    "Longitude": -149.887058,
    "Latitude": 61.189096,
    "IsTabletSite": true
  }
  
  To Do : https://www.buzztime.com/site-finder - Full bar , free wifi , live music e
 * 
 */

public class BuzzTime {

	private static final String FILE_NAME = "./temp/US.xls";
	private static Set<HRCanadaEntity> hrDataSet = new HashSet<HRCanadaEntity>();
	private volatile static int headcounter = 0;
	public static Set<GeoCodeEntityCarrierToExcelWriter> buzzTimeKeyDataSet = new HashSet<GeoCodeEntityCarrierToExcelWriter>();
	public static Set<String> dups = new HashSet<String>();

	public static void main(String args[]) throws EncryptedDocumentException, InvalidFormatException, IOException,
			InterruptedException, InstantiationException, IllegalAccessException {

		int sheetNumber = 0;

		Iterator<Row> iterator = ExcelReader.excelRead(FILE_NAME, sheetNumber);

		if (iterator == null)
			System.exit(0);

		while (iterator.hasNext()) {

			Row currentRow = iterator.next();
			Iterator<Cell> cellIterator = currentRow.iterator();
			double latitude = 0;
			double longitude = 0;

			/*
			 * Excel Reader for Geo Names Postal Code
			 */

			while (cellIterator.hasNext()) {

				Cell currentCell = cellIterator.next();

				if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {

					if (currentCell.getColumnIndex() == 9) {
						latitude = currentCell.getNumericCellValue();
					}
					if (currentCell.getColumnIndex() == 10) {
						longitude = currentCell.getNumericCellValue();
					}
				}
			}
			if (latitude != 0.0 && longitude != 0.0) {

				String sURL = "https://ntnservices.buzztime.com/players/events/sitefinder/?count=10&return_type=all&latitude="
						+ latitude + "&longitude=" + longitude + "";
				System.out.println(" URL  " + sURL);
				final GeoCodeEntityCarrierToExcelWriter responsebean = new GeoCodeEntityCarrierToExcelWriter();
				HttpURLConnection requestBuilder = AjaxResponseBuilder.generateRequest(sURL, 60000);

				if (requestBuilder != null) {
					requestBuilder.connect();
					if (requestBuilder.getContent() != null) {

						StringBuilder builder = new StringBuilder();
						BufferedReader br = new BufferedReader(
								new InputStreamReader((requestBuilder.getInputStream())));

						String output;
						System.out.println("Output from Server .... \n");
						while ((output = br.readLine()) != null) {
							builder.append(output);
						}

						String buzzTimeResponse = builder.toString();
						System.out.println(buzzTimeResponse);

						TypeReference<List<BuzzTimeResponse>> typeRef = new TypeReference<List<BuzzTimeResponse>>() {
						};

						ObjectMapper mapper = new ObjectMapper();

						List<BuzzTimeResponse> buzzResponse = mapper.readValue(buzzTimeResponse, typeRef);

						for (BuzzTimeResponse v : buzzResponse) {

							String street = v.getLocation().getAddress().getStreetAddress1();
							String state = v.getLocation().getAddress().getState().getName();
							String city = v.getLocation().getAddress().getCity().getName();
							String zipcode = v.getLocation().getAddress().getZipCode().getZipCodeValue();

							StringBuilder buffer = new StringBuilder();
							buffer.append(street + state + city + zipcode);

							if (!dups.contains(buffer.toString())) {

								responsebean.setStorenumber(v.getLocation().getId());
								responsebean.setStorename(v.getLocation().getName());
								dups.add(buffer.toString());
								System.out.println(" street---> " + street + " state -->" + state + " city " + city
										+ " zipcode " + zipcode);
								responsebean.setPhoneNumber(v.getPhone());
								new GeoCodingApi(buffer.toString(), responsebean);
								buzzTimeKeyDataSet.add(responsebean);

							}
						}
						headcounter++;
						if (headcounter == 500)
							break;
					}
				}
				requestBuilder.disconnect();
			}
			System.out.println(" Size of the set ====> " + hrDataSet.size());
		}
		SheetLocalWriter.writeXLSXFile("BuzzTimeEntertainment.xlsx", buzzTimeKeyDataSet);

		hrDataSet.parallelStream().forEach((action) -> {
			System.out.println(action.toString());
		});
	}
}