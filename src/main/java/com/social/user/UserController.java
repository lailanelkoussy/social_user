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
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    //Users
    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/{id}")
    public UserDTO getUser(@PathVariable int id) {
        return userService.getUser(id);
    }

    @GetMapping(value = "/{id}/groups")
    public List<Group> getUserGroups(@PathVariable int id) {
        return userService.getUsersGroup(id);
    }

    @GetMapping(value = "/search/{query}")
    public List<UserDTO> searchForUser(@PathVariable String query){
        return userService.searchForUser(query);
    }

    @GetMapping(value = "/{id}/following")
    public List<UserDTO> getUserFollowing(@PathVariable int id){
        return userService.getUserFollowing(id);
    }

    @PostMapping
    public ResponseEntity<Object> createNewUser(@RequestBody @Valid UserDTO userDTO) {
        return new ResponseEntity<>(userService.addUser(userDTO) ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping
    public ResponseEntity<Object> updateUser(@RequestBody @Valid UserDTO userDTO) {
        return new ResponseEntity<>(userService.updateUser(userDTO) ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }

    @PatchMapping(value = "/{id}/{activate}")
    public void ActivateOrDeactivateUser(@PathVariable int id, @PathVariable boolean activate) {
        userService.ActivateOrDeactivateUser(id, activate);

    }
    @PatchMapping(value = "/{id}/follow/{userToFollowId}/{follow}")
    public void followOrUnfollowUser(@PathVariable int id, @PathVariable int userToFollowId, @PathVariable boolean follow){
        userService.followOrUnfollowUser(id, userToFollowId, follow);
    }

}
