package Wikipedia.Wikipedia;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class WikiPage {
    private String url;

    public WikiPage(String url) {
        this.url = url;
    }

    public Set<String> getUniqueWikiLinks() throws IOException {
        Set<String> uniqueLinks = new HashSet<>();
        Document doc = Jsoup.connect(url).get();
        Elements links = doc.select("a[href]");

        for (Element link : links) {
            String href = link.attr("href");
            if (href.startsWith("/wiki/") && !href.contains(":")) {
                uniqueLinks.add("https://en.wikipedia.org" + href);
            }
            if (uniqueLinks.size() >= 10) {
                break;
            }
        }
        return uniqueLinks;
    }
}

