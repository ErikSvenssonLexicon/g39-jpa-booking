package se.lexicon.jpabooking.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpabooking.model.dto.form.ContactInfoForm;
import se.lexicon.jpabooking.model.dto.form.PatientForm;
import se.lexicon.jpabooking.model.dto.view.PatientDTO;
import se.lexicon.jpabooking.service.entity.PatientEntityService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PatientServiceImpl implements PatientService{

    private final PatientEntityService patientEntityService;
    private final DTOService dtoService;

    @Autowired
    public PatientServiceImpl(PatientEntityService patientEntityService, DTOService dtoService) {
        this.patientEntityService = patientEntityService;
        this.dtoService = dtoService;
    }

    @Override
    public PatientDTO create(PatientForm patientForm) {
        return dtoService.toFullPatientDTO(patientEntityService.create(patientForm));
    }

    @Override
    public PatientDTO findById(String id) {
        return dtoService.toFullPatientDTO(patientEntityService.findById(id));
    }

    @Override
    public List<PatientDTO> findAll() {
        return patientEntityService.findAll().stream()
                .map(dtoService::toSmallPatientDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PatientDTO update(String id, PatientForm patientForm) {
        return dtoService.toFullPatientDTO(patientEntityService.update(id, patientForm));
    }

    @Override
    public void delete(String id) {
        patientEntityService.delete(id);
    }

    @Override
    public PatientDTO findByPnr(String pnr) {
        return dtoService.toSmallPatientDTO(patientEntityService.findByPnr(pnr));
    }

    @Override
    public List<PatientDTO> searchByName(String name) {
        return patientEntityService.searchByName(name).stream()
                .map(dtoService::toSmallPatientDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PatientDTO addBooking(String id, String bookingId) {
        return dtoService.toFullPatientDTO(patientEntityService.addBooking(id, bookingId));
    }

    @Override
    public PatientDTO removeBooking(String id, String bookingId) {
        return dtoService.toFullPatientDTO(patientEntityService.removeBooking(id, bookingId));
    }

    @Override
    public PatientDTO updateContactInfo(String id, ContactInfoForm contactInfoForm) {
        return dtoService.toFullPatientDTO(patientEntityService.updateContactInfo(id, contactInfoForm));
    }
}
