package com.nilsign.springbootdemo;

import com.nilsign.springbootdemo.entity.RoleEntity;
import com.nilsign.springbootdemo.entity.UserEntity;
import com.nilsign.springbootdemo.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserEntityService userEntityService;

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String email) {
    Optional<UserEntity> userEntity = userEntityService.findByEmail(email);
    userEntity.orElseThrow(() -> new UsernameNotFoundException(email));
    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
    for (RoleEntity roleEntity : userEntity.get().getRoles()){
      grantedAuthorities.add(new SimpleGrantedAuthority(roleEntity.getRoleName()));
    }
    return new User(
        userEntity.get().getEmail(),
        userEntity.get().getPassword(),
        grantedAuthorities);
  }
}
