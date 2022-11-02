package com.kenguru.demowebapp.controllers;


import com.kenguru.demowebapp.entities.Users;
import com.kenguru.demowebapp.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService service) {
        this.userService = service;
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(
            @RequestParam("password2") String passwordConfirm,
            @Valid Users user,
            BindingResult bindingResult,
            Model model
    ) {
        boolean isConfirmEmpty = passwordConfirm.isEmpty();

        if (isConfirmEmpty) {
            model.addAttribute("password2Error", "Поддтверждение пароля не может быть пустым");
        }

        if (user.getPassword() != null && !user.getPassword().equals(passwordConfirm)) {
            model.addAttribute("passwordError", "Пароли различаются!");
        }

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
        }

        if(isConfirmEmpty ||
                model.containsAttribute("passwordError") ||
                bindingResult.hasErrors()
        ){
            model.addAttribute("user", user);

            return "registration";
        }else{
            if(!userService.registration(user))
            {
                model.addAttribute("usernameError", "Такой пользователь уже есть!");
                return "registration";
            }
        }




        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("messageType", "alert-success");
            model.addAttribute("message", "Пользователь успешно активирован");
        } else {
            model.addAttribute("messageType", "alert-danger");
            model.addAttribute("message", "Код активации не найден");
        }

        return "login";
    }
}

