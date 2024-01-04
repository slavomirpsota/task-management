package org.psota.taskmanagementbe.controller;

import lombok.RequiredArgsConstructor;
import org.psota.taskmanagementbe.api.request.AuthenticationRequest;
import org.psota.taskmanagementbe.api.request.RegistrationRequest;
import org.psota.taskmanagementbe.api.response.AuthenticationResponse;
import org.psota.taskmanagementbe.exception.ServiceException;
import org.psota.taskmanagementbe.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody RegistrationRequest request) throws ServiceException {
        return ResponseEntity.ok().body(userService.registerUser(request));
    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) throws ServiceException {
        return ResponseEntity.ok().body(userService.authenticateUser(request));
    }


}
