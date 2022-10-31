package com.kenguru.demowebapp.controllers;

import com.kenguru.demowebapp.entities.Role;
import com.kenguru.demowebapp.entities.Users;
import com.kenguru.demowebapp.services.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model){
        model.addAttribute("users", userService.findAllUsers());

        return "userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    //  /user/id
    @GetMapping("{user}")
    public String userEdit(@PathVariable Users user, Model model){
        model.addAttribute("user",user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") Users user
    ) {
        userService.saveEditedUser(user, username, form);

        return "redirect:/user";
    }


    @GetMapping("profile")
    public String getProfile(Model model, @AuthenticationPrincipal Users user) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("email", user.getEmail());

        return "profile";
    }

    @PostMapping("profile")
    public String updateProfile(
            @AuthenticationPrincipal Users user,
            @RequestParam String password,
            @RequestParam String email
    ) {
        userService.updateProfile(user, password, email);

        return "redirect:/user/profile";
    }

}
