package Pegas.dto;

import Pegas.entity.PersonalInfo;
import Pegas.entity.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record UserCreateDTO(
        @Valid
        PersonalInfo personalInfo,
        @NotNull
        String username, Role role, Long companyId) {
}
