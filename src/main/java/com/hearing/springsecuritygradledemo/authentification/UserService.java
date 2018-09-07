package com.hearing.springsecuritygradledemo.authentification;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

public class UserService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername...");

        List<String> roles = new ArrayList<>();

        if (s.equals("hearing")) {
            roles.add("role1");
            return new User(s, new BCryptPasswordEncoder().encode("123456"), true, roles);
        } else if (s.equals("hhh") ){
            return new User(s, new BCryptPasswordEncoder().encode("123456"), true, roles);
        } else {
            return null;
        }
    }
}
