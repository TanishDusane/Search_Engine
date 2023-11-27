package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

// Crawler class for web page crawling and indexing
public class Crawler {
    // Set to store visited URLs and avoid duplicates
    HashSet<String> urlSet;

    // Maximum depth for crawling, can be adjusted based on requirements
    int MAX_DEPTH = 2;

    // Constructor to initialize the URL set
    Crawler(){
        urlSet = new HashSet<String>();
    }

    // Recursive method to get page text and links, and perform indexing
    public void getPageTextAndLinks(String url, int depth){
        // Check if the URL has already been visited
        if(urlSet.contains(url)){
            return;
        }

        // Check if the maximum depth has been reached
        if(depth >= MAX_DEPTH){
            return;
        }

        // Add the URL to the set to mark it as visited
        if(urlSet.add(url)){
            // Print the URL to the console
            System.out.println(url);
        }

        // Increment the depth for the recursive call
        depth++;

        try {
            // Connect to the URL and fetch the document
            Document document = Jsoup.connect(url).timeout(5000).get();

            // Index the page content
            Indexer indexer = new Indexer(document, url);

            // Print the title of the document to the console
            System.out.println(document.title());

            // Extract all available links on the page
            Elements availableLinksOnPage = document.select("a[href]");

            // Recursively call the method for each extracted link
            for (Element currentLink : availableLinksOnPage) {
                getPageTextAndLinks(currentLink.attr("abs:href"), depth);
            }
        }
        // Handle exceptions related to I/O operations
        catch (IOException ioException){
            // Print the stack trace for debugging purposes
            ioException.printStackTrace();
        }
    }

    // Main method to start the crawling process
    public static void main(String[] args){
        // Create an instance of the Crawler class
        Crawler crawler = new Crawler();

        // Start crawling from the specified URL with initial depth 0
        crawler.getPageTextAndLinks("https://www.javatpoint.com/", 0);
    }
}