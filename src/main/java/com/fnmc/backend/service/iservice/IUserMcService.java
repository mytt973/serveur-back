package com.fnmc.backend.service.iservice;

import com.fnmc.backend.entity.UserMc;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IUserMcService {
    public UserMc createUser(UserMc user);

    Map<String, Object> login(String nom, String password, String id);
    String loginServer(String nom, String password);

    public Optional<UserMc> getUser(String id);

    void addStr(String name, String str, String id);

    public Optional<List<String>> getAllUser();

}
