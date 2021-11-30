package se.lexicon.jpabooking.model.dto.form;

import org.springframework.validation.annotation.Validated;
import se.lexicon.jpabooking.validation.OnPost;
import se.lexicon.jpabooking.validation.OnPut;
import se.lexicon.jpabooking.validation.UniqueUsername;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

import static se.lexicon.jpabooking.validation.messages.ValidationMessages.*;

@Validated
public class AppUserForm implements Serializable {

    @NotBlank(message = MANDATORY_FIELD, groups = OnPut.class)
    private String id;
    @NotBlank(message = MANDATORY_FIELD, groups = {OnPost.class, OnPut.class})
    @Size(min = 6, max = 100, message = USERNAME_SIZE, groups = {OnPost.class, OnPut.class})
    @UniqueUsername(message = USERNAME_TAKEN, groups = OnPost.class)
    private String username;
    @NotBlank(message = MANDATORY_FIELD, groups = OnPost.class)
    @Pattern(message = WEAK_PASSWORD, regexp = PASSWORD_REGEX, groups = OnPost.class)
    private String password;

    public AppUserForm() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
