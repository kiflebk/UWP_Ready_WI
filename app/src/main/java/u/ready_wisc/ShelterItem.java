package u.ready_wisc;

/**
 * Item to hold shelter info from the database for a listview
 */
public class ShelterItem {
    String address;
    String city;
    String phone;
    String contact;
    String organization;

    public ShelterItem() {
    }

    public ShelterItem(String address, String city, String phone, String contact, String organization) {
        this.address = address;
        this.city = city;
        this.phone = phone;
        this.contact = contact;
        this.organization = organization;
    }

    public String getAddress() {
        if (address != null && !address.equals("null"))
            return address;
        else
            return " ";
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        if (city != null && !city.equals("null"))
            return city;
        else
            return " ";
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getContact() {
        if (contact != null && !contact.equals("null"))
            return contact;
        else
            return " ";
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    // if no data is found no shelters listed is displayed
    public String getOrganization() {
        if (organization != null && !organization.equals("null"))
            return organization;
        else
            return "No Shelters Listed";
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @Override
    public String toString() {
        return address + " " + city + " " + phone + " " + contact + " " + organization;
    }
}
