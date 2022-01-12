package se.lexicon.jpabooking.model.dto.view;

import java.util.List;
import java.util.Map;

public class ValidationErrorResponse extends ExceptionResponseDTO{

    private Map<String, List<String>> errors;

    public Map<String, List<String>> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, List<String>> errors) {
        this.errors = errors;
    }
}
