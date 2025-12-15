package com.gearops.repositories;

import com.gearops.entities.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    // add custom query methods here, e.g.:
    // Optional<UserProfile> findByEmail(String email);
}
