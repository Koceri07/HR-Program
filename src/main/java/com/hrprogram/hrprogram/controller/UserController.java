package com.hrprogram.hrprogram.controller;

import com.hrprogram.hrprogram.model.dto.UserDto;
import com.hrprogram.hrprogram.response.ApiResponse;
import com.hrprogram.hrprogram.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public void create(@RequestBody UserDto userDto){
        userService.createUser(userDto);
    }

    @GetMapping("/{id}")
    public ApiResponse getById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @GetMapping
    public ApiResponse getAll(){
        return userService.getAllUsers();
    }

    @DeleteMapping("/{id}")
    public void hardDelete(@PathVariable Long id){
        userService.userHardDeleteById(id);
    }

    @PutMapping("/{id}")
    public void softDelete(@PathVariable Long id){
        userService.useSoftDeleteById(id);
    }
}
