package se.lexicon.jpabooking;

import com.github.javafaker.Faker;
import se.lexicon.jpabooking.model.entity.Address;
import se.lexicon.jpabooking.model.entity.Patient;
import se.lexicon.jpabooking.model.entity.Premises;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class FakerGenerator {

    private static final FakerGenerator INSTANCE = new FakerGenerator();

    public static FakerGenerator getInstance(){
        return INSTANCE;
    }

    private final Faker faker = Faker.instance();

    private FakerGenerator(){}

    public LocalDate toLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public Patient randomPatient(){
        Patient patient = new Patient();
        patient.setFirstName(faker.name().firstName());
        patient.setLastName(faker.name().lastName());
        patient.setPnr(faker.number().digits(12));
        patient.setBirthDate(toLocalDate(faker.date().past(50000, TimeUnit.DAYS)));
        return patient;
    }

    public Premises randomPremises(){
        Premises premises = new Premises();
        premises.setName(faker.lorem().characters(5, 255));
        return premises;
    }

    public Address randomAddress(){
        Address address = new Address();
        address.setStreetAddress(faker.address().streetAddress());
        address.setZipCode(faker.address().zipCode());
        address.setCity(faker.address().city());
        return address;
    }




}
