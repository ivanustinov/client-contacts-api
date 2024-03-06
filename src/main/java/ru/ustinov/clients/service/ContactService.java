package ru.ustinov.clients.service;

import io.swagger.model.ContactTo;
import io.swagger.model.ContactType;

import java.util.List;
import java.util.UUID;

public interface ContactService {

    List<ContactTo> findContactsByClientAndType(UUID clientId, ContactType type);

    ContactTo saveContact(UUID clientId, ContactTo contactTo);
}
