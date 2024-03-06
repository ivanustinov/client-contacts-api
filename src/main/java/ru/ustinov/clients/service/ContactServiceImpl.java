package ru.ustinov.clients.service;

import io.swagger.model.ContactTo;
import io.swagger.model.ContactType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ustinov.clients.entities.Client;
import ru.ustinov.clients.entities.Contact;
import ru.ustinov.clients.exception.ClientAppException;
import ru.ustinov.clients.repositories.ClientRepository;
import ru.ustinov.clients.repositories.ContactRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<ContactTo> findContactsByClientAndType(UUID clientId, ContactType type) {
        List<Contact> contacts;
        if (type == null) {
            contacts = contactRepository.findByClient(clientId);
        } else {
            contacts = contactRepository.findByClientAndType(clientId, type);
        }
        return contacts.stream().map(this::createTo).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ContactTo saveContact(UUID clientId, ContactTo contactTo) {
        contactRepository.findContactByValue(contactTo.getValue())
            .ifPresent(
                contact -> {
                    String message = String
                        .format("Контакт типа %s со значением %s уже существует", contact.getType(), contact.getValue());
                    throw new ClientAppException(message);
                });
        Contact entity = createEntity(clientId, contactTo);
        Contact save = contactRepository.save(entity);
        return createTo(save);
    }

    private ContactTo createTo(Contact contact) {
        ContactTo contactTo = new ContactTo();
        contactTo.setValue(contact.getValue());
        contactTo.setType(contact.getType());
        contactTo.setId(contact.getId());
        return contactTo;
    }

    private Contact createEntity(UUID clientId, ContactTo contactTo) {
        Client client = clientRepository.findById(clientId)
            .orElseThrow(() -> new NoSuchElementException("Не найден клиент с id: " + clientId));
        Contact contact = new Contact();
        contact.setType(contactTo.getType());
        contact.setValue(contactTo.getValue());
        contact.setClient(client);
        return contact;
    }
}
