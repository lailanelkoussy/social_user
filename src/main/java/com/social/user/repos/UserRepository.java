package com.social.user.repos;

import com.social.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    long countByEmailOrUsername(String email, String username);

    List<User> findAllByFollowing_UserId(int id);

    List<User> findAllByFirstName(String firstName);

    List<User> findAllByFirstNameAndLastName(String firstName, String lastName);

    List<User> findAllByFirstNameAndMiddleNameAndLastName(String firstName, String middleName, String lastName);

    List<User> findAllByActive(boolean isActive);

    Optional<User> findByUsername(String username);

}
