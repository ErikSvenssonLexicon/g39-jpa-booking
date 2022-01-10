package se.lexicon.jpabooking.service.facade;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpabooking.model.dto.view.AddressDTO;
import se.lexicon.jpabooking.model.dto.view.BookingDTO;
import se.lexicon.jpabooking.model.dto.view.PremisesDTO;
import se.lexicon.jpabooking.model.entity.Address;
import se.lexicon.jpabooking.model.entity.Booking;
import se.lexicon.jpabooking.model.entity.Premises;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class EntityToDTOConverter {

    public PremisesDTO toFullPremisesDTO(Premises premises){
        if(premises == null) return null;
        PremisesDTO premisesDTO = toSmallPremisesDTO(premises);
        premisesDTO.setAddress(
                toAddressDTO(premises.getAddress())
        );
        List<BookingDTO> bookingDTOS = new ArrayList<>();
        if(!premises.getBookings().isEmpty()){
            bookingDTOS = premises.getBookings().stream()
                    .map(this::toSmallBookingDTO)
                    .collect(Collectors.toList());
        }
        premisesDTO.setBookings(bookingDTOS);
        return premisesDTO;
    }

    public PremisesDTO toSmallPremisesDTO(Premises premises){
        if(premises == null) return null;
        PremisesDTO premisesDTO = new PremisesDTO();
        premisesDTO.setId(premises.getId());
        premisesDTO.setName(premises.getName());
        return premisesDTO;
    }

    public BookingDTO toSmallBookingDTO(Booking booking){
        if(booking == null) return null;
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setDateTime(booking.getDateTime());
        bookingDTO.setPrice(booking.getPrice());
        bookingDTO.setAdministratorId(booking.getAdministratorId());
        bookingDTO.setVaccineType(booking.getVaccineType());
        bookingDTO.setVacant(booking.isVacant());
        return bookingDTO;
    }

    public AddressDTO toAddressDTO(Address address){
        if(address == null) return null;
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(address.getId());
        addressDTO.setStreetAddress(address.getStreetAddress());
        addressDTO.setZipCode(address.getZipCode());
        addressDTO.setCity(address.getCity());
        return addressDTO;
    }

}
