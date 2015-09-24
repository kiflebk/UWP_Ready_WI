package u.readybadger;

/**
 * Holds volunteer item for listview adapter
 */
public class VolunteerItem {
    String name;
    String email;
    String phone;
    String url;

    public VolunteerItem() {
    }

    public VolunteerItem(String name, String email, String phone, String url) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.url = url;
    }

    // if no activity is found a label is displayed
    public String getName() {
        if (name != null && !name.equals("null"))
            return name;
        else
            return "No Volunteer Activities Listed";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {

        if (email != null && !email.equals("null"))
            return email;
        else
            return " ";
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {

        if (phone != null && !phone.equals("null"))
            return phone;
        else
            return " ";
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrl() {

        if (url != null && !url.equals("null"))
            return url;
        else
            return " ";
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return name + " " + email + " " + phone + " " + url;
    }
}
