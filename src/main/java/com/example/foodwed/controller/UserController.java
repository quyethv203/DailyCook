package com.example.foodwed.controller;

import com.example.foodwed.dto.Request.UserUpdateRequest;
import com.example.foodwed.dto.response.ApiRespone;
import com.example.foodwed.dto.Request.UserCreateRequest;
import com.example.foodwed.dto.response.UserResponse;
import com.example.foodwed.entity.User;
import com.example.foodwed.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping()
    ApiRespone<List<UserResponse>> getUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("email: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return ApiRespone.<List<UserResponse>>builder()
                .result(userService.getUsers())
                .build();
    }


    @PutMapping("/change/{userid}")
    public ResponseEntity<?> changePassword(
            @PathVariable String userid,
            @RequestBody UserUpdateRequest request) {
            User updatedUser = userService.changePassword(userid, request);
            return ResponseEntity.ok(new ApiRespone<>(
                    "success",
                    "1000",
                    "Thay đổi mật khẩu thành công",
                    updatedUser
            ));

        }
}

