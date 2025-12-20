package com.gearops.serices;

import com.gearops.entities.UserProfile;
import com.gearops.repositories.UserProfileRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserProfileService {

    @Autowired
    UserProfileRepository userProfileRepository;

    /**
     * Retrieves all user profiles from the system.
     *
     * @return a list of {@link UserProfile} instances; the list may be empty if no profiles exist
     */
    public List<UserProfile> getAllUserprofiles() {
        return userProfileRepository.findAll();
    }

    /**
     * Retrieves a specific user profile by its unique identifier.
     *
     * @param userId the ID of the user profile to retrieve; must not be {@code null}
     * @return the {@link UserProfile} corresponding to the given ID
     * @throws RuntimeException if no user profile exists with the given ID
     */
    public UserProfile getUserProfile(Long userId) {
        return userProfileRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * Creates a new user profile in the database.
     *
     * @param userProfile the user profile to create. Must not be null, must not have an ID (new entity),
     *                    and must have a valid non-empty email address.
     *
     * @return a success message containing the generated ID of the created user profile
     *
     * @throws IllegalArgumentException if:
     * - userProfile is null
     * - userProfile already has an ID (not a new entity)
     * - email is null or empty
     * - a user with the same email already exists
     *
     * @see UserProfileRepository#existsByEmail(String)
     */
    public String createUserProfile(UserProfile userProfile) {
        // 1. Validate input
        if (userProfile == null) {
            throw new IllegalArgumentException("UserProfile cannot be null");
        }

        // 2. Check if user already exists
        if (userProfile.getId() != null) {
            throw new IllegalArgumentException("New user profile cannot have an ID");
        }

        // 3. Validate required fields (adjust based on your entity)
        if (userProfile.getEmail() == null || userProfile.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }

        // 4. Check for duplicate email (business rule)
        if (userProfileRepository.existsByEmail(userProfile.getEmail())) {
            throw new IllegalArgumentException("User with email already exists: " + userProfile.getEmail());
        }

        // 5. Save and return generated ID
        UserProfile savedUser = userProfileRepository.save(userProfile);

        return "User profile created successfully with ID: " + savedUser.getId();
    }

    /**
     * Updates an existing user profile with new data.
     * <p>
     * This method first checks whether a profile with the given ID exists.
     * If it does, the provided {@code userProfile} is saved; otherwise, an exception is thrown.
     * </p>
     *
     * @param userId      the ID of the user profile to update; must not be {@code null}
     * @param userProfile the updated user profile data; must not be {@code null}
     * @return a confirmation message indicating that the profile was updated
     * @throws RuntimeException if no user profile exists with the given ID
     */
    public String editUserProfile(Long userId, UserProfile userProfile) {
        boolean isUserExist = userProfileRepository.existsById(userId);

        if (!isUserExist) {
            throw new RuntimeException("User not found");
        }

        UserProfile updatedUser = userProfileRepository.save(userProfile);
        return "User Profile got updated for user Id :" + updatedUser.getId();
    }

    /**
     * Removes an existing user profile from the system.
     * <p>
     * If no profile exists with the given ID, an exception is thrown.
     * </p>
     *
     * @param userId the ID of the user profile to remove; must not be {@code null}
     * @return a confirmation message indicating that the profile was removed
     * @throws RuntimeException if no user profile exists with the given ID
     */
    public String removeUserProfile(Long userId) {

        UserProfile existingUser = userProfileRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        userProfileRepository.delete(existingUser);
        return "User Profile got updated for user Id :" + userId;
    }

}
