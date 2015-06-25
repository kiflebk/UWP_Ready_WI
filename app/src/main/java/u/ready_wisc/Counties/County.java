package u.ready_wisc.Counties;

/**
 * Created by nathaneisner on 6/2/15.
 */
public class County {
    private String name;
    private String code;
    private String appID;
    private String region;

    public County(String name, String code, String appID, String region) {
        this.appID = appID;
        this.name = name;
        this.code = code;
        this.region = region;
    }

    public String getRegion() {
        return region;
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
