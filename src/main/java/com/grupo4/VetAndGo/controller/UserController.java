package com.grupo4.VetAndGo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo4.VetAndGo.controller.dto.LoginDto;
import com.grupo4.VetAndGo.controller.dto.LoginResponseDto;
import com.grupo4.VetAndGo.controller.dto.UserDto;
import com.grupo4.VetAndGo.controller.webmodel.UserInsert;
import com.grupo4.VetAndGo.domain.exception.ValidationException;
import com.grupo4.VetAndGo.domain.model.Role;
import com.grupo4.VetAndGo.domain.model.User;
import com.grupo4.VetAndGo.domain.service.TokenUtils;
import com.grupo4.VetAndGo.domain.service.UserService;

@RestController
@RequestMapping("/api/bank/users")
public class UserController {

  private final UserService userService;
  private final TokenUtils tokenUtils;

  public UserController(UserService userService, TokenUtils tokenUtils) {
    this.userService = userService;
    this.tokenUtils = tokenUtils;
  }

  @GetMapping("")
  public ResponseEntity<List<UserDto>> getAllUsers() {
    return ResponseEntity.ok(userService.getAll());
  }

  @PostMapping("/register")
  public ResponseEntity<UserDto> createUser(@RequestBody UserInsert userInsert) {
    UserDto userToCreate = new UserDto(null, userInsert.username(), userInsert.password(), userInsert.role());
    UserDto createdUser = userService.create(userToCreate);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
  }

  @PostMapping("/auth/login/{role}")
  public ResponseEntity<LoginResponseDto> login(@PathVariable String role, @RequestBody LoginDto loginDto) {
    UserDto user = userService.findByUsername(loginDto.username());
    if (role.equals("admin") && user.role() != Role.ADMIN) {
      throw new ValidationException(
          "Usuario no autorizado para acceder como administrador.");
    }

    String token = userService.login(loginDto);
    LoginResponseDto response = new LoginResponseDto(token, user.username(), user.role().toString());
    return ResponseEntity.ok(response);
  }

  @PostMapping("/auth/session")
  public ResponseEntity<UserDto> validateSession(@RequestBody String token) {
    User user = tokenUtils.validateToken(token);
    if (user == null) {
      throw new ValidationException(
          "Token inválido o expirado.");
    }

    UserDto userDto = new UserDto(user.getId(), user.getUsername(), user.getPassword(), user.getRole());
    return ResponseEntity.ok(userDto);
  }

}
