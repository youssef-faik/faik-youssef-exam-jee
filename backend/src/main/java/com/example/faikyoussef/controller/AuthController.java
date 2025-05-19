package com.example.faikyoussef.controller;

import com.example.faikyoussef.dto.JwtResponse;
import com.example.faikyoussef.dto.LoginRequest;
import com.example.faikyoussef.dto.MessageResponse;
import com.example.faikyoussef.dto.SignupRequest;
import com.example.faikyoussef.entity.Client;
import com.example.faikyoussef.entity.ERole;
import com.example.faikyoussef.entity.Role;
import com.example.faikyoussef.entity.User;
import com.example.faikyoussef.repository.ClientRepository;
import com.example.faikyoussef.repository.RoleRepository;
import com.example.faikyoussef.repository.UserRepository;
import com.example.faikyoussef.security.jwt.JwtUtils;
import com.example.faikyoussef.security.services.UserDetailsImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "API for user authentication and registration")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ClientRepository clientRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, 
                         UserRepository userRepository, 
                         RoleRepository roleRepository,
                         ClientRepository clientRepository,
                         PasswordEncoder encoder, 
                         JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.clientRepository = clientRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signin")
    @Operation(
        summary = "Authenticate user",
        description = "Authenticates a user with username and password, returns JWT token",        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Login credentials",
            content = @Content(schema = @Schema(implementation = LoginRequest.class))
        ),        
        responses = {
            @ApiResponse(responseCode = "200", description = "Authentication successful",
                    content = @Content(schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
        },
        security = {}  // No security required for this endpoint
    )
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                                                 userDetails.getId(), 
                                                 userDetails.getUsername(), 
                                                 userDetails.getEmail(), 
                                                 roles));
    }

    @PostMapping("/signup")
    @Operation(
        summary = "Register new user",
        description = "Registers a new user with username, email, password and roles",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Registration details",
            content = @Content(schema = @Schema(implementation = SignupRequest.class))
        ),        responses = {
            @ApiResponse(responseCode = "200", description = "Registration successful",
                    content = @Content(schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
        },
        security = {}  // No security required for this endpoint
    )
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                             signUpRequest.getEmail(),
                             encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            Role clientRole = roleRepository.findByName(ERole.ROLE_CLIENT)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(clientRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "employe":
                        Role modRole = roleRepository.findByName(ERole.ROLE_EMPLOYE)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_CLIENT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        User savedUser = userRepository.save(user);
        
        // Create client profile if required
        if (signUpRequest.getClientInfo() != null && roles.stream().anyMatch(r -> r.getName() == ERole.ROLE_CLIENT)) {
            Client client = new Client();
            client.setName(signUpRequest.getClientInfo().getName() != null ? 
                          signUpRequest.getClientInfo().getName() : 
                          signUpRequest.getUsername());
            client.setEmail(signUpRequest.getEmail());
            client.setUser(savedUser);
            clientRepository.save(client);
        }

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
