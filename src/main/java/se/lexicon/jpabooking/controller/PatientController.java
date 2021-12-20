package se.lexicon.jpabooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import se.lexicon.jpabooking.service.entity.PatientEntityService;

@Controller
public class PatientController {

    private final PatientEntityService patientEntityService;

    @Autowired
    public PatientController(PatientEntityService patientEntityService) {
        this.patientEntityService = patientEntityService;
    }

    @GetMapping("/patients/{id}")
    public String findPatientById(@PathVariable("id") String id, Model model){
        model.addAttribute("patient", patientEntityService.findById(id));
        return "patient";
    }

}
