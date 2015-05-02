package u.ready_wisc;

/**
 * Created by Ben on 5/1/2015.
 */
public class VolunteerItem {
    String name;
    String email;
    String phone;
    String url;

    public String getName() {return name;}

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {this.phone = phone;}

    public String getUrl(){return url;}

    public void setUrl(String contact){ this.url = url;}

    public VolunteerItem() {
    }

    public VolunteerItem(String name, String email, String phone, String url) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.url = url;
    }
}
