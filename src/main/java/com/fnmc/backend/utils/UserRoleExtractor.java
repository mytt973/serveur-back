package com.fnmc.backend.utils;
import com.fnmc.backend.enums.RoleEnum;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public class UserRoleExtractor {
    static public boolean isServerUser(Authentication authentication){
        boolean isProf = false;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities){
            if (authority.getAuthority().equals(RoleEnum.SERVER.toString()))
                isProf = true;
        }
        return isProf;
    }

    static public boolean isPlayerUser(Authentication authentication){
        boolean isStudent = false;
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities){
            if (authority.getAuthority().equals(RoleEnum.PLAYER.toString()))
                isStudent = true;
        }
        return isStudent;
    }
}
