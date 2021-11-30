package se.lexicon.jpabooking.model.dto.form;

import org.springframework.validation.annotation.Validated;
import se.lexicon.jpabooking.validation.OnPost;
import se.lexicon.jpabooking.validation.OnPut;
import se.lexicon.jpabooking.validation.UniquePnr;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;

import static se.lexicon.jpabooking.validation.messages.ValidationMessages.*;

@Validated
public class PatientForm implements Serializable {
    @NotBlank(message = MANDATORY_FIELD, groups = OnPut.class)
    private String id;
    @NotBlank(message = MANDATORY_FIELD, groups = {OnPut.class, OnPost.class})
    @Pattern(message = INVALID_PNR, regexp = PNR_REGEX, groups = {OnPut.class, OnPost.class})
    @UniquePnr(message = UNIQUE_PNR, groups=OnPost.class)
    private String pnr;
    @NotBlank(message = MANDATORY_FIELD, groups = {OnPut.class, OnPost.class})
    private String firstName;
    @NotBlank(message = MANDATORY_FIELD, groups = {OnPut.class, OnPost.class})
    private String lastName;
    @NotNull(message = MANDATORY_FIELD, groups = {OnPut.class, OnPost.class})
    private LocalDate birthDate;

    @NotNull(message = MANDATORY_FORM, groups = OnPost.class)
    @Valid private ContactInfoForm contactInfo;

    public PatientForm() {
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

    public ContactInfoForm getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfoForm contactInfo) {
        this.contactInfo = contactInfo;
    }
}
