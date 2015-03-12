package u.ready_wisc;

/**
 * Created by Jake on 3/11/2015.
 */
public class ResourceItem {
    String name;
    String address;
    String phone;
    String other;
    String county;
    String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ResourceItem(String name, String address, String phone, String other, String county, String type) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.other = other;
        this.county = county;
        this.type = type;
    }
}
