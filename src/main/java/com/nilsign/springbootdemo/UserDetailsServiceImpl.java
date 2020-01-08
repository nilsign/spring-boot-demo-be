package com.nilsign.springbootdemo;

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

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserEntityService userEntityService;

  @Override
  // Why should this read be transactional?
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String email) {
    // Reformat the lambda expression to make it more readable
    UserEntity userEntity = userEntityService.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException(email));

    //Optional<UserEntity> userEntity = userEntityService.findByEmail(email);
    //userEntity.orElseThrow(() -> new UsernameNotFoundException(email));

    // Write a readable lambda expression to create the GrantedAuthorities here
    Set<GrantedAuthority> grantedAuthorities = userEntity.getRoles()
        .stream()
        .map(roleEntity -> new SimpleGrantedAuthority(roleEntity.getRoleName()))
        .collect(Collectors.toSet());
    //Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
    //for (RoleEntity roleEntity : userEntity.get().getRoles()) {
    //  grantedAuthorities.add(new SimpleGrantedAuthority(roleEntity.getRoleName()));
    //}
    // See comment in UserEntity. Your UserEntity should extend the User from spring security.
    return new User(
        userEntity.getEmail(),
        userEntity.getPassword(),
        grantedAuthorities);
  }
}
