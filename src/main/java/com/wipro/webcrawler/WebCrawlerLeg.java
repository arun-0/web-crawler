package com.wipro.webcrawler;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebCrawlerLeg
{
    private String hostName;
    private Set<String> links = new HashSet<String>();
    private Set<String> externalLinks = new HashSet<String>();
    private Set<String> images = new HashSet<String>();



    // makes an HTTP request, checks the response, and then gathers up all the links on the page.
    public boolean crawl(String url)
    {
        try
        {
        	if (hostName == null) {
        		hostName = getDomainName(url);
        	}
            Connection connection = getConnection(url);

            Document htmlDocument = connection.get();
            if(connection.response().statusCode() == 200) {
                System.out.println("\n**Received web page at " + url);
            }
            if(!connection.response().contentType().contains("text/html")) {
                System.out.println("**Failure** Retrieved something other than HTML");
                return false;
            }

            Elements linksOnPage = htmlDocument.select("a[href]");
            Elements imagesOnPage = htmlDocument.select("img[src]");

            System.out.println("Found (" + linksOnPage.size() + ") links");
            System.out.println("Found (" + imagesOnPage.size() + ") images");

            for(Element link : linksOnPage)
            {
            	String linkURLString = link.absUrl("href");
            	if (hostName.equals(this.getDomainName(linkURLString))) {
                    this.links.add(linkURLString);
            	} else {
            		this.externalLinks.add(linkURLString);
            	}
            }
            for(Element link : imagesOnPage)
            {
            	String imgURLString = link.absUrl("src");
            	this.images.add(imgURLString);
            }

            return true;
        }
        catch(IOException ioe)
        {
            // We were not successful in our HTTP request
            return false;
        }
        catch(URISyntaxException use)
        {
            // We were not successful in our HTTP request
            return false;
        }
    }

	public Connection getConnection(String url) {
		Connection connection = Jsoup.connect(url);
		return connection;
	}
    
    private String getDomainName(String url) throws URISyntaxException {
        URI uri = new URI(url);
        String domain = uri.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }

    public Set<String> getLinks()
    {
        return this.links;
    }

    public Set<String> getExternalLinks()
    {
        return this.externalLinks;
    }

    public Set<String> getImages()
    {
        return this.images;
    }

}