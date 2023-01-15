package pl.edu.pjatk.gymmanagementapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccessDeniedController {

    @GetMapping("/access-denied")
    public ResponseEntity<Object> getAccessDenied() {
        return new ResponseEntity<>("You don't have access to this URL", HttpStatus.UNAUTHORIZED);
    }

}
