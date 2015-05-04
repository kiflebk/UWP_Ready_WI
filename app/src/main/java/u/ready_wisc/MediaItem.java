package u.ready_wisc;

/**
 * Holds social media items for listview
 */

// holds the social media accounts loaded from the web db
public class MediaItem {
    String facebook;
    String twitter;
    String extra;

    public MediaItem() {
    }

    public MediaItem(String facebook, String twitter, String extra) {
        this.facebook = facebook;
        this.twitter = twitter;
        this.extra = extra;
    }

    // if no data has been entered facebook.com is returned as the default
    public String getFacebook() {

        if(facebook != null && !facebook.equals("null"))
            return facebook;
        else
            return "http://facebook.com";
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    // if no data is entered twitter.com is returned as a default
    public String getTwitter() {

        if(twitter != null && !twitter.equals("null"))
            return twitter;
        else
            return "http://twitter.com";
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    // currently not being used but does exist in the web database
    public String getExtra() {

        if(extra != null && !extra.equals("null"))
            return extra;
        else
            return " ";
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    @Override
    public String toString(){
        return facebook + " " + twitter + " " + extra;
    }
}
