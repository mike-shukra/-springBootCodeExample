package ru.yogago.goyoga.service;


import ru.yogago.goyoga.data.ClientDTO;
import ru.yogago.goyoga.data.ClientEntity;

import java.util.List;

public interface ClientService {

    List<ClientDTO> getClientList();
    ClientEntity getClientByUsername(String username);
    ClientEntity updateClient(ClientEntity client);
    ClientEntity createClient(ClientEntity client);
    int updateClientData(String username, String displayName);

}
