# web-crawler
A simple web crawler which lists:
1. All the pages in a domain name
2. All the links to external domain names
3. All the images

The program was written from test'ablity point of view, eg the calls to the Static method/ new operator are encapsulated in a separate method to be able to mock it

However I could not complete the Unit tests

This is a simple Maven project created in Eclipse IDE which could be imported from its POM
and built using:

mvn clean install

To run the program, Main.java could be run as Java program, which prompts the crawler to crawl "wiprodigital.com"

If had more time I would write comprehensive test using Mocks and test resources
Here I started working off an HTML resource from src/test/resources
