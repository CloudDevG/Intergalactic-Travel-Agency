package com.clouddevg.ita.service;

import com.clouddevg.ita.dto.entity.user.UserDto;
import com.clouddevg.ita.dto.mapper.UserMapper;
import com.clouddevg.ita.entity.user.Role;
import com.clouddevg.ita.entity.user.User;
import com.clouddevg.ita.entity.user.UserRoles;
import com.clouddevg.ita.exception.EntityType;
import com.clouddevg.ita.exception.ExceptionType;
import com.clouddevg.ita.exception.ITAException;
import com.clouddevg.ita.repository.user.RoleRepository;
import com.clouddevg.ita.repository.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static com.clouddevg.ita.exception.EntityType.USER;
import static com.clouddevg.ita.exception.ExceptionType.DUPLICATE_ENTITY;
import static com.clouddevg.ita.exception.ExceptionType.ENTITY_NOT_FOUND;

@Component
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final FlightReservationService flightReservationService;

    private final ModelMapper modelMapper;

    public UserServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository,
                           UserRepository userRepository,
                           FlightReservationService flightReservationService, ModelMapper modelMapper) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.flightReservationService = flightReservationService;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDto signup(UserDto userDto) {
        Role userRole;
        User user = userRepository.findByEmail(userDto.getEmail());
        if (user == null) {
            if (userDto.isAdmin()) {
                userRole = roleRepository.findByRole(UserRoles.ADMIN);
            } else {
                userRole = roleRepository.findByRole(UserRoles.PASSENGER);
            }
            user = new User()
                    .setEmail(userDto.getEmail())
                    .setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()))
                    .setRoles(new HashSet<>(Arrays.asList(userRole)))
                    .setFirstName(userDto.getFirstName())
                    .setLastName(userDto.getLastName())
                    .setMobileNumber(userDto.getMobileNumber());
            return UserMapper.toUserDto(userRepository.save(user));
        }
        throw exception(USER, DUPLICATE_ENTITY, userDto.getEmail());
    }

    @Transactional
    public UserDto findUserByEmail(String email) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));
        if (user.isPresent()) {
            return modelMapper.map(user.get(), UserDto.class);
        }
        throw exception(USER, ENTITY_NOT_FOUND, email);
    }

    @Override
    public UserDto updateProfile(UserDto userDto) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(userDto.getEmail()));
        if (user.isPresent()) {
            User userModel = user.get();
            userModel.setFirstName(userDto.getFirstName())
                    .setLastName(userDto.getLastName())
                    .setMobileNumber(userDto.getMobileNumber());
            return UserMapper.toUserDto(userRepository.save(userModel));
        }
        throw exception(USER, ENTITY_NOT_FOUND, userDto.getEmail());
    }

    @Override
    public UserDto changePassword(UserDto userDto, String newPassword) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(userDto.getEmail()));
        if (user.isPresent()) {
            User userModel = user.get();
            userModel.setPassword(bCryptPasswordEncoder.encode(newPassword));
            return UserMapper.toUserDto(userRepository.save(userModel));
        }
        throw exception(USER, ENTITY_NOT_FOUND, userDto.getEmail());
    }

    private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String... args) {
        return ITAException.throwException(entityType, exceptionType, args);
    }
}

