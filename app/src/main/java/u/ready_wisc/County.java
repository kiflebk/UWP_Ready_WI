package u.ready_wisc;

/**
 * Created by nathaneisner on 6/2/15.
 */
public class County {
    private String name;
    private String code;
    private String appID;

    public County(String name, String code, String appID) {
        this.appID = appID;
        this.name = name;
        this.code = code;
    }

    public String getAppID() {
        return appID;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
