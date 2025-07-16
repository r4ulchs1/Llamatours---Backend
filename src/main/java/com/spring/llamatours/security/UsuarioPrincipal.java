package com.spring.llamatours.security;

import com.spring.llamatours.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UsuarioPrincipal implements UserDetails {

    private final Usuario usuario;

    public UsuarioPrincipal(Usuario usuario) {
        this.usuario = usuario;
        System.out.println("🔐 Login con: " + usuario.getEmail() + " | Rol: " + usuario.getRol());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 1 solo rol por usuario; Spring exige el prefijo "ROLE_"
        return List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name()));
    }

    

    @Override public String getPassword()                       { return usuario.getPassword(); }
    @Override public String getUsername()                       { return usuario.getEmail(); }
    @Override public boolean isAccountNonExpired()              { return true; }
    @Override public boolean isAccountNonLocked()               { return true; }
    @Override public boolean isCredentialsNonExpired()          { return true; }
    @Override public boolean isEnabled()                        { return true; }
}
