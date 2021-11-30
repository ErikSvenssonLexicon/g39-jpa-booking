package se.lexicon.jpabooking.model.dto.form;

import org.springframework.validation.annotation.Validated;
import se.lexicon.jpabooking.validation.OnPost;
import se.lexicon.jpabooking.validation.OnPut;
import se.lexicon.jpabooking.validation.UniqueEmail;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

import static se.lexicon.jpabooking.validation.messages.ValidationMessages.MANDATORY_FIELD;
import static se.lexicon.jpabooking.validation.messages.ValidationMessages.UNIQUE_EMAIL;

@Validated
public class ContactInfoForm implements Serializable {

    @NotBlank(message = MANDATORY_FIELD, groups = OnPut.class)
    private String id;
    @NotBlank(message = MANDATORY_FIELD, groups = {OnPut.class, OnPost.class})
    @UniqueEmail(message = UNIQUE_EMAIL, groups = OnPost.class)
    private String email;
    private String phone;

    public ContactInfoForm() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
