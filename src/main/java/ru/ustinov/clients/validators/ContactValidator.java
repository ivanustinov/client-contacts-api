package ru.ustinov.clients.validators;

import io.swagger.model.ContactTo;
import io.swagger.model.ContactType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class ContactValidator implements Validator {

    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private PhoneValidator phoneValidator;

    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return ContactTo.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        ContactTo contact = (ContactTo) target;
        if (contact.getType() == ContactType.EMAIL) {
            ValidationUtils.invokeValidator(emailValidator, contact, errors);
        } else if (contact.getType() == ContactType.PHONE) {
            ValidationUtils.invokeValidator(phoneValidator, contact, errors);
        }
    }
}

