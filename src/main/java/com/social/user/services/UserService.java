package com.social.user.services;

import com.social.user.dtos.UserDTO;
import com.social.user.entities.User;
import com.social.user.proxies.GroupServiceProxy;
import com.social.user.repos.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
@Slf4j
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GroupServiceProxy groupServiceProxy;

    private ModelMapper modelMapper = new ModelMapper();

    public List<UserDTO> getAllUsers() {
        log.info("Retrieving all users");
        List<User> users = userRepository.findAll();
        return toUserDTO(users);
    }

    public UserDTO getUser(int id) {
        Optional<User> userOptional = userRepository.findById(id);
        log.info("Retrieving user id#" + id + " from database");
        if (userOptional.isPresent()) {
            log.info("User id#" + id + "found in database");
            UserDTO userDTO = new UserDTO();
            modelMapper.map(userOptional.get(), userDTO);
            return userDTO;
        } else {
            log.error("User id#" + id + " not found in database");
            throw (new EntityNotFoundException("User not found in database"));
        }
    }

    public boolean addUser(UserDTO userDTO) {

        //checking for username and email
        User user = new User();
        modelMapper.map(userDTO, user);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        if (userRepository.countByEmailOrUsername(user.getEmail(), user.getUsername()) != 0) {
            log.error("Username or email is already used");
            return false;
        } else {
            userRepository.save(user);
            log.info("User added");
            return true;
        }

    }

    public boolean updateUser(UserDTO userDTO) {
        Optional<User> userOptional = userRepository.findByUsername(userDTO.getUsername());
        if (userOptional.isPresent()) {
            log.info("User " + userDTO.getUsername() + "found in database");
            User user = userOptional.get();
            modelMapper.map(userDTO, user);
            userRepository.save(user);
            return true;

        } else {
            log.error("User " + userDTO.getUsername() + " not found in database");
            throw (new EntityNotFoundException("User " + userDTO.getUsername() + " not found in database"));
        }
    }

    public void deleteUser(int id) {
        log.info("Deleting user id#" + id);
        userRepository.deleteById(id);
    }

    public void patchService(int userId, UserDTO userDTO) {
        if (userDTO.getActivate() != -1) {
            activateOrDeactivateUser(userId, userDTO.getActivate() != 0);
        }
        if (userDTO.getFollowingIds() != null) {
            followOrUnfollowUsers(userId, userDTO.getFollowingIds(), true);
        }
    }

    public void activateOrDeactivateUser(int id, boolean activate) {
        Optional<User> userOptional = userRepository.findById(id);

        log.info("Retrieving user");
        if (userOptional.isPresent()) {
            log.info("User retrieved");
            User user = userOptional.get();
            groupServiceProxy.activateOrDeactivateGroupsOfUser(user.getUserId(), activate, "PATCH");
            user.setActive(activate);
            userRepository.save(user);

        } else {
            log.error("User not found");
        }

    }


    public void followOrUnfollowUsers(int userId, List<Integer> userToFollowIds, boolean follow) {

        Optional<User> userOptional = userRepository.findById(userId);
        log.info("Retrieving user");

        if (userOptional.isPresent()) {
            log.info("User retrieved");
            User user = userOptional.get();
            List<User> following = user.getFollowing();
            log.info("Making changes to user's following list");
            for (int userToFollowId : userToFollowIds) {
                if (user.getUserId() != userToFollowId) {
                    Optional<User> userToFollowOptional = userRepository.findById(userToFollowId);
                    if (userToFollowOptional.isPresent()) {
                        User userToFollow = userToFollowOptional.get();

                        if (follow) {
                            following.add(userToFollow);
                        } else {
                            following.remove(userToFollow);
                        }

                        user.setFollowing(following);
                        userRepository.save(user);

                    } else {
                        throw (new EntityNotFoundException("Could not find user"));
                    }
                } else {
                    log.warn("A user cannot follow themselves");
                }
            }
        } else {
            log.error("Could not find user");
            throw (new EntityNotFoundException("Could not find user"));
        }
    }

    public List<UserDTO> searchForUser(String query) {
        String[] keywords = query.split(" ");
        List<User> users;
        log.info("Searching for users");

        switch (keywords.length) {
            case 1:
                users = userRepository.findAllByFirstName(keywords[0]);
                break;
            case 2:
                users = userRepository.findAllByFirstNameAndLastName(keywords[0], keywords[1]);
                break;
            default:
                users = userRepository.findAllByFirstNameAndMiddleNameAndLastName(keywords[0], keywords[1], keywords[2]);
                break;
        }

        return toUserDTO(users);
    }

    public List<UserDTO> getUserFollowing(int userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        log.info("Retrieving user...");

        if (optionalUser.isPresent()) {
            log.info("User retrieved...");
            User user = userRepository.getOne(userId);
            List<User> following = user.getFollowing();
            return toUserDTO(following);

        } else {
            log.error("Could not find user");
            throw (new EntityNotFoundException("Could not find user"));
        }
    }

    List<UserDTO> toUserDTO(List<User> users) {

        List<UserDTO> userDTOS = new ArrayList<>();

        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            modelMapper.map(user, userDTO);
            userDTOS.add(userDTO);
        }
        return userDTOS;

    }


}
