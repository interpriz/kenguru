package com.kenguru.demowebapp.services;

import com.kenguru.demowebapp.entities.Role;
import com.kenguru.demowebapp.entities.Users;
import com.kenguru.demowebapp.repositories.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserService implements UserDetailsService {

    private final MailSenderService mailSender;

    private final UsersRepository userRepo;

    public UserService(MailSenderService mailSender, UsersRepository userRepo) {
        this.mailSender = mailSender;
        this.userRepo = userRepo;
    }

    public boolean registration(Users user){
        Users userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        userRepo.save(user);

        sendMessage(user);

        return true;

    }

    private void sendMessage(Users user) {
        if (!user.getEmail().isBlank()) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Sweater. Please, visit next link: http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );

            mailSender.send(user.getEmail(), "Activation code", message);
        }
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return userRepo.findByUsername(userName);
    }

    public List<Users> findAllUsers(){
        return userRepo.findAll();
    }

    public Users loadUserById(Long id){
        //return userRepo.getById(id);
        return userRepo.findById(id).orElse(null);
    }

    public void saveEditedUser(Users user, String name, Map<String, String> formParams){

        user.setUsername(name);

        //все роли
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : formParams.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

        userRepo.save(user);
    }

    public boolean activateUser(String code) {
        Users user =  userRepo.findByActivationCode(code);
        if(user == null){
            return false;
        }
        user.setActivationCode(null);
        userRepo.save(user);

        return true;
    }

    public void updateProfile(Users user, String password, String email) {
        //Users user = userRepo.findByUsername(usr.getUsername());

        String userEmail = user.getEmail();

        boolean isEmailChanged = (email != null && !email.equals(userEmail)) ||
                (userEmail != null && !userEmail.equals(email));

        if (isEmailChanged) {
            user.setEmail(email);

            if (!email.isEmpty()) {
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }

        if (!password.isEmpty()) {
            user.setPassword(password);
        }

        userRepo.save(user);

        if (isEmailChanged) {
            sendMessage(user);
        }
    }
}
