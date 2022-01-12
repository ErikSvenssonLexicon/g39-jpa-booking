package se.lexicon.jpabooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import se.lexicon.jpabooking.model.dto.form.BookingForm;
import se.lexicon.jpabooking.model.dto.form.PremisesForm;
import se.lexicon.jpabooking.model.dto.view.PremisesDTO;
import se.lexicon.jpabooking.service.facade.PremisesService;
import se.lexicon.jpabooking.validation.OnPost;
import se.lexicon.jpabooking.validation.OnPut;

import java.util.List;

@RestController
public class PremisesController {

    private final PremisesService premisesService;

    @Autowired
    public PremisesController(PremisesService premisesService) {
        this.premisesService = premisesService;
    }

    @PostMapping("/api/v1/premises")
    public ResponseEntity<PremisesDTO> create(@Validated(OnPost.class) @RequestBody PremisesForm form){
        PremisesDTO premises = premisesService.create(form);
        return ResponseEntity.status(201).body(premises);
    }

    @PostMapping("/api/v1/premises/{id}/bookings")
    public ResponseEntity<PremisesDTO> addBooking(@PathVariable("id") String id, @Validated(OnPost.class) @RequestBody BookingForm form){
        return ResponseEntity.ok(premisesService.addNewBooking(id, form));
    }

    @GetMapping("/api/v1/premises")
    public ResponseEntity<List<PremisesDTO>> find(){
        return ResponseEntity.ok(
                premisesService.findAll()
        );
    }

    @GetMapping("/api/v1/premises/{id}")
    public ResponseEntity<PremisesDTO> findById(@PathVariable("id") String id){
        return ResponseEntity.ok(premisesService.findById(id));
    }

    @PutMapping("/api/v1/premises/{id}")
    public ResponseEntity<PremisesDTO> update(@PathVariable("id") String id, @Validated(OnPut.class) @RequestBody PremisesForm form){
        return ResponseEntity.ok(premisesService.update(id, form));
    }

    @DeleteMapping("/api/v1/premises/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id){
        premisesService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
