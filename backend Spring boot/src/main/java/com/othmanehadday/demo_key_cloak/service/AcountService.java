package com.othmanehadday.demo_key_cloak.service;

import com.othmanehadday.demo_key_cloak.entities.AppRole;
import com.othmanehadday.demo_key_cloak.entities.AppUser;

public interface AcountService {
    public AppUser saveUser(AppUser user);
    public AppRole saveRole(AppRole role);
    public void addRoleToUser(String userName,String roleName);
    public AppUser findUserByUsername(String username);
}
