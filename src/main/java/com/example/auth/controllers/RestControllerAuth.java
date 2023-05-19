package com.example.auth.controllers;

import com.example.auth.dtos.DtoAuthRespuesta;
import com.example.auth.dtos.DtoLogin;
import com.example.auth.dtos.DtoRegistro;
import com.example.auth.modelo.roles;
import com.example.auth.modelo.usuarios;
import com.example.auth.repositories.IRolesRepository;
import com.example.auth.repositories.IUsuariosRepository;
import com.example.auth.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth/")
public class RestControllerAuth {
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private IRolesRepository rolesRepository;
    private IUsuariosRepository usuariosRepository;
    private JwtTokenProvider tokenProvider;

    @Autowired
    public RestControllerAuth(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, IRolesRepository rolesRepository, IUsuariosRepository usuariosRepository, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.rolesRepository = rolesRepository;
        this.usuariosRepository = usuariosRepository;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("registro")
    public ResponseEntity<String> registrar(@RequestBody DtoRegistro dtoRegistro){
        if (usuariosRepository.existsByName(dtoRegistro.getUsername())){
            return new ResponseEntity<>("El usuario ya existe", HttpStatus.BAD_REQUEST);
        }
        usuarios user = new usuarios();
        user.setName(dtoRegistro.getUsername());
        user.setPassword(passwordEncoder.encode(dtoRegistro.getPassword()));
        user.setLast_name(dtoRegistro.getLast_name());
        user.setEmail(dtoRegistro.getEmail());
        roles rol = rolesRepository.findByName("USER").get();
        user.setRol(Collections.singletonList(rol));
        usuariosRepository.save(user);
        return new ResponseEntity<>("Registro exitoso",HttpStatus.OK);
    }

    @PostMapping("registroAdm")
    public ResponseEntity<String> registrarAdmin(@RequestBody DtoRegistro dtoRegistro){
        if (usuariosRepository.existsByName(dtoRegistro.getUsername())){
            return new ResponseEntity<>("El usuario ya existe", HttpStatus.BAD_REQUEST);
        }
        usuarios user = new usuarios();
        user.setName(dtoRegistro.getUsername());
        user.setPassword(passwordEncoder.encode(dtoRegistro.getPassword()));
        user.setLast_name(dtoRegistro.getLast_name());
        user.setEmail(dtoRegistro.getEmail());
        roles rol = rolesRepository.findByName("ADMIN").get();
        user.setRol(Collections.singletonList(rol));
        usuariosRepository.save(user);
        return new ResponseEntity<>("Registro de admin exitoso",HttpStatus.OK);
    }

    @PostMapping("login")
    public ResponseEntity<DtoAuthRespuesta> login(@RequestBody DtoLogin dtoLogin){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                dtoLogin.getUsername(),dtoLogin.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generarToken(authentication);
        return new ResponseEntity<>(new DtoAuthRespuesta(token),HttpStatus.OK);
    }

}
