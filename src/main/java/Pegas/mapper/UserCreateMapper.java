package Pegas.mapper;

import Pegas.dao.CompanyRepository;
import Pegas.dto.UserCreateDTO;
import Pegas.entity.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserCreateMapper implements Mapper<UserCreateDTO, User>{
    private  final CompanyRepository companyRepository;
    @Override
    public User mapFrom(UserCreateDTO object) {
        return User.builder()
                .personalInfo(object.personalInfo())
                .username(object.username())
                .role(object.role())
                .company(companyRepository.findBuId(object.companyId()).orElseThrow(IllegalArgumentException::new))
                .build();
    }
}
