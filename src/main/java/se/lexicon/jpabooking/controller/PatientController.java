package se.lexicon.jpabooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import se.lexicon.jpabooking.model.dto.form.ContactInfoForm;
import se.lexicon.jpabooking.model.dto.form.PatientForm;
import se.lexicon.jpabooking.model.entity.Patient;
import se.lexicon.jpabooking.service.entity.PatientEntityService;
import se.lexicon.jpabooking.validation.OnPut;

@Controller
public class PatientController {

    private final PatientEntityService patientEntityService;

    @Autowired
    public PatientController(PatientEntityService patientEntityService) {
        this.patientEntityService = patientEntityService;
    }

    @PreAuthorize("#id == authentication.principal.patientId || hasAnyRole('PREMISES_ADMIN','SUPER_ADMIN')")
    @GetMapping("/patients/{id}")
    public String findPatientById(@PathVariable("id") String id, Model model){
        model.addAttribute("patient", patientEntityService.findById(id));
        return "patient";
    }

    @PreAuthorize("#id == authentication.principal.patientId || hasAnyRole('PREMISES_ADMIN','SUPER_ADMIN')")
    @GetMapping("/patients/{id}/bookings")
    public String findBookingsByPatientId(@PathVariable("id") String id, Model model){
        model.addAttribute("patient", patientEntityService.findById(id));
        return "patient-bookings";
    }

    @PreAuthorize("#id == authentication.principal.patientId || hasRole('SUPER_ADMIN')")
    @GetMapping("/patients/{id}/update")
    public String getUpdateForm(@PathVariable("id") String id, Model model){
        Patient patient = patientEntityService.findById(id);
        PatientForm form = new PatientForm();
        form.setId(patient.getId());
        form.setPnr(patient.getPnr());
        form.setBirthDate(patient.getBirthDate());
        form.setFirstName(patient.getFirstName());
        form.setLastName(patient.getLastName());

        ContactInfoForm contactInfoForm = new ContactInfoForm();
        contactInfoForm.setId(patient.getContactInfo().getId());
        contactInfoForm.setEmail(patient.getContactInfo().getEmail());
        contactInfoForm.setPhone(patient.getContactInfo().getPhone());

        form.setContactInfo(contactInfoForm);

        model.addAttribute("form", form);
        model.addAttribute("actionUrl", "/patients/"+patient.getId()+"/update/process");
        model.addAttribute("action", "PUT");
        return "patient-form";
    }

    @PreAuthorize("#id == authentication.principal.patientId || hasRole('SUPER_ADMIN')")
    @PostMapping("/patients/{id}/update/process")
    public String processUpdate(@PathVariable(name = "id") String id,
                                @Validated(value = OnPut.class) @ModelAttribute(name = "form") PatientForm form,
                                BindingResult bindingResult){
        //Todo Fix implementation on update/process on Patient
        throw new RuntimeException("Not yet implemented");
    }

}
