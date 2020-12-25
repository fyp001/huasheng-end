package com.myproject.api.springboot_mybatis.dao;

import com.myproject.api.springboot_mybatis.entity.Client;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface ClientMapper{

    int insertClient(Client client);
    int deleteClient(String id);
    int updateClient(String id,String newNameValue,String newTypeValue,
                     String newAddressValue,String newRepresentativeValue,String newBusinessValue,String newRegisterCapitalValue,
                     String newPersonName,String newPersonPhone);
    Client readClient(int id);
    List<Client> readAllClient();
}