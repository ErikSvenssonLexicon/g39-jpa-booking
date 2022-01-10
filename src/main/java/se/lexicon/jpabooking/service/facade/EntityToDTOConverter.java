package se.lexicon.jpabooking.service.facade;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpabooking.model.dto.view.*;
import se.lexicon.jpabooking.model.entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Transactional
public class EntityToDTOConverter implements DTOService{

    @Override
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

    @Override
    public PremisesDTO toSmallPremisesDTO(Premises premises){
        if(premises == null) return null;
        PremisesDTO premisesDTO = new PremisesDTO();
        premisesDTO.setId(premises.getId());
        premisesDTO.setName(premises.getName());
        return premisesDTO;
    }

    @Override
    public BookingDTO toFullBookingDTO(Booking booking){
        if(booking == null) return null;
        BookingDTO bookingDTO = toSmallBookingDTO(booking);
        bookingDTO.setPatient(toSmallPatientDTO(booking.getPatient()));
        bookingDTO.setPremises(toSmallPremisesDTO(booking.getPremises()));
        return bookingDTO;
    }

    @Override
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

    @Override
    public AddressDTO toAddressDTO(Address address){
        if(address == null) return null;
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(address.getId());
        addressDTO.setStreetAddress(address.getStreetAddress());
        addressDTO.setZipCode(address.getZipCode());
        addressDTO.setCity(address.getCity());
        return addressDTO;
    }

    @Override
    public PatientDTO toFullPatientDTO(Patient patient){
        if(patient == null) return null;
        PatientDTO patientDTO = toSmallPatientDTO(patient);
        patientDTO.setContactInfo(toContactInfoDTO(patient.getContactInfo()));
        List<BookingDTO> bookingDTOS = patient.getVaccineBookings().stream()
                .map(this::toSmallBookingDTO)
                .collect(Collectors.toList());
        patientDTO.setVaccineBookings(bookingDTOS);
        return patientDTO;
    }

    @Override
    public PatientDTO toSmallPatientDTO(Patient patient){
        if(patient == null) return null;
        PatientDTO patientDTO = new PatientDTO();
        patientDTO.setId(patient.getId());
        patientDTO.setPnr(patient.getPnr());
        patientDTO.setFirstName(patient.getFirstName());
        patientDTO.setLastName(patient.getLastName());
        patientDTO.setBirthDate(patient.getBirthDate());
        return patientDTO;
    }

    @Override
    public ContactInfoDTO toContactInfoDTO(ContactInfo contactInfo){
        if(contactInfo == null) return null;
        ContactInfoDTO contactInfoDTO = new ContactInfoDTO();
        contactInfoDTO.setEmail(contactInfo.getEmail());
        contactInfoDTO.setId(contactInfo.getId());
        contactInfoDTO.setPhone(contactInfo.getPhone());
        return contactInfoDTO;
    }

}
