package com.kdob.piq.identity.api;

import com.kdob.piq.identity.api.dto.LoginRequest;
import com.kdob.piq.identity.api.dto.RegisterRequest;
import com.kdob.piq.identity.api.dto.TokenResponse;
import com.kdob.piq.identity.application.TokenService;
import com.kdob.piq.identity.application.UserService;
import com.kdob.piq.identity.domain.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final TokenService tokenService;

    public AuthController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Valid @RequestBody RegisterRequest request) {
        userService.register(request.email(), request.password());
    }

    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        User user = userService.authenticate(loginRequest.email(), loginRequest.password());
        return new TokenResponse(tokenService.generateToken(user));
    }
}
