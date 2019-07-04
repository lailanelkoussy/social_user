package com.social.user;

import com.social.user.entities.Group;
import com.social.user.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    //Users
    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/{username}")
    public UserDTO getUser(@PathVariable String username) {
        return userService.getUser(username);
    }

    @GetMapping(value = "/{username}/groups")
    public List<Group> getUserGroups(@PathVariable String username) {
        return userService.getUsersGroup(username);
    }

    @GetMapping(value = "/search/{query}")
    public List<User> searchForUser(@PathVariable String query){
        return userService.searchForUser(query);
    }

    @PostMapping
    public ResponseEntity<Object> createNewUser(@RequestBody @Valid UserDTO userDTO) {
        return new ResponseEntity<>(userService.addUser(userDTO) ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping
    public ResponseEntity<Object> updateUser(@RequestBody @Valid UserDTO userDTO) {
        return new ResponseEntity<>(userService.updateUser(userDTO) ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE);
    }

    @DeleteMapping(value = "/{username}")
    public void deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
    }

    @PatchMapping(value = "/{username}")
    public void ActivateOrDeactivateUser(@PathVariable String username, @RequestBody boolean activate) {
        userService.ActivateOrDeactivateUser(username, activate);

    }

    @PatchMapping(value = "/{id}/follow/{userToFollowId}/{follow}")
    public void followOrUnfollowUser(@PathVariable int id, @PathVariable int userToFollowId, @PathVariable boolean follow){
        userService.followOrUnfollowUser(id, userToFollowId, follow);
    }

}
