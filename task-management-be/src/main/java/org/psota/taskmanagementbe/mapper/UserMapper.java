package org.psota.taskmanagementbe.mapper;

import org.mapstruct.Mapper;
import org.psota.taskmanagementbe.api.request.RegistrationRequest;
import org.psota.taskmanagementbe.persistance.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User registrationRequestToUser(RegistrationRequest in);
}
