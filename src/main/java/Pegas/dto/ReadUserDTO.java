package Pegas.dto;

import Pegas.entity.PersonalInfo;
import Pegas.entity.Role;

public record ReadUserDTO (Long id, PersonalInfo personalInfo, String username, Role role, CompanyReadDTO company){}
