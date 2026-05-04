package com.kdob.piq.user.infrastructure.web;

import com.kdob.piq.user.application.service.UserService;
import com.kdob.piq.user.domain.model.User;
import com.kdob.piq.user.infrastructure.config.GatewayPrincipal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal GatewayPrincipal principal) {
        User user = userService.findByAuthId(Long.parseLong(principal.userId()));
        return ResponseEntity.ok(UserResponse.from(user));
    }
}