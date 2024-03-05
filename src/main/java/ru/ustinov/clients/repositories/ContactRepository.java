package ru.ustinov.clients.repositories;

import io.swagger.model.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.ustinov.clients.entities.Client;
import ru.ustinov.clients.entities.Contact;

import java.util.List;
import java.util.UUID;

@Repository
public interface ContactRepository extends JpaRepository<Contact, UUID> {

    @Query("SELECT c FROM Contact c WHERE c.client.id=:clientId and c.type=:type")
    List<Contact> findByClientAndType(UUID clientId, ContactType type);

    @Query("SELECT c FROM Contact c WHERE c.client.id=:clientId")
    List<Contact> findByClient(UUID clientId);
}
