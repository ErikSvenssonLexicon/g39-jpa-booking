package se.lexicon.jpabooking.service.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.jpabooking.database.ContactInfoDAO;
import se.lexicon.jpabooking.exception.AppResourceNotFoundException;
import se.lexicon.jpabooking.model.dto.form.ContactInfoForm;
import se.lexicon.jpabooking.model.entity.ContactInfo;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ContactInfoEntityServiceImpl implements ContactInfoEntityService{

    private final ContactInfoDAO contactInfoDAO;

    @Autowired
    public ContactInfoEntityServiceImpl(ContactInfoDAO contactInfoDAO) {
        this.contactInfoDAO = contactInfoDAO;
    }

    @Override
    public ContactInfo create(ContactInfoForm contactInfoForm) {
        if(contactInfoForm == null){
            throw new IllegalArgumentException("ContactInfoForm was null");
        }
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setEmail(contactInfoForm.getEmail().trim());
        if(contactInfoForm.getPhone() != null){
            contactInfo.setPhone(contactInfoForm.getPhone());
        }

        return contactInfoDAO.save(contactInfo);
    }

    @Override
    public ContactInfo findById(String id) {
        return contactInfoDAO.findById(id)
                .orElseThrow(() -> new AppResourceNotFoundException("Could not find ContactInfo with id " + id));
    }

    @Override
    public List<ContactInfo> findAll() {
        return contactInfoDAO.findAll();
    }

    @Override
    public ContactInfo update(String id, ContactInfoForm contactInfoForm) {
        ContactInfo contactInfo = findById(id);

        Optional<ContactInfo> optional = contactInfoDAO.findByEmail(contactInfoForm.getEmail().trim());
        if(optional.isPresent() && !optional.get().getId().equals(id)){
            throw new IllegalArgumentException("Provided email is already used");
        }

        contactInfo.setEmail(contactInfoForm.getEmail().trim());
        contactInfo.setPhone(contactInfoForm.getPhone());

        return contactInfoDAO.save(contactInfo);
    }

    @Override
    public void delete(String id) {
        contactInfoDAO.deleteById(id);
    }
}
