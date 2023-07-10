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

import static com.kenguru.demowebapp.StaticStrings.*;

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
            @RequestParam(ATTRIBUTE_PASSWORD_2) String passwordConfirm,
            @Valid Users user,
            BindingResult bindingResult,
            Model model
    ) {
        boolean isConfirmEmpty = passwordConfirm.isEmpty();

        if (isConfirmEmpty) {
            model.addAttribute(ATTRIBUTE_PASSWORD_2_ERROR, MESSAGE_PASSWORD_2_ERROR);
        }

        if (user.getPassword() != null && !user.getPassword().equals(passwordConfirm)) {
            model.addAttribute(ATTRIBUTE_PASSWORD_ERROR, MESSAGE_PASSWORD_ERROR);
        }

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
        }

        if(isConfirmEmpty ||
                model.containsAttribute(ATTRIBUTE_PASSWORD_ERROR) ||
                bindingResult.hasErrors()
        ){
            model.addAttribute(ATTRIBUTE_USER, user);

            return "registration";
        }else{
            if(!userService.registration(user))
            {
                model.addAttribute(ATTRIBUTE_USER_NAME_ERROR, MESSAGE_USER_NAME_ERROR);
                return "registration";
            }
        }




        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute(ATTRIBUTE_MESSAGE_TYPE, VALUE_ALERT_SUCCESS);
            model.addAttribute(ATTRIBUTE_MESSAGE, MESSAGE_USER_ACTIVATION_SUCCESS);
        } else {
            model.addAttribute(ATTRIBUTE_MESSAGE_TYPE, VALUE_ALERT_DANGER);
            model.addAttribute(ATTRIBUTE_MESSAGE, MESSAGE_USER_ACTIVATION_ERROR);
        }

        return "login";
    }
}

