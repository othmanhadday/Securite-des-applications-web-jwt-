package com.othmanehadday.demo_key_cloak.dao;

import com.othmanehadday.demo_key_cloak.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole,Long> {
    public  AppRole findByRole(String role);
}
