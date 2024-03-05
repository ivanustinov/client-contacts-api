package ru.ustinov.clients.web;

import io.swagger.api.ContactsApi;
import io.swagger.model.ContactTo;
import io.swagger.model.ContactType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RestController;
import ru.ustinov.clients.service.ContactService;

import java.util.List;
import java.util.UUID;

@RestController
public class ContactController implements ContactsApi {

    @Autowired
    private ContactService contactService;

    @Override
    public ResponseEntity<ContactTo> createContact(ContactType type, UUID clientId, ContactTo body) {
        return null;
    }

    @Override
    public ResponseEntity<List<ContactTo>> getContactsByClient(UUID clientId, ContactType type) {
        List<ContactTo> contacts = contactService.findContactsByClientAndType(clientId, type);
        return ResponseEntity.ok(contacts);
    }
}
