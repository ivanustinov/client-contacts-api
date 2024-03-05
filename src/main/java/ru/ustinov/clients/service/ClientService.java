package ru.ustinov.clients.service;

import io.swagger.model.ClientTo;
import io.swagger.model.ContactTo;
import io.swagger.model.ContactType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ustinov.clients.entities.Client;
import ru.ustinov.clients.entities.Contact;
import ru.ustinov.clients.repositories.ClientRepositry;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepositry clientRepositry;

    public ClientTo findById(UUID uuid) {
        Client client = clientRepositry.getWithContacts(uuid).orElseThrow(NoSuchElementException::new);
        ClientTo clientTo = createTo(client);
        List<Contact> contacts = client.getContacts();
        if (!contacts.isEmpty()) {
            Map<ContactType, List<Contact>> groupedContacts = contacts.stream()
                .collect(Collectors.groupingBy(Contact::getType, Collectors.toList()));
            clientTo.setEmails(mapContacts(groupedContacts.get(ContactType.EMAIL)));
            clientTo.setPhones(mapContacts(groupedContacts.get(ContactType.PHONE)));
        }
        return clientTo;
    }

    public List<ClientTo> getClients() {
        List<Client> clients = clientRepositry.findAll();
        return clients.stream().map(this::createTo).collect(Collectors.toList());
    }

    public ClientTo saveClient(ClientTo clientTo) {
        Client client = new Client();
        client.setFullName(clientTo.getName());
        Client save = clientRepositry.save(client);
        return createTo(save);
    }

    public List<ContactTo> mapContacts(List<Contact> contacts) {
        return contacts.stream().map(contact -> {
            ContactTo contactTo = new ContactTo();
            contactTo.setId(contact.getId());
            contactTo.setType(contact.getType());
            contactTo.setValue(contact.getValue());
            return contactTo;
        }).collect(Collectors.toList());
    }

    public ClientTo createTo(Client client) {
        ClientTo clientTo = new ClientTo();
        clientTo.setClientId(client.getId());
        clientTo.setName(client.getFullName());
        return clientTo;
    }

}
