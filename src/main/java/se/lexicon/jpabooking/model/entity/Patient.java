package se.lexicon.jpabooking.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static se.lexicon.jpabooking.model.constants.EntityConstants.GENERATOR;
import static se.lexicon.jpabooking.model.constants.EntityConstants.UUID_GENERATOR;

@Entity
public class Patient {

    @Id
    @GeneratedValue(generator = GENERATOR)
    @GenericGenerator(name = GENERATOR, strategy = UUID_GENERATOR)
    @Column(name = "id", updatable = false)
    private String id;
    @Column(name = "pnr", unique = true, length = 20)
    private String pnr;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "fk_contact_info_id")
    private ContactInfo contactInfo;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "fk_app_user_id", table = "patient")
    private AppUser userCredentials;

    @OneToMany(
            cascade = {CascadeType.REFRESH, CascadeType.DETACH},
            fetch = FetchType.LAZY,
            mappedBy = "patient"
    )
    private List<Booking> vaccineBookings;

    public Patient(String id, String pnr, String firstName, String lastName, LocalDate birthDate) {
        this.id = id;
        this.pnr = pnr;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public Patient() {
    }

    public AppUser getUserCredentials() {
        return userCredentials;
    }

    public void setUserCredentials(AppUser userCredentials) {
        this.userCredentials = userCredentials;
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

    public List<Booking> getVaccineBookings() {
        if(vaccineBookings == null) vaccineBookings = new ArrayList<>();
        return vaccineBookings;
    }

    public void setVaccineBookings(List<Booking> vaccineBookings) {
        if(vaccineBookings == null) vaccineBookings = new ArrayList<>();
        if(vaccineBookings.isEmpty()){
            if(this.vaccineBookings != null){
                this.vaccineBookings.forEach(booking -> booking.setPatient(null));
            }
        }else {
            vaccineBookings.forEach(booking -> booking.setPatient(this));
        }
        this.vaccineBookings = vaccineBookings;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public void addBooking(Booking booking){
        if(booking == null) throw new IllegalArgumentException("Booking was null");
        if(vaccineBookings == null) vaccineBookings = new ArrayList<>();
        if(!vaccineBookings.contains(booking)){
            vaccineBookings.add(booking);
            booking.setPatient(this);
        }
    }

    public void removeBooking(Booking booking){
        if(booking == null) throw new IllegalArgumentException("Booking was null");
        if(vaccineBookings == null) vaccineBookings = new ArrayList<>();
        if(vaccineBookings.contains(booking)){
            vaccineBookings.remove(booking);
            booking.setPatient(null);
        }
    }
}
