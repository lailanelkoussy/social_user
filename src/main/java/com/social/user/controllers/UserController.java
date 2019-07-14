package com.social.user.controllers;

import com.social.user.dtos.UserDTO;
import com.social.user.services.UserService;
import com.social.user.dtos.GroupDTO;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
@Api(value = "User Management Service")
public class UserController {

    @Autowired
    private UserService userService;

    //Users
    @GetMapping
    @ApiOperation(value = "Get all users", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    public List<UserDTO> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get user by id", response = UserDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved object"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    public UserDTO getUser(
            @ApiParam(value = "Id of user", required = true) @PathVariable int id) {
        return userService.getUser(id);
    }

    @GetMapping(value = "/{id}/groups", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get groups of a specific user", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    public List<GroupDTO> getUserGroups(
            @ApiParam(value = "Id of user", required = true) @PathVariable int id) { //todo this api shouldn't be in this service!
        return userService.getUsersGroup(id);
    }

    @GetMapping(value = "/search/{query}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Search for user using query", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    public List<UserDTO> searchForUser(
            @ApiParam(value = "Query to search", required = true) @PathVariable String query) {
        return userService.searchForUser(query);
    }

    @GetMapping(value = "/{id}/following", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get users followed by a specific user", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    public List<UserDTO> getUserFollowing(
            @ApiParam(value = "Id of user", required = true) @PathVariable int id) {
        return userService.getUserFollowing(id);
    }

    @PostMapping
    @ApiOperation(value = "Create new user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added object"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),})
    public ResponseEntity<Object> createNewUser(
            @ApiParam(value = "User object to create", required = true) @RequestBody @Valid UserDTO userDTO) {
        return new ResponseEntity<>(userService.addUser(userDTO) ? HttpStatus.CREATED : HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping
    @ApiOperation(value = "Update user's data")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Successfully updated object"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    public ResponseEntity<Object> updateUser(
            @ApiParam(value = "Updated User object", required = true) @RequestBody @Valid UserDTO userDTO) {
        return new ResponseEntity<>(userService.updateUser(userDTO) ? HttpStatus.ACCEPTED : HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Delete user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted object"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    public void deleteUser(
            @ApiParam(value = "Id of user", required = true) @PathVariable int id) {
        userService.deleteUser(id);
    }

    @DeleteMapping(value = "/{id}/following")
    @ApiOperation(value = "Unfollow users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully unfollowed users"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    public void unfollowUsers(@PathVariable int id, @RequestBody List<Integer> usersToUnfollow) {
        userService.followOrUnfollowUsers(id, usersToUnfollow, false);
    }

    @PatchMapping(value = "/{id}")
    @ApiOperation(value = "Follow a user, activate or deactivate user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated object"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    public void patchMapping(//todo this name is not descriptive
            @ApiParam(value = "Id of user", required = true) @PathVariable int id,
            @ApiParam(value = "User object with relevant fields edited", required = true) @RequestBody UserDTO user) {
        userService.patchService(id, user);
    }

}
