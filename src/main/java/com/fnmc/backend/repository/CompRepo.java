package com.fnmc.backend.repository;

import com.fnmc.backend.entity.Comps;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CompRepo extends MongoRepository< Comps,String> {
    @Query("{player:'?0', named:'?1'}")
   Comps getStructByName(String player, String name);

}
