package se.lexicon.jpabooking.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.lexicon.jpabooking.database.PatientDAO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class UniquePnrValidator implements ConstraintValidator<UniquePnr, String> {

    private final PatientDAO patientDAO;

    @Autowired
    public UniquePnrValidator(PatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null){
            return true;
        }
        return !patientDAO.findByPnr(value).isPresent();
    }
}
