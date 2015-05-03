package u.ready_wisc;

/**
 * Created by Ben on 5/1/2015.
 */
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

    public String getFacebook() {

        if(facebook != null && !facebook.equals("null"))
            return facebook;
        else
            return "No Facebook Account Listed";
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {

        if(twitter != null && !twitter.equals("null"))
            return twitter;
        else
            return "No Shelters Listed";
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

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
