package com.social.user;

import com.social.user.entities.Group;
import com.social.user.entities.User;
import com.social.user.proxies.GroupServiceProxy;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            modelMapper.map(user, userDTO);
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }

    public UserDTO getUser(int id) {
        Optional<User> userOptional = userRepository.findById(id);
        log.info("Retrieving user id#" + id + " from database");
        if (userOptional.isPresent()) {
            log.info("User id#" + id + "found in database");
            ModelMapper modelMapper = new ModelMapper();
            UserDTO userDTO = new UserDTO();
            modelMapper.map(userOptional.get(), userDTO);
            return userDTO;
        } else {
            log.error("User id#" + id + " not found in database");
            return null;
        }
    }

    public boolean addUser(UserDTO userDTO) {
        //checking for username and email
        User user = new User();
        ModelMapper modelMapper = new ModelMapper();
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
            ModelMapper modelMapper = new ModelMapper();
            User user = userOptional.get();
            modelMapper.map(userDTO, user);
            userRepository.save(user);
            return true;

        } else {
            log.error("User " + userDTO.getUsername() + " not found in database");
            return false;
        }
    }

    public void deleteUser(int id) {
        log.info("Deleting user id#" + id);
        userRepository.deleteById(id);
    }

    public void ActivateOrDeactivateUser(int id, boolean activate) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            groupServiceProxy.activateOrDeactivateGroupsOfUser(user.getUserId(), activate);
            user.setActive(activate);
            userRepository.save(user);

        } else {
            log.error("User not found");
        }

    }

    public List<Group> getUsersGroup(int id) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return groupServiceProxy.getUserGroups(user.getUserId());

        } else {
            log.error("User not found");
            return null;
        }
    }

    public void followOrUnfollowUser(int userId, int userToFollowId, boolean follow) {
        User user = userRepository.getOne(userId);
        User userToFollow = userRepository.getOne(userToFollowId);

        List<User> following = user.getFollowing();
        if (user.getUserId() != userToFollowId) {
            if (follow) {
                following.add(userToFollow);
            } else {
                following.remove(userToFollow);
            }
            user.setFollowing(following);
            userRepository.save(user);
        }
        log.warn("A user cannot follow themselves");
    }

    public List<UserDTO> searchForUser(String query) {
        String[] keywords = query.split(" ");

        List<User> users = userRepository.findAllByFirstName(keywords[0]);

        if (keywords.length == 2) {
            users.retainAll(userRepository.findAllByLastName(keywords[1]));
        } else if (keywords.length >= 3) {
            users.retainAll(userRepository.findAllByMiddleName(keywords[1]));
            users.retainAll(userRepository.findAllByLastName(keywords[2]));
        }

        ModelMapper modelMapper = new ModelMapper();
        List<UserDTO> userDTOS = new ArrayList<>();

        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            modelMapper.map(user, userDTO);
            userDTOS.add(userDTO);
        }
        return userDTOS;
    }

    public List<UserDTO> getUserFollowing(int userId) {
        User user = userRepository.getOne(userId);
        List<User> following = user.getFollowing();
        List<UserDTO> followingDTOs = new ArrayList<>();

        ModelMapper modelMapper = new ModelMapper();

        for (User userFollowing : following) {
            UserDTO userDTO = new UserDTO();
            modelMapper.map(userFollowing, userDTO);
            followingDTOs.add(userDTO);
        }

        return followingDTOs;
    }
}
