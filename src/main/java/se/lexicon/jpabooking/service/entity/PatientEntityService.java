package se.lexicon.jpabooking.service.entity;

import se.lexicon.jpabooking.model.dto.form.ContactInfoForm;
import se.lexicon.jpabooking.model.dto.form.PatientForm;
import se.lexicon.jpabooking.model.entity.Patient;

import java.util.List;

public interface PatientEntityService extends GenericEntityService<Patient, PatientForm>{
    Patient findByPnr(String pnr);
    List<Patient> searchByName(String name);
    Patient addBooking(String id, String bookingId);
    Patient removeBooking(String id, String bookingId);
    Patient updateContactInfo(String id, ContactInfoForm form);
}
