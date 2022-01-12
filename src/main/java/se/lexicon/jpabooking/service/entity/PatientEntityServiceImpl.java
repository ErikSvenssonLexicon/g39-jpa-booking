package se.lexicon.jpabooking.service.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpabooking.database.BookingDAO;
import se.lexicon.jpabooking.database.PatientDAO;
import se.lexicon.jpabooking.exception.AppResourceNotFoundException;
import se.lexicon.jpabooking.model.dto.form.ContactInfoForm;
import se.lexicon.jpabooking.model.dto.form.PatientForm;
import se.lexicon.jpabooking.model.entity.Booking;
import se.lexicon.jpabooking.model.entity.Patient;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PatientEntityServiceImpl implements PatientEntityService{

    private final PatientDAO patientDAO;
    private final AppUserEntityService appUserEntityService;
    private final ContactInfoEntityService contactInfoEntityService;
    private final BookingDAO bookingDAO;

    @Autowired
    public PatientEntityServiceImpl(PatientDAO patientDAO, AppUserEntityService appUserEntityService, ContactInfoEntityService contactInfoEntityService, BookingDAO bookingDAO) {
        this.patientDAO = patientDAO;
        this.appUserEntityService = appUserEntityService;
        this.contactInfoEntityService = contactInfoEntityService;
        this.bookingDAO = bookingDAO;
    }

    @Override
    public Patient create(PatientForm patientForm) {
        if(patientForm == null){
            throw new IllegalArgumentException("PatientForm was null");
        }
        Patient patient = new Patient();
        patient.setPnr(patientForm.getPnr());
        patient.setFirstName(patientForm.getFirstName().trim());
        patient.setLastName(patientForm.getLastName().trim());
        patient.setBirthDate(patientForm.getBirthDate());
        patient.setUserCredentials(
                appUserEntityService.create(patientForm.getUserCredentials())
        );
        patient.setContactInfo(
                contactInfoEntityService.create(patientForm.getContactInfo())
        );

        return patientDAO.save(patient);
    }

    @Override
    public Patient findById(String id) {
        return patientDAO.findById(id)
                .orElseThrow(() -> new AppResourceNotFoundException("Could not find Patient with id " + id));
    }

    @Override
    public List<Patient> findAll() {
        return patientDAO.findAll();
    }

    @Override
    public Patient update(String id, PatientForm patientForm) {
        Patient patient = findById(id);
        Optional<Patient> optional = patientDAO.findByPnr(patientForm.getPnr());

        if(optional.isPresent() && !optional.get().getId().equals(id)){
            throw new IllegalArgumentException("Pnr specified is already in use");
        }

        patient.setPnr(patientForm.getPnr());
        patient.setFirstName(patientForm.getFirstName().trim());
        patient.setLastName(patientForm.getLastName().trim());
        patient.setBirthDate(patientForm.getBirthDate());

        return patientDAO.save(patient);
    }

    @Override
    public void delete(String id) {
        Patient patient = findById(id);
        patient.setVaccineBookings(null);
        patientDAO.deleteById(id);
    }

    @Override
    public Patient findByPnr(String pnr) {
        return patientDAO.findByPnr(pnr.replaceAll(" ","").replaceAll("-","").trim())
                .orElseThrow(() -> new AppResourceNotFoundException("Could not find Patient with provided pnr"));
    }

    @Override
    public List<Patient> searchByName(String name) {
        return patientDAO.searchByName(name);
    }

    @Override
    public Patient addBooking(String id, String bookingId) {
       Patient patient = findById(id);
       Booking booking = bookingDAO.findById(bookingId)
               .orElseThrow(() -> new AppResourceNotFoundException("Could not find Booking with id " + bookingId));

       patient.addBooking(booking);

       patient = patientDAO.save(patient);
       return patient;
    }

    @Override
    public Patient removeBooking(String id, String bookingId) {
        Patient patient = findById(id);
        Booking booking = bookingDAO.findById(bookingId)
                .orElseThrow(() -> new AppResourceNotFoundException("Could not find Booking with id " + bookingId));

        patient.removeBooking(booking);

        patient = patientDAO.save(patient);
        return patient;
    }

    @Override
    public Patient updateContactInfo(String id, ContactInfoForm form) {
        Patient patient = findById(id);
        patient.setContactInfo(
                contactInfoEntityService.update(form.getId(), form)
        );
        return patientDAO.save(patient);
    }
}
