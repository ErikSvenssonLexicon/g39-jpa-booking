package se.lexicon.jpabooking.service.facade;

import se.lexicon.jpabooking.model.dto.view.*;
import se.lexicon.jpabooking.model.entity.*;

public interface DTOService {
    PremisesDTO toFullPremisesDTO(Premises premises);

    PremisesDTO toSmallPremisesDTO(Premises premises);

    BookingDTO toFullBookingDTO(Booking booking);

    BookingDTO toSmallBookingDTO(Booking booking);

    AddressDTO toAddressDTO(Address address);

    PatientDTO toFullPatientDTO(Patient patient);

    PatientDTO toSmallPatientDTO(Patient patient);

    ContactInfoDTO toContactInfoDTO(ContactInfo contactInfo);

    AppUserDTO toFullAppUserDTO(AppUser appUser);

    AppUserDTO toSmallAppUserDTO(AppUser appUser);
}
