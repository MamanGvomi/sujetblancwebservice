package com.example.ctwebserv.config;

import com.example.ctwebserv.controller.Controller;
import com.example.ctwebserv.models.Association;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomUserDetailsService implements UserDetailsService {
    private static final String[] ROLES_MODERATEUR = {"USER","ADMIN"};
    private static final String[] ROLES_RESPONSABLE = {"USER"};

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Association responsable = Controller.getAssociationByResponsableName(s);
        if (responsable == null){
            throw new UsernameNotFoundException("Il n'existe pas d'association nommé : " + s + ". Donc aucun responsable.");
        }
        String[] roles = responsable.getNomResponsable().equals("admin") ? ROLES_MODERATEUR : ROLES_RESPONSABLE;
        return User.builder()
                .username(responsable.getNomResponsable())
                .password(passwordEncoder.encode(responsable.getNomAssociation()))
                .roles(roles)
                .build();
    }
}
