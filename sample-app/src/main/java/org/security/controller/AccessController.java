package org.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AccessController {

    @GetMapping("/public/health")
    public ResponseEntity<ApiResponse> health() {
        return ResponseEntity.ok(
                new ApiResponse("Service is up and running")
        );
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/me")
    public ResponseEntity<?> me(Authentication authentication) {

        return ResponseEntity.ok(
                new ApiResponse(
                        "Authenticated user: " + authentication.getName()
                )
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/users")
    public ResponseEntity<ApiResponse> admin() {

        return ResponseEntity.ok(
                new ApiResponse(
                        "Admin access granted. Only admins can see this"
                )
        );
    }
}