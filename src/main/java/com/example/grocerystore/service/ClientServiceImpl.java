package com.example.grocerystore.service;

import com.example.grocerystore.entity.CategoryEntity;
import com.example.grocerystore.entity.ClientEntity;
import com.example.grocerystore.mapper.MapperHelper;
import com.example.grocerystore.model.Client;
import com.example.grocerystore.repository.ClientRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClientServiceImpl implements ClientService{
    private final ClientRepo clientRepo;
    private final MapperHelper mapperHelper;
    @Autowired
    public ClientServiceImpl(ClientRepo clientRepo, MapperHelper mapperHelper) {
        this.clientRepo = clientRepo;
        this.mapperHelper = mapperHelper;
    }

    @Override
    public Client getClientById(Long id) {
//        Optional<ClientEntity> foundUser = clientRepo.findById(id);
        Client client = new Client();
        List<ClientEntity> clientEntityList=clientRepo.findAll();
        for(ClientEntity clientEntity: clientEntityList){
            if(clientEntity.getClientId().equals(id)){
                client=mapperHelper.convertClientEntityToClient(clientEntity);
            }
        }
        return client;
    }
}
