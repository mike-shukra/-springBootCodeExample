package ru.yogago.goyoga.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yogago.goyoga.data.ClientDTO;
import ru.yogago.goyoga.data.ClientEntity;
import ru.yogago.goyoga.data.ParametersDTO;
import ru.yogago.goyoga.data.UserEntity;
import ru.yogago.goyoga.repo.ClientRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @PostConstruct
    public void init() {

        if (clientRepository.count() == 0) {
            ClientEntity adminEntity = new ClientEntity();
            adminEntity.setUsername("admin");
            adminEntity.setDangerKnee(false);
            adminEntity.setDangerLoins(false);
            adminEntity.setDangerKnee(false);
            adminEntity.setDangerNeck(false);

            clientRepository.save(adminEntity);

            ClientEntity userEntity = new ClientEntity();
            userEntity.setUsername("user1");
            adminEntity.setDangerKnee(true);
            adminEntity.setDangerLoins(true);
            adminEntity.setDangerKnee(true);
            adminEntity.setDangerNeck(true);


            clientRepository.save(userEntity);
        }
    }


    @Override
    public List<ClientDTO> getClientList() {
        List<ClientEntity> clientEntityList = clientRepository.findAll();
        List<ClientDTO> clientDTOList = new ArrayList<>();
        clientEntityList.forEach(clientEntity -> {
            clientDTOList.add(new ClientDTO(clientEntity));
        });
        return clientDTOList;
    }

    @Override
    public ClientEntity getClientByUsername(String username) {
        return this.clientRepository.findByUsername(username);
    }

    @Override
    public ClientEntity updateClient(ClientEntity client) {
        return this.clientRepository.save(client);
    }

    @Override
    public ClientEntity createClient(ClientEntity client) {
        return this.clientRepository.save(client);
    }

    @Override
    public int updateClientData(String username, String displayName) {
        return this.clientRepository.updateClientData(username, displayName);
    }

}
