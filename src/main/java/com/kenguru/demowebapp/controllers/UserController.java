package com.kenguru.demowebapp.controllers;

import com.kenguru.demowebapp.entities.Role;
import com.kenguru.demowebapp.entities.Users;
import com.kenguru.demowebapp.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {


    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String userList(Model model){
        model.addAttribute("users", userService.findAllUsers());

        return "userList";
    }

    //  /user/id
    @GetMapping("{user}")
    public String userEdit(@PathVariable Users user, Model model){
        model.addAttribute("user",user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") Users user
    ) {
        userService.saveEditedUser(user, username, form);
        /*user.setUsername(username);

        //все роли
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepo.save(user);*/

        return "redirect:/user";
    }
}
