package ru.ustinov.clients.validators;

import io.swagger.model.ClientTo;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ClientValidator implements Validator {

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return ClientTo.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        ValidationUtils
            .rejectIfEmptyOrWhitespace(errors, "name", "client.name.empty", "Заполните поле");
    }
}