package com.kenguru.demowebapp.services;

import com.kenguru.demowebapp.entities.Role;
import com.kenguru.demowebapp.entities.Users;
import com.kenguru.demowebapp.repositories.UsersRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;



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
}
