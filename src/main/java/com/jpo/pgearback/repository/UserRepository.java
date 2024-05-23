package com.jpo.pgearback.repository;

import com.jpo.pgearback.entity.User;
import com.jpo.pgearback.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findFirstByEmail(String email);
    User findByRole(UserRole role);
}
