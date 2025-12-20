package com.gearops.serices;

import com.gearops.entities.UserAddress;
import com.gearops.repositories.UserAddressRepository;
import com.gearops.repositories.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserAddressService {

    @Autowired
    UserAddressRepository userAddressRepository;

    @Autowired
    UserProfileRepository userProfileRepository;

    /**
     * Retrieves all user addresss from the system.
     *
     * @return a list of {@link UserAddress} instances; the list may be empty if no addresss exist
     */
    public List<UserAddress> getAllUserAddresses() {
        return userAddressRepository.findAll();
    }

    /**
     * Creates a new user address in the database.
     *
     * @param userAddress the user address to create. Must not be null, must not have an ID (new entity),
     *                    and must reference a valid existing UserProfile.
     *
     * @return a success message containing the generated ID of the created user address
     *
     * @throws IllegalArgumentException if:
     * - userAddress is null
     * - userAddress already has an ID (not a new entity)
     * - userProfile is null or does not exist in database
     *
     */
    public String createUserAddress(UserAddress userAddress) {
        // 1. Validate input
        if (userAddress == null) {
            throw new IllegalArgumentException("UserAddress cannot be null");
        }

        // 2. Check if already exists
        if (userAddress.getId() != null) {
            throw new IllegalArgumentException("New user address cannot have an ID");
        }

        // 3. Validate UserProfile relationship (matches your entity)
        if (userAddress.getUserProfile() == null) {
            throw new IllegalArgumentException("UserProfile reference is required");
        }

        // 4. Verify UserProfile exists in database
        if (!userProfileRepository.existsById(userAddress.getUserProfile().getId())) {
            throw new IllegalArgumentException("UserProfile with ID " +
                    userAddress.getUserProfile().getId() + " does not exist");
        }

        // 5. Save and return generated ID
        UserAddress savedAddress = userAddressRepository.save(userAddress);

        return "User address created successfully with ID: " + savedAddress.getId();
    }

    /**
     * Retrieves a specific user address by its unique identifier.
     *
     * @param userAddressId the ID of the user address to retrieve; must not be {@code null}
     * @return the {@link UserAddress} corresponding to the given ID
     * @throws RuntimeException if no user address exists with the given ID
     */
    public UserAddress getUserAddress(Long userAddressId) {
        return userAddressRepository.findById(userAddressId).orElseThrow(() -> new RuntimeException("User Address not found"));
    }

    /**
     * Updates an existing user address with new data.
     * <p>
     * This method first checks whether a address with the given ID exists.
     * If it does, the provided {@code UserAddress} is saved; otherwise, an exception is thrown.
     * </p>
     *
     * @param userAddressId      the ID of the user address to update; must not be {@code null}
     * @param userAddress the updated user address data; must not be {@code null}
     * @return a confirmation message indicating that the address was updated
     * @throws RuntimeException if no user address exists with the given ID
     */
    public String editUserAddress(Long userAddressId, UserAddress userAddress) {
        boolean isUserAddressExist = userAddressRepository.existsById(userAddressId);

        if (!isUserAddressExist) {
            throw new RuntimeException("User Address not found");
        }

        UserAddress updatedUserAddress = userAddressRepository.save(userAddress);
        return "User Address got updated for user Id :" + updatedUserAddress.getId();
    }

    /**
     * Removes an existing user address from the system.
     * <p>
     * If no address exists with the given ID, an exception is thrown.
     * </p>
     *
     * @param userAddressId the ID of the user address to remove; must not be {@code null}
     * @return a confirmation message indicating that the address was removed
     * @throws RuntimeException if no user address exists with the given ID
     */
    public String removeUserAddress(Long userAddressId) {

        UserAddress existingUserAddress = userAddressRepository.findById(userAddressId).orElseThrow(() -> new RuntimeException("User Address not found"));

        userAddressRepository.delete(existingUserAddress);
        return "User Address got updated for user Id :" + userAddressId;
    }

}
