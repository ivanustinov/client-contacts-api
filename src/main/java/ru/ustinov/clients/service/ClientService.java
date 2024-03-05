package ru.ustinov.clients.service;

import io.swagger.model.ClientTo;
import io.swagger.model.ContactTo;
import io.swagger.model.ContactType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ustinov.clients.entities.Client;
import ru.ustinov.clients.entities.Contact;
import ru.ustinov.clients.repositories.ClientRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public ClientTo findById(UUID uuid) {
        Client client = clientRepository.getWithContacts(uuid).orElseThrow(NoSuchElementException::new);
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
        List<Client> clients = clientRepository.findAll();
        return clients.stream().map(this::createTo).collect(Collectors.toList());
    }

    public ClientTo saveClient(ClientTo clientTo) {
        Client client = new Client();
        client.setFullName(clientTo.getName());
        Client save = clientRepository.save(client);
        return createTo(save);
    }

    public List<ContactTo> mapContacts(List<Contact> contacts) {
        List<ContactTo> contactTos = new ArrayList<>();
        if (contacts != null) {
            contactTos = contacts.stream().map(contact -> {
                ContactTo contactTo = new ContactTo();
                contactTo.setId(contact.getId());
                contactTo.setType(contact.getType());
                contactTo.setValue(contact.getValue());
                return contactTo;
            }).collect(Collectors.toList());
        }
        return contactTos;
    }

    public ClientTo createTo(Client client) {
        ClientTo clientTo = new ClientTo();
        clientTo.setClientId(client.getId());
        clientTo.setName(client.getFullName());
        return clientTo;
    }

}
