package Pegas.dto;

import Pegas.entity.PersonalInfo;
import Pegas.entity.Role;

public record UserCreateDTO(PersonalInfo personalInfo, String username, Role role, Long companyId) {
}
