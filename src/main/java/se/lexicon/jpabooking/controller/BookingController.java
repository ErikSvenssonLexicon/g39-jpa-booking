package se.lexicon.jpabooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import se.lexicon.jpabooking.service.entity.BookingEntityService;

@Controller
public class BookingController {

    private final BookingEntityService bookingEntityService;

    @Autowired
    public BookingController(BookingEntityService bookingEntityService) {
        this.bookingEntityService = bookingEntityService;
    }

    @GetMapping("/bookings")
    public String find(@RequestParam(name = "search") String search, @RequestParam(name = "value", required = false) String value){
        throw new RuntimeException("Not yet implemented");
    }

    @GetMapping("/bookings/{id}")
    public String findById(@PathVariable(name = "id") String id, Model model){
        model.addAttribute("booking", bookingEntityService.findById(id));
        return "booking";
    }

}
