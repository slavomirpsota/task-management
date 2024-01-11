package org.psota.taskmanagementbe.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.psota.taskmanagementbe.api.request.AuthenticationRequest;
import org.psota.taskmanagementbe.api.request.RegistrationRequest;
import org.psota.taskmanagementbe.api.response.AuthenticationResponse;
import org.psota.taskmanagementbe.exception.ServiceException;
import org.psota.taskmanagementbe.mapper.UserMapper;
import org.psota.taskmanagementbe.persistence.dao.RoleDao;
import org.psota.taskmanagementbe.persistence.dao.UserDao;
import org.psota.taskmanagementbe.service.util.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    private final UserMapper userMapper;

    private final RoleDao roleDao;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse registerUser(RegistrationRequest request) throws ServiceException {
        var user = userMapper.registrationRequestToUser(request);
        var role = roleDao.getRoleByRoleName("USER").orElseThrow(() -> new ServiceException("user.role.not-found"));
        user.setRoles(Set.of(role));
        userDao.save(user);
        var token = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticateUser(AuthenticationRequest request) throws ServiceException {
        authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        var user = userDao.findByUsername(request.getUsername()).orElseThrow(() -> new ServiceException("user.username.not-found"));
        var token = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .accessToken(token)
                .build();
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            var user = userDao.findByUsername(username)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
