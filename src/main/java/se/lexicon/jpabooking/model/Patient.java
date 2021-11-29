package se.lexicon.jpabooking.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.time.LocalDate;

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

    public Patient(String id, String pnr, String firstName, String lastName, LocalDate birthDate) {
        this.id = id;
        this.pnr = pnr;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public Patient() {
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
}
