package com.jpo.pgearback.service;

import com.jpo.pgearback.entity.User;
import com.jpo.pgearback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository v_userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository v_userRepository) {
        this.v_userRepository = v_userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User v_optionalUser = v_userRepository.findFirstByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found")); ;
        return new org.springframework.security.core.userdetails.User(v_optionalUser.getEmail(),
                v_optionalUser.getPassword(), AuthorityUtils.createAuthorityList(v_optionalUser.getRole().name()));
    }
}
