package se.lexicon.jpabooking.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

import static se.lexicon.jpabooking.model.constants.EntityConstants.GENERATOR;
import static se.lexicon.jpabooking.model.constants.EntityConstants.UUID_GENERATOR;

@Entity
public class Address {
    @Id
    @GeneratedValue(generator = GENERATOR)
    @GenericGenerator(name = GENERATOR, strategy = UUID_GENERATOR)
    @Column(updatable = false)
    private String id;
    @Column(name = "street")
    private String streetAddress;
    @Column(name = "zip_code", length = 20)
    private String zipCode;
    @Column(name = "city")
    private String city;

    public Address(String id, String streetAddress, String zipCode, String city) {
        this.id = id;
        this.streetAddress = streetAddress;
        this.zipCode = zipCode;
        this.city = city;
    }

    public Address() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(streetAddress, address.streetAddress) && Objects.equals(zipCode, address.zipCode) && Objects.equals(city, address.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(streetAddress, zipCode, city);
    }

    @Override
    public String toString() {
        return "Address{" +
                "id='" + id + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
