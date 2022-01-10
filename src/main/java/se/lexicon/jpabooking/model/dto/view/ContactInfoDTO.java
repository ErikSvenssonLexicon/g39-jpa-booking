package se.lexicon.jpabooking.model.dto.view;

import java.io.Serializable;

public class ContactInfoDTO implements Serializable {
    private String id;
    private String email;
    private String phone;

    public ContactInfoDTO(String id, String email, String phone) {
        this.id = id;
        this.email = email;
        this.phone = phone;
    }

    public ContactInfoDTO() {
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
