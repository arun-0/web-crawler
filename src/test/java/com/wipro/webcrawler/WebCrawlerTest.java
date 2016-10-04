package com.wipro.webcrawler;

import static org.mockito.Mockito.when;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class WebCrawlerTest {

	@Mock
	Connection connection;

	@Mock
	Response response;

	@Mock
	Document doc;
	
	@Mock
	WebCrawlerLeg leg;

	@Mock
	WebCrawler crawler;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

      	when(response.statusCode()).thenReturn(200);
      	when(response.contentType()).thenReturn("text/html");
    	when(connection.response()).thenReturn(response);
    	
    	
		String simpleHTML = new String(Files.readAllBytes(Paths.get(getClass().
				getResource("/wiprodigital.html").toURI())));
		doc = Jsoup.parse(simpleHTML);
    	
    	//when(doc.select("a[href]")).thenReturn(doc);

    	when(connection.get()).thenReturn(doc);
        when(leg.getConnection("")).thenReturn(connection);

    	when(crawler.initializeLeg()).thenReturn(leg);
    }

    @Test
    public void crawlerTest() {
    	crawler.search("http://wiprodigital.com");
    }

}
