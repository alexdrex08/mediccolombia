package com.sena.meciccolombia.mediccolombia.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sena.meciccolombia.mediccolombia.domain.Usuario;

public class MyUserDetails implements UserDetails {

    private final Usuario usuario;
    private final List<GrantedAuthority> authorities;

    public MyUserDetails(Usuario usuario, List<GrantedAuthority> authorities) {
        this.usuario = usuario;
        this.authorities = authorities;
    }

    @Override
    public String getPassword() {
        return usuario.getContrasena();
    }

    @Override
    public String getUsername() {
        return usuario.getCorreo();
    }

    public String getNombre() {
        return usuario.getNombre();
    }

    public Long getId() {
        return usuario.getId();
    }

    public String getCorreo() {
        return usuario.getCorreo();
    }

    public String getRol() {
        return usuario.getRol();
    }

    

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
