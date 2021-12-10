package se.lexicon.jpabooking.service.entity;

import se.lexicon.jpabooking.model.dto.form.PremisesForm;
import se.lexicon.jpabooking.model.entity.Premises;

public interface PremisesEntityService extends GenericEntityService<Premises, PremisesForm>{

    Premises addBooking(String premisesId, String bookingId);
    Premises removeBooking(String premisesId, String bookingId);

}
