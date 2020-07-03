package com.othmanehadday.demo_key_cloak.web;

import com.othmanehadday.demo_key_cloak.entities.AppUser;
import com.othmanehadday.demo_key_cloak.service.AcountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountRestController {
    @Autowired
    private AcountService accouService;

    @PostMapping("/register")
    public AppUser register(@RequestBody RegisterForm registerForm){
        if(!registerForm.getPassword().equals(registerForm.getRePassword())){
            throw new RuntimeException("You must confirm your password");
        }

        AppUser user = accouService.findUserByUsername(registerForm.getUsername());
        if(user!=null) throw new RuntimeException("This user already exists");

        AppUser appUser = new AppUser();
        appUser.setUsername(registerForm.getUsername());
        appUser.setPassword(registerForm.getPassword());
        accouService.saveUser(appUser);
        accouService.addRoleToUser(registerForm.getUsername(),"USER");

        return appUser;
    }
}
