package com.Airplane.AirplaneApp.AAA.Controller;

import com.Airplane.AirplaneApp.AAA.DTO.AuthResponse;
import com.Airplane.AirplaneApp.AAA.DTO.UserDTO;
import com.Airplane.AirplaneApp.AAA.Entity.User;
import com.Airplane.AirplaneApp.AAA.Service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UserDTO userDTO) {
        AuthResponse response = authService.register(userDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserDTO userDTO) {
        AuthResponse response = authService.login(userDTO.getUsername(), userDTO.getPassword());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
