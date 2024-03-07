package Pegas.mapper;

import Pegas.dto.CompanyReadDTO;
import Pegas.dto.ReadUserDTO;
import Pegas.entity.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, ReadUserDTO>{
    private final CompanyReadMapper companyReadMapper;
    @Override
    public ReadUserDTO mapFrom(User object) {
        return new ReadUserDTO(object.getId(), object.getPersonalInfo(), object.getUsername(), object.getRole(),
                companyReadMapper.mapFrom(object.getCompany()));
    }
}
