package se.lexicon.jpabooking.model.dto.form;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

import static se.lexicon.jpabooking.validation.messages.ValidationMessages.MANDATORY_FIELD;

@Validated
public class AddressForm implements Serializable {
    
    private String id;
    @NotBlank(message = MANDATORY_FIELD)
    private String streetAddress;
    @NotBlank(message = MANDATORY_FIELD)
    private String zipCode;
    @NotBlank(message = MANDATORY_FIELD)
    private String city;

    public AddressForm() {
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
