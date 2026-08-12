package com.example.JWTAuthAPI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class AuthServiceTest {

  @Autowired
  private AuthService authService;

  @Autowired
  private UserService userService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Test


  void testRegister() {

    RegisterRequest request = new RegisterRequest();
    request.setFirstname("Lukas");
    request.setLastname("Simek");
    request.setEmail("lukas@test.com");
    request.setPassword("heslo123");
    request.setRole(Role.USER);

    AuthResponse response = authService.register(request);

    assertNotNull(response);
    assertNotNull(response.getToken());
  }

  @Test
  void testLogin() {

    UserEntity user = new UserEntity();
    user.setFirstname("Test");
    user.setLastname("User");
    user.setEmail("login@test.com");
    user.setPassword(passwordEncoder.encode("heslo123"));
    user.setRole(Role.USER);

    userService.save(user);

    LoginRequest request = new LoginRequest();
    request.setEmail("login@test.com");
    request.setPassword("heslo123");

    AuthResponse response = authService.login(request);

    assertNotNull(response);
    assertNotNull(response.getToken());
  }
}