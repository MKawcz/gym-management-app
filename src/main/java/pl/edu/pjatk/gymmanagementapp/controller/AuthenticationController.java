package pl.edu.pjatk.gymmanagementapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import pl.edu.pjatk.gymmanagementapp.response.request.AuthenticationRequest;
import pl.edu.pjatk.gymmanagementapp.response.request.RegisterRequest;
import pl.edu.pjatk.gymmanagementapp.response.AuthenticationResponse;
import pl.edu.pjatk.gymmanagementapp.service.AuthenticationService;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest request) {
        AuthenticationResponse authenticationResponse = authenticationService.register(request);
        log.info("New registration: {}", request);
        return ResponseEntity.ok(authenticationResponse);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse authenticationResponse = authenticationService.authenticate(request);
        log.info("Login credentials successfully authenticated: {}", request);
        return ResponseEntity.ok(authenticationResponse);
    }
}

