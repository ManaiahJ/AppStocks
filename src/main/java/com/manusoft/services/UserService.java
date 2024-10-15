package com.manusoft.services;

import com.manusoft.dto.AuthDto;
import com.manusoft.dto.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    /**
     * Retrieves the details of all users in the system.
     *
     * @return a list of UserDto objects containing user details
     */
    public List<UserDto> getUserDetails();

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id The unique identifier of the user to retrieve
     * @return The UserDto object representing the user
     */
    public UserDto getUserById(UUID id);

    /**
     * Saves the user details.
     *
     * @param userDto the user data transfer object to be saved
     * @return the saved user data transfer object
     */
    public UserDto saveUserDetails(UserDto userDto);

    /**
     * Updates the user details based on the information provided in the UserDto object.
     *
     * @param userDto the UserDto object containing the updated user information
     * @return the updated UserDto object
     */
    public UserDto updateUserDetails(UserDto userDto);

    /**
     * Deletes user details based on the provided ID.
     *
     * @param id The ID of the user to delete
     * @return The DTO of the deleted user
     */
    public UserDto deleteUserDetailsById(UUID id);
    /**
     * Retrieves user details based on the email id provided in the authentication data transfer object.
     *
     * @param authDto the authentication data transfer object containing the email id
     * @return the user data transfer object with details corresponding to the provided email id
     */
    public UserDto getUserDetailsByMailId(AuthDto authDto);


}
