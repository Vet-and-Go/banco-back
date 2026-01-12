package com.grupo4.VetAndGo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo4.VetAndGo.controller.dto.UserDto;
import com.grupo4.VetAndGo.controller.webmodel.UserInsert;
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
}
