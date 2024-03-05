package ru.ustinov.clients.web;

import io.swagger.api.ClientsApi;
import io.swagger.model.ClientTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.ustinov.clients.service.ClientService;

import java.util.List;
import java.util.UUID;

@RestController
public class ClientController implements ClientsApi {

    @Autowired
    private ClientService clientService;

//    @InitBinder
//    protected void initBinder(WebDataBinder binder) {
//        binder.addValidators(uniqueRestaurantNameValidator);
//    }

    @Override
    public ResponseEntity<ClientTo> createClient(ClientTo to) {
        ClientTo clientTo = clientService.saveClient(to);
        return ResponseEntity.ok(clientTo);
    }

    @Override
    public ResponseEntity<ClientTo> getClientById(UUID id) {
        ClientTo clientTo = clientService.findById(id);
        return ResponseEntity.ok(clientTo);
    }

    @Override
    public ResponseEntity<List<ClientTo>> getClients() {
        List<ClientTo> clients = clientService.getClients();
        return ResponseEntity.ok(clients);
    }
}
