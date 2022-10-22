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

    private UsersRepository userRepo;

    public UserService(UsersRepository userRepo) {
        this.userRepo = userRepo;
    }

    public boolean registration(Users user){
        Users userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return false;
        }else{
            userFromDb = new Users(user.getUsername(),user.getPassword(),true, Collections.singleton(Role.USER));
            userRepo.save(userFromDb);
            return true;
        }
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return userRepo.findByUsername(userName);
    }

    public List<Users> findAllUsers(){
        return userRepo.findAll();
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
}
