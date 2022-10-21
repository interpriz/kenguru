package com.kenguru.demowebapp.controllers;


import com.kenguru.demowebapp.entities.Role;
import com.kenguru.demowebapp.entities.Users;
import com.kenguru.demowebapp.repositories.UsersRepository;
import com.kenguru.demowebapp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    private UserService service;

    public RegistrationController(UserService service) {
        this.service = service;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(
            Users user,
            Model model
    ) {

        if(!service.registration(user))
        {
            model.addAttribute("exist", true);
            return "registration";
        }

        return "redirect:/login";
    }
}

