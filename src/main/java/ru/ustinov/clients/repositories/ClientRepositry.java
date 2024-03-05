package ru.ustinov.clients.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.ustinov.clients.entities.Client;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepositry extends JpaRepository<Client, UUID> {

    @EntityGraph(attributePaths = {"contacts"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select c from Client c where c.id=:uuid")
    Optional<Client> getWithContacts(UUID uuid);
}
