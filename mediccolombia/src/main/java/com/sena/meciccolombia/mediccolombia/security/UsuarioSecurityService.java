package com.sena.meciccolombia.mediccolombia.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sena.meciccolombia.mediccolombia.dao.UsuarioDAO;
import com.sena.meciccolombia.mediccolombia.domain.Usuario;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioSecurityService implements UserDetailsService {
    
    private final UsuarioDAO usuarioDAO;

    @Override
    public UserDetails loadUserByUsername(String correo)
                
    throws UsernameNotFoundException {
        Usuario usuario = usuarioDAO.findByCorreo(correo)
                        .orElseThrow( () -> 
                                new UsernameNotFoundException(
                                    "Usuario no encontrado."
                                ));
        List<GrantedAuthority> authorities =
                                List.of(
                                    new SimpleGrantedAuthority(
                                        "ROLE_"+usuario.getRol()
                                    )
                                );
                return new MyUserDetails(usuario, authorities);
    }

}
