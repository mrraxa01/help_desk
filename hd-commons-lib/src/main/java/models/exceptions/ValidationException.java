package models.exceptions;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import java.util.List;

@SuperBuilder

public class ValidationException extends StandardError{
@Getter
 List<FieldError> errors;

@Getter
 private static class FieldError {
    private String fieldName;
    private String message;


    public FieldError(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }
}
    public void addError(final String fieldName, final String message){
        this.errors.add(new FieldError(fieldName, message));
    }





}
