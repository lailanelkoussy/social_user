package com.social.user.controllers;

import com.social.user.dtos.CreateUserDTO;
import com.social.user.dtos.PatchDTO;
import com.social.user.dtos.UnfollowDTO;
import com.social.user.dtos.UserDTO;
import com.social.user.services.UserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.InvalidClassException;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
@Api(value = "User Management Service")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Get all users", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.getAllUsers();
    }

    @ApiOperation(value = "Get user by id", response = UserDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved object"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO getUser(
            @ApiParam(value = "Id of user", required = true) @PathVariable int id) {
        return userService.getUser(id);
    }

    @ApiOperation(value = "Search for user using query", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping(value = "/search/{query}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> searchForUser(
            @ApiParam(value = "Query to search", required = true) @PathVariable String query) {
        return userService.searchForUser(query);
    }

    @ApiOperation(value = "Get users followed by a specific user", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @GetMapping(value = "/{id}/following", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> getUserFollowing(
            @ApiParam(value = "Id of user", required = true) @PathVariable int id) {
        return userService.getUserFollowing(id);
    }

    @ApiOperation(value = "Create new user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added object"),
            @ApiResponse(code = 406, message = "Invalid user object "),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),})
    @PostMapping
    public ResponseEntity<Object> createNewUser(
            @ApiParam(value = "User object to create", required = true) @RequestBody @Valid CreateUserDTO userDTO) throws InvalidClassException {
        userService.addUser(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update user's data")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Successfully updated object"),
            @ApiResponse(code = 406, message = "Invalid user object"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateUser(
            @ApiParam(value = "Id of user to update", required = true) @PathVariable int id,
            @ApiParam(value = "Updated User object", required = true) @RequestBody @Valid UserDTO userDTO) throws InvalidClassException {
        userService.updateUser(id, userDTO);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "Delete user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted object"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @DeleteMapping(value = "/{id}")
    public void deleteUser(
            @ApiParam(value = "Id of user", required = true) @PathVariable int id) {
        userService.deleteUser(id);
    }

    @ApiOperation(value = "Unfollow users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully unfollowed users"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @DeleteMapping(value = "/{id}/following")
    public void unfollowUsers(@PathVariable int id, @RequestBody UnfollowDTO usersToUnfollow) {
        userService.followOrUnfollowUsers(id, usersToUnfollow.getUnfollow(), false);
    }

    @ApiOperation(value = "Follow a user, activate or deactivate user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated object"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    @PatchMapping(value = "/{id}")
    public void followUserOrActivateDeactivate(
            @ApiParam(value = "Id of user", required = true) @PathVariable int id,
            @ApiParam(value = "User object with relevant fields edited", required = true) @RequestBody PatchDTO patchDTO) {
        userService.patchService(id, patchDTO);
    }

}
