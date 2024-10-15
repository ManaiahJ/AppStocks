package com.manusoft.services;

import com.manusoft.dto.AuthDto;
import com.manusoft.dto.UserDto;
import com.manusoft.exceptions.UserException;
import com.manusoft.exceptions.UserExistingException;
import com.manusoft.exceptions.UserNotFoundException;
import com.manusoft.model.UserModel;
import com.manusoft.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userrepo;

    /**
     * Retrieves the list of user details.
     *
     * @return List of UserDto objects
     */
    @Override
    public List<UserDto> getUserDetails() {
        log.info("Inside get user details service started..");
        List<UserModel> model = null;
        List<UserDto> dto = null;
        try {
            model = userrepo.findAll();
            if (!model.isEmpty()) {
                dto = model.stream().map(this::getUserDto).toList();
            }
        } catch (Exception e) {
            log.error("error while getting user details ", e);
        }
        return dto;
    }

    /**
     * Retrieves user details by the provided ID.
     *
     * @param id The ID of the user to retrieve
     * @return The UserDto object corresponding to the ID
     * @throws IllegalArgumentException if the ID is null or if the user is not found
     */
    @Override
    public UserDto getUserById(UUID id) {
        log.info("Inside getuser Details by Id service started..");
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("User id cannot be null");
        }
        UserDto dto = null;
        try {
            Optional<UserModel> model = userrepo.findById(id);
            if (model.isPresent()) {
                dto = getUserDto(model.get());
            } else {
                throw new UserNotFoundException("User not found with id " + id);
            }
        } catch (Exception e) {
            log.error("error while getting user details By Id", e);
        }
        return dto;
    }

    /**
     * Saves user details.
     *
     * @param userDto The user data transfer object to be saved
     * @return The saved user data transfer object
     */
    @Override
    public UserDto saveUserDetails(UserDto userDto) {
        log.info("Inside save user details service started..");
        UserDto dto = null;
        UserModel model = null;
        if (Objects.nonNull(userDto)) {
            model = findByUsernameIgnoreCase(userDto.getUserName());
            if (Objects.nonNull(model)) {
                throw new UserExistingException("User already exists with username or email id " + userDto.getUserName());
            }
        }
        try {
            userDto.setUpdatedDate(LocalDateTime.now());
            userDto.setCreatedDate(LocalDateTime.now());
            userDto.setPasswordChange(Boolean.FALSE);
            userDto.setEnableEmailUpdates(Boolean.FALSE);
            model = getUserModel(userDto);
            model = userrepo.save(model);
            dto = getUserDto(model);
        } catch (Exception e) {
            log.error("error while saving user details {}", e.getMessage());
            throw new UserException("Error while creating the user");
        }
        return dto;
    }

    /**
     * Finds a user by username (case insensitive).
     *
     * @param userName The username to search for
     * @return The user model if found, null otherwise
     * @throws IllegalArgumentException if userName is null
     */
    public UserModel findByUsernameIgnoreCase(String userName) {
        log.debug("Inside get user details service started..");
        UserModel user = null;
        if (Objects.isNull(userName)) {
            throw new IllegalArgumentException("User name cannot be null");
        }
        try {
            user = userrepo.findByUserNameIgnoreCase(userName);
            if(Objects.isNull(user)) {
                throw new UserNotFoundException("User not found with username " + userName);
            }
        } catch (Exception e) {
            log.error("error while getting user details", e);
        }
        return user;
    }

    /**
     * Updates user details based on the provided UserDto
     *
     * @param userDto the UserDto object containing the updated user details
     * @return the updated UserDto object
     * @throws IllegalArgumentException if userDto is null or if username or email is null
     */
    @Override
    public UserDto updateUserDetails(UserDto userDto) {
        log.info("Inside update user details service started..");
        if (Objects.isNull(userDto)) {
            throw new IllegalArgumentException("User details cannot be null");
        }
        if (Objects.isNull(userDto.getUserName()) || Objects.isNull(userDto.getEmail())) {
            throw new IllegalArgumentException("User  username or email id cannot be null ");
        }
        UserModel model = null;
        UserDto dto = null;
        try {
            Optional<UserModel> model1 = userrepo.findById(userDto.getId());
            if (!model1.isPresent()) {
                throw new UserNotFoundException("User not found with id " + userDto.getId());
            }
            userDto.setCreatedDate(LocalDateTime.now());
            userDto.setUpdatedDate(LocalDateTime.now());
            userDto.setPasswordChange(Boolean.FALSE);
            userDto.setEnableEmailUpdates(Boolean.FALSE);
            model = getUserModel(userDto);
            model = userrepo.save(model);
            dto = getUserDto(model);
            System.out.println(dto);
        } catch (Exception e) {
            log.error("error while updating user details {}", e.getMessage());
            throw new UserException("Error while updating the user");
        }

        return dto;
    }

    /**
     * Deletes user details by ID.
     *
     * @param id The ID of the user to delete
     * @return The UserDto object of the deleted user
     * @throws IllegalArgumentException if the user id is null or if the user is not found
     */

    @Override
    public UserDto deleteUserDetailsById(UUID id) {
        log.info("Inside delete user details service started..");
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("User id cannot be null");
        }
        UserDto dto = null;
        try {
            Optional<UserModel> model = userrepo.findById(id);
            if (!model.isPresent()) {
                throw new UserNotFoundException("User not found with id " + id);
            }
            model = userrepo.findById(id);
            userrepo.delete(model.get());
            dto = getUserDto(model.get());
        } catch (Exception e) {
            log.error("error while getting user details", e);
        }
        return dto;
    }

    /**
     * Converts a UserModel object to a UserDto object.
     *
     * @param model the UserModel to convert
     * @return the UserDto object
     * @throws IllegalArgumentException if the model is null
     */
    public UserDto getUserDto(UserModel model) {
        UserDto dto = new UserDto();
        if (Objects.isNull(model)) {
            throw new IllegalArgumentException("User details cannot be null");
        }
        BeanUtils.copyProperties(model, dto);
        return dto;
    }

    /**
     * Creates a UserModel object based on the provided UserDto.
     *
     * @param dto the UserDto object containing user details
     * @return a UserModel object with user details copied from the UserDto
     * @throws IllegalArgumentException if the UserDto is null
     */
    public UserModel getUserModel(UserDto dto) {
        UserModel model = new UserModel();
        if (Objects.isNull(dto)) {
            throw new IllegalArgumentException("User details cannot be null");
        }
        BeanUtils.copyProperties(dto, model);
        return model;
    }

    /**
     * Retrieves user details based on the provided email id.
     *
     * @param authDto the email id of the user
     * @return the UserModel object containing user details, or null if not found
     * @throws IllegalArgumentException if userMailId is null
     */
    public UserDto getUserDetailsByMailId(AuthDto authDto) {
        UserDto dto = null;
        log.info("Inside get user details service started.."+authDto.getUsername());
        if (Objects.isNull(authDto.getUsername())) {
            throw new IllegalArgumentException("User mail id cannot be null");
        }
        UserModel model = null;
        try {
            model = userrepo.findByEmailIgnoreCase(authDto.getUsername());
            if (Objects.isNull(model)) {
               throw new UserNotFoundException("User not found with mail id {}" + model.getEmail());
            }
            dto = getUserDto(model);
        } catch (Exception e) {
            log.error("error while getting user details By mail id {}", e.getMessage());
        }
        return dto;
    }
}
