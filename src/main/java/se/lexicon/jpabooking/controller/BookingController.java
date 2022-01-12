package se.lexicon.jpabooking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import se.lexicon.jpabooking.model.dto.form.BookingForm;
import se.lexicon.jpabooking.model.dto.view.BookingDTO;
import se.lexicon.jpabooking.service.facade.BookingService;
import se.lexicon.jpabooking.validation.OnPost;
import se.lexicon.jpabooking.validation.OnPut;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/api/v1/bookings")
    public ResponseEntity<BookingDTO> createBooking(@Validated(OnPost.class) @RequestBody BookingForm bookingForm){
        return ResponseEntity.status(201).body(
                bookingService.create(bookingForm)
        );
    }

    @GetMapping("/api/v1/bookings")
    public ResponseEntity<List<BookingDTO>> find(
            @RequestParam(value = "search", defaultValue = "all") String search,
            @RequestParam(value = "value", required = false ) List<String> values
            ){
        List<BookingDTO> bookingDTOS;
        switch (search.toLowerCase()){
            case "between":
                bookingDTOS = bookingService.findByDateTimeBetween(
                        LocalDateTime.parse(values.get(0)), LocalDateTime.parse(values.get(1))
                );
                break;
            case "before":
                bookingDTOS = bookingService.findByDateTimeBefore(LocalDateTime.parse(values.get(0)));
                break;
            case "after":
                bookingDTOS = bookingService.findByDateTimeAfter(LocalDateTime.parse(values.get(0)));
                break;
            case "administrator":
                bookingDTOS = bookingService.findByAdministratorId(values.get(0));
                break;
            case "vaccine":
                bookingDTOS = bookingService.findByVaccineType(values.get(0));
                break;
            case "vacant":
                bookingDTOS = bookingService.findByVacantStatus(Boolean.parseBoolean(values.get(0)));
                break;
            case "city":
                bookingDTOS = bookingService.findAvailableTimesInCity(values.get(0));
                break;
            case "all":
                bookingDTOS = bookingService.findAll();
                break;
            default:
                throw new IllegalArgumentException("Invalid search value: " + search + ". Valid search values are 'all', 'between' 'before', 'after', 'administrator', 'vaccine', 'vacant' and 'city'.");
        }

        return ResponseEntity.ok(bookingDTOS);
    }

    @GetMapping("/api/v1/bookings/{id}")
    public ResponseEntity<BookingDTO> findById(@PathVariable("id") String id){
        return ResponseEntity.ok(bookingService.findById(id));
    }

    @PutMapping("/api/v1/bookings/{id}")
    public ResponseEntity<BookingDTO> update(@PathVariable("id") String id, @Validated(OnPut.class) @RequestBody BookingForm bookingForm){
        return ResponseEntity.ok(bookingService.update(id, bookingForm));
    }

    @DeleteMapping("/api/v1/bookings/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") String id){
        bookingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
