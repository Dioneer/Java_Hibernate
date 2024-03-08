package Pegas.service;

import Pegas.dao.CompanyRepository;
import Pegas.dao.UserRepository;
import Pegas.dto.ReadUserDTO;
import Pegas.dto.UserCreateDTO;
import Pegas.entity.User;
import Pegas.mapper.UserCreateMapper;
import Pegas.mapper.UserReadMapper;
import jakarta.validation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateMapper userCreateMapper;

    public boolean delete(Long id){
        Optional<User> user=userRepository.findBuId(id);
        user.ifPresent(i->userRepository.delete(id));
        return user.isPresent();
    }
    public Optional<ReadUserDTO>findUserId(Long id){
        return userRepository.findBuId(id).map(userReadMapper::mapFrom);
    }
    public Long create(UserCreateDTO userCreateDTO){
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<UserCreateDTO>> violationSet = validator.validate(userCreateDTO);
        if(!violationSet.isEmpty()){
            throw new ConstraintViolationException(violationSet);
        }
        User userEntity = userCreateMapper.mapFrom(userCreateDTO);
        return userRepository.save(userEntity).getId();
    }
    public void update(UserCreateDTO userCreateDTO){
        List<User> arr = userRepository.findAll();
        User findUser = null;
        for (User i : arr){
            if(i.getUsername().equals(userCreateDTO.username())) {
                findUser = i;
            }
        }
        User userUpdate = userCreateMapper.mapFrom(userCreateDTO);
        assert findUser != null;
        if(!findUser.getUsername().equals(userUpdate.getUsername())){
           findUser.setUsername(userUpdate.getUsername());
       }
       if(!findUser.getCompany().equals(userUpdate.getCompany())){
            findUser.setCompany(userUpdate.getCompany());
        }
        if(!findUser.getPersonalInfo().getFirstname().equals(userUpdate.getPersonalInfo().getFirstname())){
            findUser.setPersonalInfo(userUpdate.getPersonalInfo());
        }
        userRepository.update(findUser);
    }
}
