package se.lexicon.jpabooking.model.dto.view;

import java.io.Serializable;

public class AddressDTO implements Serializable {
    private String id;
    private String streetAddress;
    private String zipCode;
    private String city;

    public AddressDTO(String id, String streetAddress, String zipCode, String city) {
        this.id = id;
        this.streetAddress = streetAddress;
        this.zipCode = zipCode;
        this.city = city;
    }

    public AddressDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
