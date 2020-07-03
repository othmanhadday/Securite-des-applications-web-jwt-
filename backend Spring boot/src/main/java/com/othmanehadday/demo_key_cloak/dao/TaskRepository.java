package com.othmanehadday.demo_key_cloak.dao;

import com.othmanehadday.demo_key_cloak.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
public interface TaskRepository extends JpaRepository<Task,Long> {
}
