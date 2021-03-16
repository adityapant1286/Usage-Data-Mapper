package com.zuora.usagedatamapper.model.configs.vo;

import com.zuora.usagedatamapper.model.configs.dao.AuthoritiesDao;
import com.zuora.usagedatamapper.model.configs.dao.UserDao;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AppUserDetails implements UserDetails {

    private String username;
    private String password;
    private boolean active;
    private List<GrantedAuthority> authorities;

    public AppUserDetails(UserDao dao, List<AuthoritiesDao> authoritiesDaos) {
        this.username = dao.getUsername();
        this.password = dao.getPassword();
        this.active = dao.isEnabled();
        this.authorities = authoritiesDaos.stream()
                            .map(d -> new SimpleGrantedAuthority(d.getAuthority()))
                            .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
