package se.lexicon.jpabooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.lexicon.jpabooking.model.dto.form.AppUserForm;
import se.lexicon.jpabooking.model.dto.form.ContactInfoForm;
import se.lexicon.jpabooking.model.dto.form.PatientForm;
import se.lexicon.jpabooking.model.entity.Patient;
import se.lexicon.jpabooking.service.entity.PatientEntityService;

@Controller
public class PublicController {

    private final PatientEntityService patientEntityService;

    @Autowired
    public PublicController(PatientEntityService patientEntityService) {
        this.patientEntityService = patientEntityService;
    }

    @GetMapping(value = {"/", "/index"})
    public String getIndexPage(){
        return "index";
    }

    @GetMapping("/public/register")
    public String getRegisterPatientForm(Model model){
        PatientForm form = new PatientForm();
        form.setContactInfo(new ContactInfoForm());
        form.setUserCredentials(new AppUserForm());
        model.addAttribute("form", form);
        return "patient-form";
    }

    @PostMapping("/public/register/process")
    public String processRegistration(@ModelAttribute(name = "form") PatientForm form, @RequestParam("repeat") String passwordRepeat){
        if(form.getUserCredentials().getPassword().equals(passwordRepeat)){
            System.out.println("Password matches!!");
        }

        Patient patient = patientEntityService.create(form);
        return "redirect:/patients/"+patient.getId();
    }

}
