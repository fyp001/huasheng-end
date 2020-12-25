
package com.myproject.api.springboot_mybatis.serviceImpl;

import com.myproject.api.springboot_mybatis.dao.ClientMapper;
import com.myproject.api.springboot_mybatis.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    @Autowired
    ClientMapper clientMapper;
    public int InsertClient(Client client){
        return clientMapper.insertClient(client);
    }

    public int DeleteClient(String id){
        return clientMapper.deleteClient(id);
    }

    public int UpdateClient(String id,String newNameValue,String newTypeValue,
                            String newAddressValue,String newRepresentativeValue,String newBusinessValue,String newRegisterCapitalValue,
                            String newPersonName,String newPersonPhone){
        return clientMapper.updateClient(id,newNameValue,newTypeValue,newAddressValue,
                newRepresentativeValue,newBusinessValue,newRegisterCapitalValue,
                newPersonName,newPersonPhone);
    }
    public Client readClient(int id){
        return clientMapper.readClient(id);
    }

    public List<Client> readAllClient(){
        return clientMapper.readAllClient();
    }

}