package com.example.JWTAuthAPI;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  public AuthService(UserService userService, PasswordEncoder passwordEncoder, JwtService jwtService) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
  }

  public AuthResponse register(RegisterRequest request) {

    UserEntity user = new UserEntity();
    user.setFirstname(request.getFirstname());
    user.setLastname(request.getLastname());
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setRole(request.getRole());

    userService.save(user);

    String token = jwtService.generateToken(user);

    return new AuthResponse(token);
  }

  public AuthResponse login(LoginRequest request) {

    UserEntity user = userService.findByEmail(request.getEmail());

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new RuntimeException("Invalid password");
    }

    String token = jwtService.generateToken(user);

    return new AuthResponse(token);
  }
}

