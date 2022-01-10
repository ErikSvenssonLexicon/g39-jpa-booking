package se.lexicon.jpabooking.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpabooking.model.dto.form.BookingForm;
import se.lexicon.jpabooking.model.dto.form.PremisesForm;
import se.lexicon.jpabooking.model.dto.view.PremisesDTO;
import se.lexicon.jpabooking.model.entity.Premises;
import se.lexicon.jpabooking.service.entity.PremisesEntityService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PremisesServiceImpl implements PremisesService{

    private final PremisesEntityService premisesEntityService;
    private final DTOService dtoConverter;

    @Autowired
    public PremisesServiceImpl(PremisesEntityService premisesEntityService, DTOService dtoConverter) {
        this.premisesEntityService = premisesEntityService;
        this.dtoConverter = dtoConverter;
    }

    @Override
    public PremisesDTO create(PremisesForm form) {
        Premises premises = premisesEntityService.create(form);
        return dtoConverter.toFullPremisesDTO(premises);
    }

    @Override
    public PremisesDTO findById(String id) {
        Premises premises = premisesEntityService.findById(id);
        return dtoConverter.toFullPremisesDTO(premises);
    }

    @Override
    public List<PremisesDTO> findAll() {
        return premisesEntityService.findAll().stream()
                .map(dtoConverter::toSmallPremisesDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PremisesDTO update(String id, PremisesForm form) {
        Premises premises = premisesEntityService.update(id, form);
        return dtoConverter.toFullPremisesDTO(premises);
    }

    @Override
    public void delete(String id) {
        premisesEntityService.delete(id);
    }

    @Override
    public PremisesDTO addNewBooking(String premisesId, BookingForm bookingForm) {
        Premises premises = premisesEntityService.addNewBooking(premisesId, bookingForm);
        return dtoConverter.toFullPremisesDTO(premises);
    }

    @Override
    public PremisesDTO removeBooking(String premisesId, String bookingId) {
        Premises premises = premisesEntityService.removeBooking(premisesId, bookingId);
        return dtoConverter.toFullPremisesDTO(premises);
    }

    @Override
    public PremisesDTO reallocateBooking(String premisesId, String bookingId) {
        throw new RuntimeException("Not yet implemented");
    }
}
