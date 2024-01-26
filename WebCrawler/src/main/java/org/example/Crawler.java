package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.HashSet;

import static javax.management.Query.attr;

public class Crawler {
    HashSet<String> urlset;

    int MAX_DEPTH = 2;
    Crawler(){
        urlset = new HashSet<String>();
    }
    public void getPageTextsAndLinks(String url, int depth){
        if(urlset.contains(url)){
            return;
        }
        if(depth>=MAX_DEPTH){
            return;
        }
        if(urlset.add(url)){
            System.out.println(url);
        }
        depth++;
        try {
            Document document = Jsoup.connect(url).timeout(5000).get();

            Indexer indexer = new Indexer(document, url);

            String pageTitle = document.title();
            System.out.println(pageTitle);
            Elements availableLinksOnPage = document.select("a[href]");
            for (Element currentLink : availableLinksOnPage) {
                getPageTextsAndLinks(currentLink.attr("abs:href"), depth);
            }
        }
        catch (IOException ioException){
            ioException.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Crawler crawler = new Crawler();
        crawler.getPageTextsAndLinks("https://www.javatpoint.com", 0);


    }
}