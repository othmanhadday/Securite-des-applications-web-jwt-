package com.othmanehadday.demo_key_cloak;

import com.othmanehadday.demo_key_cloak.dao.TaskRepository;
import com.othmanehadday.demo_key_cloak.entities.AppRole;
import com.othmanehadday.demo_key_cloak.entities.AppUser;
import com.othmanehadday.demo_key_cloak.entities.Task;
import com.othmanehadday.demo_key_cloak.service.AcountService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.stream.Stream;



@SpringBootApplication
public class DemoKeyCloakApplication implements CommandLineRunner{
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private AcountService acountService;

    public static void main(String[] args) {
        SpringApplication.run(DemoKeyCloakApplication.class, args);


    }


    @Override
    public void run(String... args) throws Exception {
        acountService.saveUser(new AppUser(null,"admin","1234",null));
        acountService.saveUser(new AppUser(null,"user","1234",null));
        acountService.saveRole(new AppRole(null,"ADMIN"));
        acountService.saveRole(new AppRole(null,"USER"));
        acountService.addRoleToUser("admin","ADMIN");
        acountService.addRoleToUser("admin","USER");
        acountService.addRoleToUser("user","USER");


        Stream.of("T1","T2","T3").forEach(t->{
            taskRepository.save(new Task(null,t));
        });
        taskRepository.findAll().forEach(task ->{
            System.out.println(task.getTaskName());
        });
    }
}
