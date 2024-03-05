package ru.ustinov.clients.validators;


import io.swagger.model.ContactTo;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EmailValidator implements Validator {

    private static final String EMAIL_PATTERN =
        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private final Pattern pattern;

    public EmailValidator() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return ContactTo.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ContactTo email = (ContactTo) target;
        Matcher matcher = pattern.matcher(email.getValue());
        if (!matcher.matches()) {
            errors.rejectValue("value", "invalid.email", "Неправильная почта");
        }
    }
}
