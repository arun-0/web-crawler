package com.wipro.webcrawler;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class WebCrawler
{
  private static final int MAX_PAGES_TO_SEARCH = 100;
  private Set<String> pagesVisited = new HashSet<String>();
  private Collection<String> pagesToVisit = new LinkedHashSet<String>();


  public void search(String url)
  {
      WebCrawlerLeg leg = initializeLeg();
      leg.crawl(url);
      this.pagesVisited.add(url);
      populatePagesToVisit(leg);
      
      while(! (this.pagesToVisit.isEmpty() | this.pagesVisited.size() == MAX_PAGES_TO_SEARCH))
      {
          String currentUrl = this.nextUrl();
          leg.crawl(currentUrl);
          populatePagesToVisit(leg);
      }

      System.out.println("\nDone - Visited " + this.pagesVisited.size() + " web page(s)");
      System.out.println("=====Page Visisted======");
      for (String s : pagesVisited) {
          System.out.println("----" + s);
      }

      System.out.println("\n\n=====Externl Links Found (" + leg.getExternalLinks().size() + ")======");
      for (String s : leg.getExternalLinks()) {
          System.out.println("----" + s);
      }

      System.out.println("\n\n=====Images Found (" + leg.getImages().size() + ")======");
      for (String s : leg.getImages()) {
          System.out.println("----" + s);
      }

  }

public WebCrawlerLeg initializeLeg() {
	WebCrawlerLeg leg = new WebCrawlerLeg();
	return leg;
}

	/**
	 * This populates the pageToVisit.
	 * It ensures that this method doesn't include a URL that has already been visited
	 * @param leg
	 */
	private void populatePagesToVisit(WebCrawlerLeg leg) {
		//Get the links from crawler
		  Set<String> s = leg.getLinks();
		  s.removeAll(pagesVisited);
		  this.pagesToVisit.addAll(s);
	}


  /**
   * Returns the next URL to visit (in the order that they were found)
   * @return
   */
  private String nextUrl()
  {
	  Iterator<String> it = this.pagesToVisit.iterator();
      String nextUrl;

	  if (it.hasNext()) {
		  nextUrl = it.next();
		  it.remove();
	      this.pagesVisited.add(nextUrl);
	  } else {
    	  nextUrl = "";
	  }
	  
      return nextUrl;
  }

}