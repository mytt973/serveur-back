package com.fnmc.backend.repository;

import com.fnmc.backend.entity.UserMc;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
//import java.util.Optional;

public interface UserMcRepository extends MongoRepository<UserMc, String> {

        @Query("{name:'?0'}")
        UserMc findItemByName(String name);

        @Query(value="{}", fields="{'name' : 1}")
        List<UserMc> findAllUserNames();







}


