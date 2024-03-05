package ru.ustinov.clients.web;

import io.swagger.api.ContactsApi;
import io.swagger.model.ContactTo;
import io.swagger.model.ContactType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestController;
import ru.ustinov.clients.service.ContactService;
import ru.ustinov.clients.validators.ContactValidator;

import java.util.List;
import java.util.UUID;

@RestController
public class ContactController implements ContactsApi {

    @Autowired
    private ContactService contactService;

    @Autowired
    private ContactValidator contactValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(contactValidator);
    }

    @Override
    public ResponseEntity<ContactTo> createContact(UUID clientId, ContactTo body) {
        ContactTo contactTo = contactService.saveContact(clientId, body);
        return ResponseEntity.ok(contactTo);
    }

    @Override
    public ResponseEntity<List<ContactTo>> getContactsByClient(UUID clientId, ContactType type) {
        List<ContactTo> contacts = contactService.findContactsByClientAndType(clientId, type);
        return ResponseEntity.ok(contacts);
    }
}
