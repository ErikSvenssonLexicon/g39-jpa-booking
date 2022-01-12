package se.lexicon.jpabooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import se.lexicon.jpabooking.model.dto.form.ContactInfoForm;
import se.lexicon.jpabooking.model.dto.form.PatientForm;
import se.lexicon.jpabooking.model.dto.view.PatientDTO;
import se.lexicon.jpabooking.service.facade.PatientService;
import se.lexicon.jpabooking.validation.OnPost;
import se.lexicon.jpabooking.validation.OnPut;

@RestController
public class PatientController {

    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/api/v1/patients")
    public ResponseEntity<PatientDTO> create(@Validated(OnPost.class) @RequestBody PatientForm form){
        return ResponseEntity.status(201).body(patientService.create(form));
    }

    @GetMapping("/api/v1/patients")
    public ResponseEntity<?> find(
            @RequestParam(name = "search", defaultValue = "all") String search,
            @RequestParam(name = "value", defaultValue = "") String value){

        switch (search.toLowerCase()){
            case "name":
                return ResponseEntity.ok(patientService.searchByName(value));
            case "pnr":
                return ResponseEntity.ok(patientService.findByPnr(value));
            case "all":
                return ResponseEntity.ok(patientService.findAll());
            default:
                throw new IllegalArgumentException("Invalid search value: " + search + ". Valid search values are 'all', 'pnr' and 'name'.");
        }
    }

    @GetMapping("/api/v1/patients/{id}")
    public ResponseEntity<PatientDTO> findById(@PathVariable("id") String id){
        return ResponseEntity.ok(patientService.findById(id));
    }

    @PutMapping("/api/v1/patients/{id}")
    public ResponseEntity<PatientDTO> update(@PathVariable("id") String id, @Validated(OnPut.class) @RequestBody PatientForm form){
        return ResponseEntity.ok(patientService.update(id, form));
    }

    @PutMapping("/api/v1/patients/{id}/bookings/add")
    public ResponseEntity<PatientDTO> addBooking(@PathVariable("id") String id, @RequestParam(name = "bookingId") String bookingId){
        return ResponseEntity.ok(patientService.addBooking(id, bookingId));
    }

    @PutMapping("/api/v1/patients{id}/bookings/remove")
    public ResponseEntity<PatientDTO> removeBooking(@PathVariable("id") String id, @RequestParam(name = "bookingId") String bookingId){
        return ResponseEntity.ok(patientService.removeBooking(id, bookingId));
    }

    public ResponseEntity<PatientDTO> updatePatientContactInfo(@PathVariable("id") String id, @Validated(OnPut.class) @RequestBody ContactInfoForm contactInfoForm){
        return ResponseEntity.ok(patientService.updateContactInfo(id, contactInfoForm));
    }

    @DeleteMapping("/api/v1/patients/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id){
        patientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
