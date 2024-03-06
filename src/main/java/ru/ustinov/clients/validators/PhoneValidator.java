package ru.ustinov.clients.validators;

import io.swagger.model.ContactTo;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PhoneValidator implements Validator {

    private static final String PHONE_PATTERN = "^\\+\\d{11}$";

    private final Pattern pattern;

    public PhoneValidator() {
        pattern = Pattern.compile(PHONE_PATTERN);
    }

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return ContactTo.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        ContactTo phone = (ContactTo) target;
        Matcher matcher = pattern.matcher(phone.getValue());
        if (!matcher.matches()) {
            errors.rejectValue("value", "invalid.phone", "Неправильный номер телефона");
        }
    }
}
