package org.psota.taskmanagementbe.service;

import lombok.RequiredArgsConstructor;
import org.psota.taskmanagementbe.api.request.AuthenticationRequest;
import org.psota.taskmanagementbe.api.request.RegistrationRequest;
import org.psota.taskmanagementbe.api.response.AuthenticationResponse;
import org.psota.taskmanagementbe.exception.ServiceException;
import org.psota.taskmanagementbe.mapper.UserMapper;
import org.psota.taskmanagementbe.persistence.dao.RoleDao;
import org.psota.taskmanagementbe.persistence.dao.UserDao;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

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
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public AuthenticationResponse authenticateUser(AuthenticationRequest request) throws ServiceException {
        authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        var user = userDao.findByUsername(request.getUsername()).orElseThrow(() -> new ServiceException("user.username.not-found"));
        var token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
}
