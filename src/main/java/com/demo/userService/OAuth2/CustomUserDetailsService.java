package com.demo.userService.OAuth2;

import com.demo.userService.models.User;
import com.demo.userService.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //username == email
        Optional<User> byEmail = userRepository.findByEmail(email);
        if(byEmail.isEmpty()){
            throw new UsernameNotFoundException("User with email " + email + " not found");
        }
        User user = byEmail.get();
        //convert User to CustomUserDetails
        return new CustomUserDetails(user);
    }
}
