package ru.ustinov.clients.service;

import io.swagger.model.ContactTo;
import io.swagger.model.ContactType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ustinov.clients.entities.Contact;
import ru.ustinov.clients.repositories.ContactRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public List<ContactTo> findContactsByClientAndType(UUID clientId, ContactType type) {
        List<Contact> contacts;
        if (type == null) {
            contacts = contactRepository.findByClient(clientId);
        } else {
            contacts = contactRepository.findByClientAndType(clientId, type);
        }
        return contacts.stream().map(this::createTo).collect(Collectors.toList());
    }

    public ContactTo createTo(Contact contact) {
        ContactTo contactTo = new ContactTo();
        contactTo.setValue(contact.getValue());
        contactTo.setType(contact.getType());
        contactTo.setId(contact.getId());
        return contactTo;
    }
}
