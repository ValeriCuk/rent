package org.example.rent.services;

import org.apache.logging.log4j.Logger;
import org.example.rent.dto.UserDTO;
import org.example.rent.entity.User;
import org.example.rent.exceptions.NotFoundException;
import org.example.rent.other.CustomLogger;
import org.example.rent.repositories.interfaces.UserRepository;
import org.example.rent.services.mappers.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final Logger log = CustomLogger.getLog();

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    //getById(Long id)
    public UserDTO getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
        log.info("User with id " + id + " found");
        return userMapper.toDto(user);
    }

    //getAll()
    public List<UserDTO> getAll() {
        List<User> users = userRepository.findAll();
        log.info("Users found");
        return users.stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    //save(DTO dto)
    @Transactional
    public void save(UserDTO dto) {
        User user = userMapper.toEntity(dto);
        userRepository.save(user);
        log.info("User with id " + user.getId() + " saved");
    }

    //delete(Long id)
    @Transactional
    public void delete(Long id) {
        if (!userRepository.existsById(id))
            throw new NotFoundException("User with id " + id + " not found");
        userRepository.deleteById(id);
        log.info("User with id " + id + " deleted");
    }

    //update(Long id, DTO dto)
    @Transactional
    public void update(Long id, UserDTO dto) {
        if (!userRepository.existsById(id))
            throw new NotFoundException("User with id " + id + " not found");
        User user = userMapper.toEntity(dto);
        user.setId(id);
        userRepository.save(user);
        log.info("User with id " + id + " updated");
    }
}
