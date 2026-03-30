package org.security.controller;

import org.security.contract.AuthUser;
import org.security.jwt.JwtTokenProvider;
import org.security.security.CustomUserPrincipalService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final CustomUserPrincipalService userService;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationController(
            CustomUserPrincipalService userService,
            JwtTokenProvider tokenProvider,
            PasswordEncoder passwordEncoder
    ) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(
            @RequestBody RegisterRequest request
    ) {
        boolean created = userService.registerUser(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword())
        );
        if (!created) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(
                            "Username already exists, Please login"
                    ));
        }
        return ResponseEntity.ok(
                new ApiResponse("User registered successfully")
        );
    }

    @PostMapping("/admin/register")
    public ResponseEntity<ApiResponse> registerAdmin(
            @RequestBody RegisterRequest request
    ) {
        boolean created = userService.registerAdmin(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword())
        );
        if (!created) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(
                            "Admin already exists"
                    ));
        }
        return ResponseEntity.ok(
                new ApiResponse("Admin registered successfully")
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        AuthUser user = userService
                .loadByUsername(request.getUsername())
                .orElse(null);

        if (user == null) {
            return ResponseEntity.status(401)
                    .body(new ApiResponse("Invalid credentials"));
        }
        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {
            return ResponseEntity.status(401)
                    .body(new ApiResponse("Invalid credentials"));
        }

        String token = tokenProvider.generateToken(user);

        return ResponseEntity.ok(new LoginResponse(token));
    }
}