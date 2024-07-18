package com.api.portofolio.services;

import com.api.portofolio.repositories.PortoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PortoUserDetailsService implements UserDetailsService {

    @Autowired
    private PortoUserRepository portoUserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return portoUserRepository.findByEmail(username).orElseThrow();
    }
}
