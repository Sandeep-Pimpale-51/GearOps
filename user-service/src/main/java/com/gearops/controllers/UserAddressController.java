package com.gearops.controllers;

import com.gearops.entities.UserAddress;
import com.gearops.serices.UserAddressService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userAddress")
public class UserAddressController {


        @Autowired
        UserAddressService userAddressService;

        /**
         * Retrieves all user addresses.
         *
         * @return {@link ResponseEntity} containing a list of all {@link UserAddress} instances
         * and HTTP status 200 (OK) if the operation is successful
         */
        @GetMapping("/getAllUsers")
        public ResponseEntity<List<UserAddress>> getAllUserAddresses() {

            List<UserAddress> allUsers = userAddressService.getAllUserAddresses();

            return ResponseEntity.ok(allUsers);

        }

    /**
     * Creates a new user address via REST API endpoint.
     */
    @PostMapping("/createUserAddress")
    public ResponseEntity<String> createAddress(@Valid @RequestBody UserAddress userAddress) {
        String result = userAddressService.createUserAddress(userAddress);
        return ResponseEntity.ok(result);
    }

    /**
         * Retrieves a specific user address by its identifier.
         *
         * @param id the unique identifier of the user address to retrieve; must not be {@code null}
         * @return {@link ResponseEntity} containing the requested {@link UserAddress}
         * and HTTP status 200 (OK) if found
         * @throws RuntimeException if no user address exists with the given {@code id}
         */
        @GetMapping("/getUser/{id}")
        public ResponseEntity<UserAddress> getUserAddress(@PathVariable Long id) {

            UserAddress userAddress = userAddressService.getUserAddress(id);

            return ResponseEntity.ok(userAddress);
        }

        /**
         * Updates an existing user address.
         * <p>
         * The profile identified by {@code id} is updated with the data provided in {@code userAddress}.
         * An exception is thrown if the user address does not exist.
         * </p>
         *
         * @param id          the unique identifier of the user address to update; must not be {@code null}
         * @param userAddress the updated user address data; must not be {@code null}
         * @return {@link ResponseEntity} containing a confirmation message and HTTP status 200 (OK)
         * @throws RuntimeException if no user address exists with the given {@code id}
         */
        @PutMapping("/editUser/{id}")
        public ResponseEntity<String> editUserAddress(@PathVariable Long id, @RequestBody UserAddress userAddress) {

            String resultMessage = userAddressService.editUserAddress(id, userAddress);

            return ResponseEntity.ok(resultMessage);
        }

        /**
         * Removes an existing user address.
         * <p>
         * The profile identified by {@code id} is deleted. An exception is thrown if
         * the user address does not exist.
         * </p>
         *
         * @param id the unique identifier of the user address to remove; must not be {@code null}
         * @return {@link ResponseEntity} containing a confirmation message and HTTP status 200 (OK)
         * @throws RuntimeException if no user address exists with the given {@code id}
         */
        @DeleteMapping("/removeUserAddress/{id}")
        public ResponseEntity<String> removeUserAddress(@PathVariable Long id) {

            String resultMessage = userAddressService.removeUserAddress(id);

            return ResponseEntity.ok(resultMessage);

        }
    

}
