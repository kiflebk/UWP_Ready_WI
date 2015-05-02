package u.ready_wisc;

/**
 * Created by Ben on 5/1/2015.
 */
public class ShelterItem {
    String address;
    String city;
    String phone;
    String contact;
    String organization;

    public String getAddress() {return address;}

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {return phone;}

    public void setPhone(String phone) {this.phone = phone;}

    public String getContact(){return contact;}

    public void setContact(String contact){ this.contact = contact;}

    public String getOrganization(){return organization;}

    public void setOrganization(String organization){this.organization = organization;}

    public ShelterItem() {
    }

    public ShelterItem(String address, String city, String phone, String contact, String organization ) {
        this.address = address;
        this.city = city;
        this.phone = phone;
        this.contact = contact;
        this.organization = organization;
    }
}
