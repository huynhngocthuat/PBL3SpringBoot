package com.bkdn.pbl3.service.impl;

import com.bkdn.pbl3.domain.Account;
import com.bkdn.pbl3.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private AccountService accountService;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Account account = accountService.findByUserName(userName);

        if (account == null) {
            System.out.println("User not found! " + userName);
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }
        String roleName;
        if(account.getRole()==1){
            roleName = "ROLE_ADMIN";
        }
        else {
            roleName = "ROLE_USER";
        }

        // [ROLE_USER, ROLE_ADMIN,..]
        List<GrantedAuthority> grant = new ArrayList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority(roleName);
        grant.add(authority);

        UserDetails userDetails =(UserDetails) new User(account.getUserName(), account.getPassWord(),grant);

        return userDetails;
    }
}
