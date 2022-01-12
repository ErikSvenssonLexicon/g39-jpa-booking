package se.lexicon.jpabooking.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpabooking.model.dto.form.ContactInfoForm;
import se.lexicon.jpabooking.model.dto.view.ContactInfoDTO;
import se.lexicon.jpabooking.service.entity.ContactInfoEntityService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContactInfoServiceImpl implements ContactInfoService{

    private final ContactInfoEntityService contactInfoEntityService;
    private final DTOService dtoService;

    @Autowired
    public ContactInfoServiceImpl(ContactInfoEntityService contactInfoEntityService, DTOService dtoService) {
        this.contactInfoEntityService = contactInfoEntityService;
        this.dtoService = dtoService;
    }

    @Override
    public ContactInfoDTO create(ContactInfoForm contactInfoForm) {
        return dtoService.toContactInfoDTO(contactInfoEntityService.create(contactInfoForm));
    }

    @Override
    public ContactInfoDTO findById(String id) {
        return dtoService.toContactInfoDTO(contactInfoEntityService.findById(id));
    }

    @Override
    public List<ContactInfoDTO> findAll() {
        return contactInfoEntityService.findAll().stream()
                .map(dtoService::toContactInfoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ContactInfoDTO update(String id, ContactInfoForm contactInfoForm) {
        return dtoService.toContactInfoDTO(contactInfoEntityService.update(id, contactInfoForm));
    }

    @Override
    public void delete(String id) {
        contactInfoEntityService.delete(id);
    }
}
