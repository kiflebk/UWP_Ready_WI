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
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    @Override
    public String toString(){
        return facebook + " " + twitter + " " + extra;
    }
}
