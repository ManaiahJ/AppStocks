package com.manusoft.repository;

import com.manusoft.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<UserModel, UUID> {
    /**
     * Find a user by their username ignoring case
     *
     * @param userName the username to search for
     * @return the UserModel object if found, null otherwise
     */
    UserModel findByUserNameIgnoreCase(String userName);

    /**
     * Finds a user by email ignoring case sensitivity.
     *
     * @param email the email to search for
     * @return the user model found, or null if not found
     */
    UserModel findByEmailIgnoreCase(String email);

}
