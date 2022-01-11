package se.lexicon.jpabooking.service.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpabooking.FakerGenerator;
import se.lexicon.jpabooking.exception.AppResourceNotFoundException;
import se.lexicon.jpabooking.model.dto.form.BookingForm;
import se.lexicon.jpabooking.model.entity.Address;
import se.lexicon.jpabooking.model.entity.Booking;
import se.lexicon.jpabooking.model.entity.Patient;
import se.lexicon.jpabooking.model.entity.Premises;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureTestEntityManager
@Transactional
@DirtiesContext
class BookingEntityServiceImplTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private BookingEntityServiceImpl testObject;

    private final FakerGenerator fakerGenerator = FakerGenerator.getInstance();

    public List<Premises> premises(){
        return Arrays.asList(
                new Premises(null, "Vårdcentral Hälsan"),
                fakerGenerator.randomPremises(),
                fakerGenerator.randomPremises(),
                fakerGenerator.randomPremises()
        );
    }

    public List<Address> addresses(){
        return Arrays.asList(
                new Address(null, "Storgatan 1", "53256", "Kalmar"),
                new Address(null, "Storgatan 2", "53256", "Kalmar"),
                new Address(null, "Gatan 4", "12345", "Piteå"),
                fakerGenerator.randomAddress()
        );
    }

    public List<Patient> patients(){
        return Arrays.asList(
                fakerGenerator.randomPatient(),
                fakerGenerator.randomPatient()
        );
    }

    public List<Booking> bookings(){
        return Arrays.asList(
                new Booking(null, LocalDateTime.parse("2022-02-01T13:00"), BigDecimal.ZERO, null, "Covid 19", true),
                new Booking(null, LocalDateTime.parse("2022-02-01T13:00"), BigDecimal.ZERO, null, "Covid 19", true),
                new Booking(null, LocalDateTime.parse("2022-02-04T14:00"), BigDecimal.valueOf(300), null, "Season Flu", true),
                new Booking(null, LocalDateTime.parse("2022-02-06T08:30"), BigDecimal.ZERO, null, "Covid 19", true)
        );
    }

    private List<Premises> persistedPremises;
    private List<Patient> persistedPatients;
    private List<Booking> persistedBookings;

    @BeforeEach
    void setUp() {
        List<Premises> premises = premises();
        List<Address> addresses = addresses();
        premises.get(0).setAddress(addresses.get(0));
        premises.get(1).setAddress(addresses.get(1));
        premises.get(2).setAddress(addresses.get(2));
        premises.get(3).setAddress(addresses.get(3));

        persistedPatients = patients().stream().map(em::persist).collect(Collectors.toList());
        persistedPremises = premises.stream().map(em::persist).collect(Collectors.toList());
        persistedBookings = bookings().stream().map(em::persist).collect(Collectors.toList());

        persistedPatients.get(0).addBooking(persistedBookings.get(0));
        persistedPatients.get(1).addBooking(persistedBookings.get(1));

        persistedPremises.get(0).addBooking(persistedBookings.get(0));
        persistedPremises.get(1).addBooking(persistedBookings.get(1));
        persistedPremises.get(2).addBooking(persistedBookings.get(2));
        persistedPremises.get(3).addBooking(persistedBookings.get(3));

        em.flush();
    }

    @Test
    void findByDateTimeBetween() {
        LocalDateTime start = LocalDateTime.parse("2022-02-01T13:05");
        LocalDateTime end = LocalDateTime.parse("2022-02-06T08:30");

        assertEquals(2, testObject.findByDateTimeBetween(start, end).size());
    }

    @Test
    void findByDateTimeBefore() {
        LocalDateTime before = LocalDateTime.parse("2022-02-04T14:00");

        assertEquals(2, testObject.findByDateTimeBefore(before).size());
    }

    @Test
    void findByDateTimeAfter() {
        LocalDateTime after = LocalDateTime.parse("2022-02-04T14:00");

        assertEquals(1, testObject.findByDateTimeAfter(after).size());
    }

    @Test
    void findByAdministratorId() {
        String administratorId = "Administrator 5";
        persistedBookings.get(3).setAdministratorId(administratorId);

        assertEquals(1, testObject.findByAdministratorId(administratorId).size());
    }

    @Test
    void findByVaccineType() {
        assertEquals(3, testObject.findByVaccineType("Covid 19").size());
    }

    @Test
    void findByVacantStatus() {
        List<Booking> result =  testObject.findByVacantStatus(false);

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(booking -> booking.getPatient() != null));
    }

    @Test
    void findAvailableTimesInCity() {
        List<Booking> result = testObject.findAvailableTimesInCity("Piteå");

        assertEquals(1, result.size());
        assertTrue(result.stream().allMatch(booking -> booking.getPatient() == null));
    }

    @Test
    void create() {
        BookingForm bookingForm = new BookingForm();
        LocalDateTime dateTime = LocalDateTime.parse("2022-03-01T16:45");
        String vaccine = "Some vaccine";
        BigDecimal price = BigDecimal.valueOf(300);

        bookingForm.setDateTime(dateTime);
        bookingForm.setPrice(price);
        bookingForm.setVaccineType(vaccine);

        Booking result = testObject.create(bookingForm);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(dateTime, result.getDateTime());
        assertEquals(price, result.getPrice());
        assertEquals(vaccine, result.getVaccineType());
        assertTrue(result.isVacant());
        assertNull(result.getPatient());
        assertNull(result.getPremises());
    }

    @Test
    void create_throws_IllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> testObject.create(null));
    }

    @Test
    void findById() {
        String id = persistedBookings.get(3).getId();
        assertNotNull(testObject.findById(id));
    }

    @Test
    void findById_throws_AppResourceNotFoundException() {
        assertThrows(AppResourceNotFoundException.class, () -> testObject.findById("Foo"));
    }

    @Test
    void findAll() {
        assertEquals(4, testObject.findAll().size());
    }

    @Test
    void update() {
        Booking toUpdate = persistedBookings.get(3);
        BookingForm bookingForm = new BookingForm();
        bookingForm.setId(toUpdate.getId());
        bookingForm.setPrice(BigDecimal.valueOf(400));
        bookingForm.setDateTime(toUpdate.getDateTime());
        bookingForm.setVaccineType(toUpdate.getVaccineType());
        bookingForm.setVacant(toUpdate.isVacant());

        Booking result = testObject.update(toUpdate.getId(), bookingForm);

        assertNotNull(result);
        assertEquals(bookingForm.getId(), result.getId());
        assertEquals(bookingForm.getPrice(), result.getPrice());
        assertEquals(bookingForm.getDateTime(), result.getDateTime());
        assertEquals(bookingForm.getVaccineType(), result.getVaccineType());
        assertEquals(bookingForm.isVacant(), result.isVacant());
    }

    @Test
    void update_throws_IllegalArgumentException() {
        String id = persistedBookings.get(0).getId();
        BookingForm bookingForm = new BookingForm();
        bookingForm.setId(persistedBookings.get(1).getId());

        assertThrows(
                IllegalArgumentException.class,
                () -> testObject.update(id, bookingForm)
        );

    }

    @Test
    void delete() {
        String id = persistedBookings.get(0).getId();
        testObject.delete(id);
        assertNull(em.find(Booking.class, id));
    }
}