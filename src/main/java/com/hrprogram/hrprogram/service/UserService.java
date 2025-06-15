package com.hrprogram.hrprogram.service;

import com.hrprogram.hrprogram.exception.NotFoundException;
import com.hrprogram.hrprogram.mapper.UserMapper;
import com.hrprogram.hrprogram.model.dto.UserDto;
import com.hrprogram.hrprogram.model.request.UserRequest;
import com.hrprogram.hrprogram.repository.UserRepository;
import com.hrprogram.hrprogram.response.ApiResponse;
import com.hrprogram.hrprogram.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private MailService mailService;
    private final UserRepository userRepository;

    public void createUser(UserRequest userRequest){
      log.info("Action.createUser.start for id {}", userRequest.getId());
      var entity = UserMapper.INSTANCE.requestToEntity(userRequest);
      userRepository.save(entity);
      log.info("Action.createUser.end for id {}", userRequest.getId());
    }

    public ApiResponse getUserById(Long id){
        log.info("Action.getUserById.start for id {}", id);
        var user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Id Not Found"));
        var userDto = UserMapper.INSTANCE.toDto(user);
        ApiResponse apiResponse = new ApiResponse(userDto);
        log.info("Action.getUserById.end by id {}", id);
        return apiResponse;
    }

    public UserResponse getUserResponseById(Long id){
        log.info("Action.getUserResponseById.start for id {}", id);
        var user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Id Not Found"));
        var userDto = UserMapper.INSTANCE.entityToResponse(user);
        log.info("Action.getUserResponseById.end for id {}", id);
        return userDto;
    }

    public ApiResponse getAllUsers(){
        log.info("Action.getAllUsers.star");
        var users = userRepository.findAll().stream()
                .map(UserMapper.INSTANCE::toDto)
                .toList();
        ApiResponse apiResponse = new ApiResponse(users);
        log.info("Action.getAllUsers.end");
        return apiResponse;
    }

    public void userHardDeleteById(Long id){
        log.info("Action.userHardDeleteById.start for id {}", id);
        userRepository.deleteById(id);
        log.info("Action.userHardDeleteById.end for id {}", id);
    }

    public void useSoftDeleteById(Long id){
        log.info("Acton.useSoftDeleteById.start for id {}", id);
        var user = getUserResponseById(id);
        var userEntity = UserMapper.INSTANCE.responseToEntity(user);
        userEntity.setActive(false);
        log.info("Acton.useSoftDeleteById.end for id {}", id);
    }

}
