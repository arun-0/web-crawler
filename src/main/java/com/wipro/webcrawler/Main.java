package com.wipro.webcrawler;

public class Main
{
    public static void main(String[] args)
    {
        WebCrawler crawler = new WebCrawler();
        crawler.search("http://wiprodigital.com");
    }
}