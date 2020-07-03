package com.othmanehadday.demo_key_cloak.service;

import com.othmanehadday.demo_key_cloak.bean.PasswordEncoder;
import com.othmanehadday.demo_key_cloak.dao.AppRoleRepository;
import com.othmanehadday.demo_key_cloak.dao.AppUserRepository;
import com.othmanehadday.demo_key_cloak.entities.AppRole;
import com.othmanehadday.demo_key_cloak.entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class AccountServiceImpl implements AcountService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AppUserRepository appUserRepository;
    @Autowired
    private AppRoleRepository appRoleRepository;

    @Override
    public AppUser saveUser(AppUser user) {
        String hashPw = passwordEncoder.getBCPE().encode(user.getPassword());
        user.setPassword(hashPw);
        return appUserRepository.save(user);
    }

    @Override
    public AppRole saveRole(AppRole role) {
        return appRoleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String userName, String roleName) {
        AppUser appUser = appUserRepository.findByUsername(userName);
        AppRole appRole = appRoleRepository.findByRole(roleName);
        appUser.getRoles().add(appRole);
    }

    @Override
    public AppUser findUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }
}
