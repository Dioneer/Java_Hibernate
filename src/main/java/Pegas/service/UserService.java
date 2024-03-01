package Pegas.service;

import Pegas.dao.UserRepository;
import Pegas.dto.ReadUserDTO;
import Pegas.dto.UserCreateDTO;
import Pegas.entity.User;
import Pegas.mapper.UserCreateMapper;
import Pegas.mapper.UserReadMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper = new UserReadMapper();
    private final UserCreateMapper userCreateMapper = new UserCreateMapper();

    public boolean delete(Long id){
        Optional<User> user=userRepository.findBuId(id);
        user.ifPresent(i->userRepository.delete(id));
        return user.isPresent();
    }
    public Optional<ReadUserDTO>findUserId(Long id){
        return userRepository.findBuId(id).map(userReadMapper::mapFrom);
    }
    public Long create(UserCreateDTO userCreateDTO){
        User userEntity = userCreateMapper.mapFrom(userCreateDTO);
        return userRepository.save(userEntity).getId();
    }
}
