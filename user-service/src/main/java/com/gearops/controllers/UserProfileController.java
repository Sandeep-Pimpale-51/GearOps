package com.gearops.controllers;

import com.gearops.entities.UserProfile;
import com.gearops.serices.UserProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userProfile")
public class UserProfileController {

    @Autowired
    UserProfileService userProfileService;

    /**
     * Retrieves all user profiles.
     *
     * @return {@link ResponseEntity} containing a list of all {@link UserProfile} instances
     * and HTTP status 200 (OK) if the operation is successful
     */
    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserProfile>> getAllUsers() {

        List<UserProfile> allUsers = userProfileService.getAllUserprofiles();

        return ResponseEntity.ok(allUsers);

    }

    /**
     * Creates a new user profile via REST API endpoint.
     *
     * @param userProfile the user profile data to create. Must be valid as per Bean Validation annotations
     *                    (e.g., @NotNull, @Email, @Size constraints on the entity fields).
     *
     * @return ResponseEntity containing HTTP 200 OK with success message including the generated user ID
     *
     *
     *
     * @see UserProfileService#createUserProfile(UserProfile)
     */

    @PostMapping("/createUser")
    public ResponseEntity<String> createProfile(@Valid @RequestBody UserProfile userProfile) {
        String result = userProfileService.createUserProfile(userProfile);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a specific user profile by its identifier.
     *
     * @param id the unique identifier of the user profile to retrieve; must not be {@code null}
     * @return {@link ResponseEntity} containing the requested {@link UserProfile}
     * and HTTP status 200 (OK) if found
     * @throws RuntimeException if no user profile exists with the given {@code id}
     */
    @GetMapping("/getUser/{id}")
    public ResponseEntity<UserProfile> getUserProfile(@PathVariable Long id) {

        UserProfile userProfile = userProfileService.getUserProfile(id);

        return ResponseEntity.ok(userProfile);
    }

    /**
     * Updates an existing user profile.
     * <p>
     * The profile identified by {@code id} is updated with the data provided in {@code userProfile}.
     * An exception is thrown if the user profile does not exist.
     * </p>
     *
     * @param id          the unique identifier of the user profile to update; must not be {@code null}
     * @param userProfile the updated user profile data; must not be {@code null}
     * @return {@link ResponseEntity} containing a confirmation message and HTTP status 200 (OK)
     * @throws RuntimeException if no user profile exists with the given {@code id}
     */
    @PutMapping("/editUser/{id}")
    public ResponseEntity<String> editUserProfile(@PathVariable Long id, @RequestBody UserProfile userProfile) {

        String resultMessage = userProfileService.editUserProfile(id, userProfile);

        return ResponseEntity.ok(resultMessage);
    }

    /**
     * Removes an existing user profile.
     * <p>
     * The profile identified by {@code id} is deleted. An exception is thrown if
     * the user profile does not exist.
     * </p>
     *
     * @param id the unique identifier of the user profile to remove; must not be {@code null}
     * @return {@link ResponseEntity} containing a confirmation message and HTTP status 200 (OK)
     * @throws RuntimeException if no user profile exists with the given {@code id}
     */
    @DeleteMapping("/removeUserProfile/{id}")
    public ResponseEntity<String> removeUserProfile(@PathVariable Long id) {

        String resultMessage = userProfileService.removeUserProfile(id);

        return ResponseEntity.ok(resultMessage);

    }
}
