package ru.ustinov.clients.service;

import io.swagger.model.ClientTo;

import java.util.List;
import java.util.UUID;

public interface ClientService {

    ClientTo findById(UUID uuid);

    List<ClientTo> getClients();

    ClientTo saveClient(ClientTo clientTo);
}
