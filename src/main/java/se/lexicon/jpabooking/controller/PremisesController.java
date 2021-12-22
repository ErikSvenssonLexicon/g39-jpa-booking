package se.lexicon.jpabooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import se.lexicon.jpabooking.model.dto.form.AddressForm;
import se.lexicon.jpabooking.model.dto.form.BookingForm;
import se.lexicon.jpabooking.model.dto.form.PremisesForm;
import se.lexicon.jpabooking.model.entity.Premises;
import se.lexicon.jpabooking.service.entity.PremisesEntityService;

@Controller
public class PremisesController {

    private final PremisesEntityService premisesEntityService;

    @Autowired
    public PremisesController(PremisesEntityService premisesEntityService) {
        this.premisesEntityService = premisesEntityService;
    }

    public String findAll(){
        return null;
    }

    @GetMapping("/premises/create")
    public String create(Model model){
        PremisesForm form = new PremisesForm();
        form.setAddress(new AddressForm());
        model.addAttribute("form", form);
        return "premises-form";
    }

    @PostMapping("/premises/create/process")
    public String processForm(@ModelAttribute("form") PremisesForm form){
        Premises premises = premisesEntityService.create(form);
        return "redirect:/premises/"+premises.getId();
    }

    public String update(){
        return "premises-form";
    }

    @GetMapping("/premises/{id}")
    public String findById(@PathVariable(name = "id") String id, Model model){
        model.addAttribute("premises", premisesEntityService.findById(id));
        return "premises";
    }

    @GetMapping("/premises/{id}/bookings/create")
    public String getBookingForm(@PathVariable("id") String id, Model model){
        BookingForm form = new BookingForm();
        model.addAttribute("form", form);
        model.addAttribute("premisesId", id);
        return "booking-form";
    }

    @PostMapping("/premises/{id}/bookings/create/process")
    public String processBookingForm(@PathVariable("id") String id, @ModelAttribute(name = "form") BookingForm form){
        premisesEntityService.addNewBooking(id, form);
        return "redirect:/premises/"+id;
    }

    public String removeBooking(){
        return "premises";
    }

}
