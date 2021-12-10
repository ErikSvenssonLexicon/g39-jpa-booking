package se.lexicon.jpabooking.model.dto.form;

import org.springframework.validation.annotation.Validated;
import se.lexicon.jpabooking.validation.OnPost;
import se.lexicon.jpabooking.validation.OnPut;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

import static se.lexicon.jpabooking.validation.messages.ValidationMessages.MANDATORY_FIELD;
import static se.lexicon.jpabooking.validation.messages.ValidationMessages.MANDATORY_FORM;

@Validated
public class PremisesForm implements Serializable {

    @NotBlank(message = MANDATORY_FIELD, groups = OnPut.class)
    private String id;
    @NotBlank(message = MANDATORY_FIELD, groups = {OnPut.class, OnPost.class})
    private String name;
    @NotNull(message = MANDATORY_FORM, groups = OnPost.class)
    @Valid private AddressForm address;

    private List<@Valid BookingForm> bookings;

    public PremisesForm() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressForm getAddress() {
        return address;
    }

    public void setAddress(AddressForm address) {
        this.address = address;
    }

    public List<BookingForm> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingForm> bookings) {
        this.bookings = bookings;
    }
}
