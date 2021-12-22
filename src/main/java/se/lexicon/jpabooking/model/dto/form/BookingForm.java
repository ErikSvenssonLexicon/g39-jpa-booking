package se.lexicon.jpabooking.model.dto.form;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import se.lexicon.jpabooking.validation.OnPost;
import se.lexicon.jpabooking.validation.OnPut;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static se.lexicon.jpabooking.validation.messages.ValidationMessages.DATETIME_IN_PAST;
import static se.lexicon.jpabooking.validation.messages.ValidationMessages.MANDATORY_FIELD;

@Validated
public class BookingForm implements Serializable {

    @NotBlank(groups = OnPut.class)
    private String id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull(message = MANDATORY_FIELD, groups = {OnPost.class, OnPut.class})
    @FutureOrPresent(message = DATETIME_IN_PAST, groups = {OnPost.class, OnPut.class})
    private LocalDateTime dateTime;
    @NotNull(message = MANDATORY_FIELD, groups = {OnPost.class, OnPut.class})
    private BigDecimal price;
    private String administratorId;
    @NotBlank(message = MANDATORY_FIELD, groups = {OnPost.class, OnPut.class})
    private String vaccineType;
    private boolean vacant;

    public BookingForm() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getAdministratorId() {
        return administratorId;
    }

    public void setAdministratorId(String administratorId) {
        this.administratorId = administratorId;
    }

    public String getVaccineType() {
        return vaccineType;
    }

    public void setVaccineType(String vaccineType) {
        this.vaccineType = vaccineType;
    }

    public boolean isVacant() {
        return vacant;
    }

    public void setVacant(boolean vacant) {
        this.vacant = vacant;
    }
}
