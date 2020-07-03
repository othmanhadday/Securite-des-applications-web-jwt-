package com.othmanehadday.demo_key_cloak.web;

import com.othmanehadday.demo_key_cloak.dao.TaskRepository;
import com.othmanehadday.demo_key_cloak.entities.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestRestController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/tasks")
    public List<Task> taskLists(){
        return taskRepository.findAll();
    }

    @PostMapping("/tasks")
    public Task save(@RequestBody Task task){
        return taskRepository.save(task);
    }
}
