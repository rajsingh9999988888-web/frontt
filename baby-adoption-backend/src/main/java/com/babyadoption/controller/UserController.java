package com.babyadoption.controller;

import com.babyadoption.model.User;
import com.babyadoption.model.User.UserRole;
import com.babyadoption.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserRepository userRepository;
    public static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        if (request.getEmail() == null || request.getPassword() == null) {
            return ResponseEntity.badRequest().body("Email and password are required");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        UserRole role = UserRole.NORMAL;
        if (request.getRole() != null) {
            try {
                role = UserRole.valueOf(request.getRole().toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("Invalid role");
            }
        }

        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(request.getPassword());
        newUser.setRole(role);

        if (role == UserRole.NORMAL) {
            newUser.setFullName(request.getFullName());
            newUser.setMobile(request.getMobile());
            newUser.setPrimarySkill(request.getPrimarySkill());
            newUser.setExperience(request.getExperience());
        } else if (role == UserRole.EMPLOYEE) {
            newUser.setCompanyName(request.getCompanyName());
            newUser.setRecruiterName(request.getRecruiterName());
            newUser.setAddress(request.getAddress());
            newUser.setMobile(request.getMobile());
        }

        User savedUser = userRepository.save(newUser);

        String token = generateToken(savedUser);
        return ResponseEntity.ok(new AuthResponse(token, savedUser.getRole().toString()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String email = request.getEmail();
        String password = request.getPassword();

        if (email == null || password == null) {
            return ResponseEntity.badRequest().body("Email and password are required");
        }

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent() && password.equals(user.get().getPassword())) {
            String token = generateToken(user.get());
            return ResponseEntity.ok(new AuthResponse(token, user.get().getRole().toString()));
        }

        return ResponseEntity.badRequest().body("Invalid credentials");
    }

    @GetMapping
    public ResponseEntity<?> getUsers(@RequestHeader("Authorization") String token) {
        if (!isAdmin(token)) {
            return ResponseEntity.status(403).body("Access denied");
        }
        return ResponseEntity.ok(userRepository.findAll());
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<?> updateRole(@PathVariable("id") Integer id, @RequestBody RoleUpdateRequest request,
            @RequestHeader("Authorization") String token) {
        if (!isAdmin(token)) {
            return ResponseEntity.status(403).body("Access denied");
        }

        if (id == null) {
            return ResponseEntity.badRequest().body("User id is required");
        }

        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (request.getRole() == null) {
            return ResponseEntity.badRequest().body("Role is required");
        }

        try {
            UserRole newRole = UserRole.valueOf(request.getRole().toUpperCase());
            User existingUser = user.get();
            existingUser.setRole(newRole);
            userRepository.save(existingUser);
            return ResponseEntity.ok("Role updated");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid role");
        }
    }

    private String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole().toString())
                .claim("userId", user.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
                .signWith(SECRET_KEY)
                .compact();
    }

    private boolean isAdmin(String token) {
        try {
            String role = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build()
                    .parseClaimsJws(token.replace("Bearer ", "")).getBody()
                    .get("role", String.class);
            return "ADMIN".equals(role);
        } catch (Exception e) {
            return false;
        }
    }

    public static class SignupRequest {
        private String email;
        private String password;
        private String role;
        private String mobile;
        private String fullName;
        private String primarySkill;
        private String experience;
        private String companyName;
        private String recruiterName;
        private String address;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public String getPrimarySkill() {
            return primarySkill;
        }

        public void setPrimarySkill(String primarySkill) {
            this.primarySkill = primarySkill;
        }

        public String getExperience() {
            return experience;
        }

        public void setExperience(String experience) {
            this.experience = experience;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getRecruiterName() {
            return recruiterName;
        }

        public void setRecruiterName(String recruiterName) {
            this.recruiterName = recruiterName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    public static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class AuthResponse {
        private String token;
        private String role;

        public AuthResponse(String token, String role) {
            this.token = token;
            this.role = role;
        }

        public String getToken() {
            return token;
        }

        public String getRole() {
            return role;
        }
    }

    public static class RoleUpdateRequest {
        private String role;

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }
}
