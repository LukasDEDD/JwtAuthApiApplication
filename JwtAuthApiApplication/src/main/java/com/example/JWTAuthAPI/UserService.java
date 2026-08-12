package com.example.JWTAuthAPI;

import com.example.JWTAuthAPI.UserEntity;
import com.example.JWTAuthAPI.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {

    this.userRepository = userRepository;
  }

  public UserEntity save(UserEntity user) {

    return userRepository.save(user);
  }

  public UserEntity findByEmail(String email) {
    return userRepository.findByEmail(email)
      .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
  }



  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return findByEmail(email);
  }
}


