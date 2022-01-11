package se.lexicon.jpabooking.model.dto.view;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class PatientDTO implements Serializable {

    private String id;
    private String pnr;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ContactInfoDTO contactInfo;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<BookingDTO> vaccineBookings;

    public PatientDTO(String id, String pnr, String firstName, String lastName, LocalDate birthDate, ContactInfoDTO contactInfo, List<BookingDTO> vaccineBookings) {
        this.id = id;
        this.pnr = pnr;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.contactInfo = contactInfo;
        this.vaccineBookings = vaccineBookings;
    }

    public PatientDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public ContactInfoDTO getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfoDTO contactInfo) {
        this.contactInfo = contactInfo;
    }

    public List<BookingDTO> getVaccineBookings() {
        return vaccineBookings;
    }

    public void setVaccineBookings(List<BookingDTO> vaccineBookings) {
        this.vaccineBookings = vaccineBookings;
    }
}
