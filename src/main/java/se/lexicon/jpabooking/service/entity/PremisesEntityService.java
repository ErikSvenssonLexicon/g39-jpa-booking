package se.lexicon.jpabooking.service.entity;

import se.lexicon.jpabooking.model.dto.form.BookingForm;
import se.lexicon.jpabooking.model.dto.form.PremisesForm;
import se.lexicon.jpabooking.model.entity.Premises;

public interface PremisesEntityService extends GenericEntityService<Premises, PremisesForm>{
    Premises addNewBooking(String premisesId, BookingForm bookingForm);
    Premises removeBooking(String premisesId, String bookingId);
    Premises reallocateBooking(String premisesId, String bookingId);
}
