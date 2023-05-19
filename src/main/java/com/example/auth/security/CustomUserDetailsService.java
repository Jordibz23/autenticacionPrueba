package com.example.auth.security;

import com.example.auth.modelo.roles;
import com.example.auth.modelo.usuarios;
import com.example.auth.repositories.IUsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private IUsuariosRepository userrepo;

    @Autowired
    public CustomUserDetailsService(IUsuariosRepository userrepo) {
        this.userrepo = userrepo;
    }

    public Collection<GrantedAuthority> mapToAuthorities(List<roles> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        usuarios user = userrepo.findByName(username).orElseThrow(()-> new UsernameNotFoundException("Usuario no encontrado"));
        return new User(user.getName(), user.getPassword(), mapToAuthorities(user.getRol()));
    }
}
