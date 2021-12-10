package se.lexicon.jpabooking.service.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpabooking.database.BookingDAO;
import se.lexicon.jpabooking.database.PremisesDAO;
import se.lexicon.jpabooking.exception.AppResourceNotFoundException;
import se.lexicon.jpabooking.model.dto.form.PremisesForm;
import se.lexicon.jpabooking.model.entity.Premises;

import java.util.List;

@Service
@Transactional
public class PremisesEntityServiceImpl implements PremisesEntityService{

    private final PremisesDAO premisesDAO;
    private final BookingDAO bookingDAO;
    private final AddressEntityService addressEntityService;

    @Autowired
    public PremisesEntityServiceImpl(PremisesDAO premisesDAO, BookingDAO bookingDAO, AddressEntityService addressEntityService) {
        this.premisesDAO = premisesDAO;
        this.bookingDAO = bookingDAO;
        this.addressEntityService = addressEntityService;
    }

    @Override
    public Premises create(PremisesForm premisesForm) {
        if(premisesForm == null) throw new IllegalArgumentException("PremisesForm premisesForm was null");
        if(premisesForm.getId() != null) throw new IllegalArgumentException("Id was not null");
        Premises premises = new Premises();
        premises.setName(premisesForm.getName().trim());
        premises.setAddress(addressEntityService.persistOrChange(premisesForm.getAddress()));
        //Todo: Once BookingEntityService is finished fix setting initial bookings if available

        return premisesDAO.save(premises);
    }

    @Override
    public Premises findById(String id) {
       return premisesDAO.findById(id)
               .orElseThrow(() -> new AppResourceNotFoundException("Premises with id " + id + " could not be found"));
    }

    @Override
    public List<Premises> findAll() {
        return premisesDAO.findAll();
    }

    @Override
    public Premises update(String id, PremisesForm premisesForm) {
        Premises premises = findById(id);
        if(!premises.getId().equals(premisesForm.getId())){
            throw new IllegalArgumentException("Parameter id didn't match " + PremisesForm.class.getName() + ".id");
        }
        premises.setName(premisesForm.getName().trim());
        if(premisesForm.getAddress() != null){
            premises.setAddress(addressEntityService.persistOrChange(premisesForm.getAddress()));
        }
        if(premisesForm.getBookings() != null || !premisesForm.getBookings().isEmpty()){
            //Todo: Finish this after creating BookingEntityService
        }

        premises = premisesDAO.save(premises);
        return premises;
    }

    @Override
    public void delete(String id) {
        Premises premises = findById(id);
        premises.setBookings(null);
        premises.setAddress(null);
        if (premises.getAddress() != null && premisesDAO.countUsagesByAddressId(premises.getAddress().getId()) > 1){
            addressEntityService.delete(premises.getAddress().getId());
        }
    }

    @Override
    public Premises addBooking(String premisesId, String bookingId) {
        //Todo: Finish this after creating BookingEntityService
        return null;
    }

    @Override
    public Premises removeBooking(String premisesId, String bookingId) {
        //Todo: Finish this after creating BookingEntityService
        return null;
    }
}
