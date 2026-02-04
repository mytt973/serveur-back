package com.fnmc.backend.entity;


import com.fnmc.backend.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.aot.generate.Generated;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("user_mc")

    public class UserMc implements UserDetails{

        @Id
        private String id;

        private String name;

        private String hash;
        private String asso;


        private List<RoleEnum> roles=new ArrayList<>();
        private List<Map<String,String>> structIn;


    public UserMc(String name,  String hash,  List<RoleEnum> role) {


        this.name = name;
        this.structIn = new ArrayList<>();
        this.hash=hash;
        this.roles=role;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (RoleEnum roleEnum : roles){
            GrantedAuthority authority = new SimpleGrantedAuthority(roleEnum.toString());
            grantedAuthorities.add(authority);
        }
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return hash;
    }

    @Override
    public String getUsername() {
        return name;
    }
}
