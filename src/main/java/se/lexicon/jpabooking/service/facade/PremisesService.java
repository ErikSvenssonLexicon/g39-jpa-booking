package se.lexicon.jpabooking.service.facade;

import se.lexicon.jpabooking.model.dto.form.BookingForm;
import se.lexicon.jpabooking.model.dto.form.PremisesForm;
import se.lexicon.jpabooking.model.dto.view.PremisesDTO;
import se.lexicon.jpabooking.service.entity.GenericEntityService;

public interface PremisesService extends GenericEntityService<PremisesDTO, PremisesForm> {
    PremisesDTO addNewBooking(String premisesId, BookingForm bookingForm);
    PremisesDTO removeBooking(String premisesId, String bookingId);
    PremisesDTO reallocateBooking(String premisesId, String bookingId);
}
