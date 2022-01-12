package se.lexicon.jpabooking.service.facade;

import se.lexicon.jpabooking.model.dto.form.ContactInfoForm;
import se.lexicon.jpabooking.model.dto.form.PatientForm;
import se.lexicon.jpabooking.model.dto.view.PatientDTO;
import se.lexicon.jpabooking.service.entity.GenericEntityService;

import java.util.List;

public interface PatientService extends GenericEntityService<PatientDTO, PatientForm> {
    PatientDTO findByPnr(String pnr);
    List<PatientDTO> searchByName(String name);
    PatientDTO addBooking(String id, String bookingId);
    PatientDTO removeBooking(String id, String bookingId);
    PatientDTO updateContactInfo(String id, ContactInfoForm contactInfoForm);
}
