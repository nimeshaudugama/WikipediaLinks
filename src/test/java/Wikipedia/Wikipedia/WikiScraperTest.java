package Wikipedia.Wikipedia;


import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class WikiScraperTest {
    private Set<String> allLinks = new HashSet<>();
    private Set<String> visitedLinks = new HashSet<>();

    @Parameters({"url", "n"})
    @Test
    public void scrapeWikiLinks(String url, int n) throws IOException {
        Assert.assertTrue(url.startsWith("https://en.wikipedia.org/wiki/"), "Invalid Wikipedia link");

        scrapeLinks(url, n);

        System.out.println("Total Links Found: " + allLinks.size());
        writeLinksToFile("wiki_links.csv");
    }

    private void scrapeLinks(String url, int cycles) throws IOException {
        if (cycles == 0 || visitedLinks.contains(url)) {
            return;
        }

        WikiPage wikiPage = new WikiPage(url);
        Set<String> uniqueLinks = wikiPage.getUniqueWikiLinks();
        uniqueLinks.removeAll(visitedLinks);

        allLinks.addAll(uniqueLinks);
        visitedLinks.add(url);

        for (String link : uniqueLinks) {
            scrapeLinks(link, cycles - 1);
        }
    }

    private void writeLinksToFile(String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.append("Link\n");
            for (String link : allLinks) {
                writer.append(link).append("\n");
            }
        }
    }
}

