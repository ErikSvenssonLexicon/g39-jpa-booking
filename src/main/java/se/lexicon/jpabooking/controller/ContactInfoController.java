package se.lexicon.jpabooking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import se.lexicon.jpabooking.model.dto.form.ContactInfoForm;
import se.lexicon.jpabooking.model.dto.view.ContactInfoDTO;
import se.lexicon.jpabooking.service.facade.ContactInfoService;
import se.lexicon.jpabooking.validation.OnPost;
import se.lexicon.jpabooking.validation.OnPut;

import java.util.List;

@RestController
public class ContactInfoController {

    private final ContactInfoService contactInfoService;

    public ContactInfoController(ContactInfoService contactInfoService) {
        this.contactInfoService = contactInfoService;
    }

    @PostMapping("/api/v1/contact")
    public ResponseEntity<ContactInfoDTO> create(@Validated(OnPost.class) @RequestBody ContactInfoForm contactInfoForm){
        return ResponseEntity.status(201).body(contactInfoService.create(contactInfoForm));
    }

    @GetMapping("/api/v1/contact")
    public ResponseEntity<List<ContactInfoDTO>> find(){
        return ResponseEntity.ok(contactInfoService.findAll());
    }

    @GetMapping("/api/v1/contact/{id}")
    public ResponseEntity<ContactInfoDTO> findById(@PathVariable("id") String id){
        return ResponseEntity.ok(contactInfoService.findById(id));
    }

    @PutMapping("/api/v1/contact/{id}")
    public ResponseEntity<ContactInfoDTO> update(@PathVariable("id") String id, @Validated(OnPut.class) @RequestBody ContactInfoForm contactInfoForm){
        return ResponseEntity.ok(contactInfoService.update(id, contactInfoForm));
    }

    @DeleteMapping("/api/v1/contact/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id){
        contactInfoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
