package se.lexicon.jpabooking.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.lexicon.jpabooking.database.ContactInfoDAO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final ContactInfoDAO contactInfoDAO;

    @Autowired
    public UniqueEmailValidator(ContactInfoDAO contactInfoDAO) {
        this.contactInfoDAO = contactInfoDAO;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null){
            return true;
        }
        return !contactInfoDAO.findByEmail(value.trim()).isPresent();
    }
}
