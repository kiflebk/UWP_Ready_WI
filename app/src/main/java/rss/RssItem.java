package rss;

/**
 * Created by piela_000 on 3/1/2015.
 */

public class RssItem {

    private final String title;
    private final String link;
    private final String desc;

    public RssItem(String title, String link, String desc) {
        this.title = title;
        this.link = link;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDesc() {return desc;}
}
