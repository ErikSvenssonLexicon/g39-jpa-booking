package se.lexicon.jpabooking.model.dto.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import se.lexicon.jpabooking.model.entity.Address;

import java.io.Serializable;
import java.util.List;

public class PremisesDTO implements Serializable {
    private String id;
    private String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private AddressDTO address;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<BookingDTO> bookings;

    public PremisesDTO(String id, String name, AddressDTO address, List<BookingDTO> bookings) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.bookings = bookings;
    }

    public PremisesDTO() {
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

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public List<BookingDTO> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingDTO> bookings) {
        this.bookings = bookings;
    }
}
