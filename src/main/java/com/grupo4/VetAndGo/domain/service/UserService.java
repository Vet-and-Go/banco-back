package com.grupo4.VetAndGo.domain.service;

import java.util.List;

import com.grupo4.VetAndGo.controller.dto.LoginDto;
import com.grupo4.VetAndGo.controller.dto.UserDto;

public interface UserService {
  List<UserDto> getAll();

  UserDto getById(Long id);

  UserDto findByUsername(String username);

  UserDto create(UserDto userDto);

  UserDto update(Long id, UserDto userDto);

  void delete(Long id);

  String login(LoginDto loginDto);

  void logout(LoginDto loginDto);
}
