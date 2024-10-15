package com.manusoft.controller;

import com.manusoft.dto.AuthDto;
import com.manusoft.dto.UserDto;
import com.manusoft.services.UserService;
import com.manusoft.util.ApiConstants;
import com.manusoft.util.AuthUtils;
import com.manusoft.util.CoockeUtils;
import com.manusoft.util.JWTUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(ApiConstants.User_Login_URL)
public class UserController {
    @Autowired
    private UserService service;

    /**
     * Retrieves user details by ID.
     *
     * @param id The ID of the user
     * @return ResponseEntity with the user details and HTTP status
     */
    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> getUserDetailsById(@PathVariable String id) {
        UserDto userDetails = service.getUserById(UUID.fromString(id));
        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }

    /**
     * Retrieves all user details.
     *
     * @return ResponseEntity with a list of UserDto containing user details
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAullUserDetails() {
        List<UserDto> userDetails = service.getUserDetails();
        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }

    /**
     * Endpoint to save user details.
     *
     * @param id      The optional ID of the user.
     * @param userDto The user data to save.
     * @return ResponseEntity with the saved user details.
     */
    @PostMapping("/save/user")
    public ResponseEntity<UserDto> saveUserDetails(@RequestParam(required = false) String id,
                                                   @RequestBody UserDto userDto) {
        UserDto saveUserDetails = service.saveUserDetails(userDto);
        return new ResponseEntity<>(saveUserDetails, HttpStatus.CREATED);
    }

    /**
     * Update user details.
     *
     * @param userDto The user data to be updated
     * @return ResponseEntity with the updated UserDto and HTTP status code ACCEPTED
     */
    @PutMapping("/update/user")
    public ResponseEntity<UserDto> updateUserDetails(@RequestBody UserDto userDto) {
        UserDto updateUserDetails = service.updateUserDetails(userDto);
        return new ResponseEntity<>(updateUserDetails, HttpStatus.ACCEPTED);
    }

    /**
     * Delete user details by ID.
     *
     * @param id The ID of the user to delete
     * @return ResponseEntity with the deleted user details and HTTP status ACCEPTED
     */
    @DeleteMapping("/delete/user/{id}")
    public ResponseEntity<UserDto> deleteUserDetailsById(@PathVariable String id) {
        UserDto deleteUserDetails = service.deleteUserDetailsById(UUID.fromString(id));
        return new ResponseEntity<>(deleteUserDetails, HttpStatus.ACCEPTED);
    }
    /**
     * Generates a token for authentication based on the provided AuthDto.
     *
     * @param authDto The authentication details
     * @param response The HTTP response object
     * @return ResponseEntity<UserDto> The user details along with the generated token
     */
    @PostMapping("/auth/login")
    public ResponseEntity<UserDto> generateToken(@RequestBody AuthDto authDto, HttpServletResponse response) {
        UserDto token = service.getUserDetailsByMailId(authDto);
        CoockeUtils.addCookie(JWTUtil.USER_ID_COOKIE_NAME, JWTUtil.generateToken(token.getEmail()),response);
        System.out.println(token);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
    /**
     * Handles the logout action for the user.
     * Removes user-related cookies and authentication.
     * @param response HTTPServletResponse to manipulate cookies
     * @return ResponseEntity with a success message and HTTP status
     */
    @GetMapping("/auth/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        CoockeUtils.removeCookie(JWTUtil.USER_ID_COOKIE_NAME, response);
        AuthUtils.removeAuthentication();
        return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
    }
}
