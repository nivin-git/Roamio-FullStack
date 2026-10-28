package com.roamio.auth;

import com.roamio.security.JwtService;
import com.roamio.user.*;
import jakarta.validation.constraints.*;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository users;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public AuthController(UserRepository users,PasswordEncoder encoder,JwtService jwt){
        this.users=users;this.encoder=encoder;this.jwt=jwt;
    }

    public record RegisterRequest(@NotBlank String fullName,@Email @NotBlank String email,
                                  @Size(min=6) String password, Role role){}
    public record LoginRequest(@Email @NotBlank String email,@NotBlank String password){}
    public record AuthResponse(String token, Long id,String email,String fullName,Role role){}

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest r){
        if(users.existsByEmailIgnoreCase(r.email())) return ResponseEntity.badRequest().body("Email already registered");
        User u=new User(); u.setFullName(r.fullName()); u.setEmail(r.email().toLowerCase());
        u.setPasswordHash(encoder.encode(r.password()));
        u.setRole(r.role()==null?Role.TRAVELER:r.role());
        users.save(u);
        return ResponseEntity.ok(new AuthResponse(jwt.generate(u.getEmail()),u.getId(),u.getEmail(),u.getFullName(),u.getRole()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest r){
        return users.findByEmailIgnoreCase(r.email())
            .filter(u->encoder.matches(r.password(),u.getPasswordHash()))
            .<ResponseEntity<?>>map(u->ResponseEntity.ok(new AuthResponse(jwt.generate(u.getEmail()),u.getId(),u.getEmail(),u.getFullName(),u.getRole())))
            .orElseGet(()->ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password"));
    }
}
