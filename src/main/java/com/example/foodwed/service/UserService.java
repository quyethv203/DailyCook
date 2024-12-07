package com.example.foodwed.service;

import com.example.foodwed.dto.Request.UserCreateRequest;
import com.example.foodwed.dto.Request.UserLogin;
import com.example.foodwed.dto.Request.UserUpdateRequest;
import com.example.foodwed.dto.response.UserResponse;
import com.example.foodwed.entity.User;
import com.example.foodwed.enums.Role;
import com.example.foodwed.exception.Appexception;
import com.example.foodwed.exception.ErrorCode;
import com.example.foodwed.repository.UserReponsitory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserReponsitory userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    public User createUser(UserCreateRequest request){
        User user = new User();
        if(userRepository.existsByEmail(request.getEmail())){
            throw new Appexception(ErrorCode.USSER_EXITED);
        }
        user.setFullname(request.getFullname());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        user.setRole(roles);
        user.setEmail(request.getEmail());

        return userRepository.save(user);
    }

    public List<UserResponse> getUsers() {
        log.info("In method get Users");
        return userRepository.findAll().stream()
                .map(user -> new UserResponse(user.getUserid(), user.getEmail(), user.getFullname(), user.getPassword(), user.getRole())) // Chuyển đổi trực tiếp từ User sang UserResponse
                .toList();
    }

    // doi mat khau
    public User changePassword(String userId, UserUpdateRequest request) {
        // Tìm người dùng
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Appexception(ErrorCode.USERNOTFOUND));

        // Kiểm tra mật khẩu cũ
        if (!passwordEncoder.matches(request.getOldpassword(), user.getPassword())) {
            throw new Appexception(ErrorCode.INVALIDPASSWORD);
        }

        // Cập nhật mật khẩu mới
        user.setPassword(passwordEncoder.encode(request.getNewpassword()));
        return userRepository.save(user);
    }


}
