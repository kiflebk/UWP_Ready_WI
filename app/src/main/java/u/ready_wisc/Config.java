package u.ready_wisc;

/**
 * Created by kiflebk on 4/17/15.
 */
public class Config {

    private Config() {
        throw new AssertionError();
    }

    // URLs portions for the county RSS feeds
    public static final String RSS_KENOSHA = "WIC059&y=0";
    public static final String RSS_RACINE = "WIC101&y=0";
    public static final String RSS_MILWAUKEE = "WIC079&y=0";
    public static final String RSS_ROCK = "WIC105&y=0";
    public static final String RSS_DANE = "WIC025&y=0";
    public static final String RSS_SAUK = "WIC111&y=0";
    public static final String RSS_OZAUKEE = "WIC089&y=0";
    public static final String RSS_WASHINGTON = "WIC131&y=0";
    public static final String RSS_JEFFERSON = "WIC055&y=0";
    public static final String RSS_WALWORTH = "WIC127&y=0";
    public static final String RSS_WAUKESHA = "WIC133&y=0";






    // URL for sending damage report data
    public static final String URL_REPORT = "http://joshuaolufs.com/" +
            "php/query_damageReports_insert.php";

}
