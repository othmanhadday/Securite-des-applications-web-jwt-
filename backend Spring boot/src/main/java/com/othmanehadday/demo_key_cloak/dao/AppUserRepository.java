package com.othmanehadday.demo_key_cloak.dao;

import com.othmanehadday.demo_key_cloak.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    public AppUser findByUsername(String username);
}
