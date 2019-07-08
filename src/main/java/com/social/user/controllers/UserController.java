package com.social.user.controllers;

import com.social.user.UserDTO;
import com.social.user.UserService;
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
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all users", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    public List<UserDTO> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/{id}", produces =  MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get user by id", response = UserDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved object"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    public UserDTO getUser(
            @ApiParam(value = "Id of user", required = true) @PathVariable int id) {
        return userService.getUser(id);
    }

    @GetMapping(value = "/{id}/groups", produces =  MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get groups of a specific user", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    public List<GroupDTO> getUserGroups(
            @ApiParam(value = "Id of user", required = true) @PathVariable int id) {
        return userService.getUsersGroup(id);
    }

    @GetMapping(value = "/search/{query}", produces =  MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Search for user using query", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    public List<UserDTO> searchForUser(
            @ApiParam(value = "Query to search", required = true) @PathVariable String query){
        return userService.searchForUser(query);
    }

    @GetMapping(value = "/{id}/following", produces =  MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get followed users for a specific user", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    public List<UserDTO> getUserFollowing(
            @ApiParam(value = "Id of user", required = true) @PathVariable int id){
        return userService.getUserFollowing(id);
    }
    @PostMapping
    @ApiOperation(value = "Create new user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added object"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"), })
    public ResponseEntity<Object> createNewUser(
            @ApiParam(value = "User object to create", required = true ) @RequestBody @Valid UserDTO userDTO) {
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

    @PatchMapping(value = "/{id}/{activate}")
    @ApiOperation(value = "Activate or deactivate a user's account")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated object"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    public void ActivateOrDeactivateUser(
            @ApiParam(value = "Id of user", required = true) @PathVariable int id,
            @ApiParam(value = "boolean value indicating whether to activate or deactivate user", required = true)
            @PathVariable boolean activate) {
        userService.ActivateOrDeactivateUser(id, activate);
    }
    @PatchMapping(value = "/{id}/follow/{userToFollowId}/{follow}")
    @ApiOperation(value = "Follow or unfollow a user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated object"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")})
    public void followOrUnfollowUser(
            @ApiParam(value = "Id of user", required = true)
            @PathVariable int id,
            @ApiParam(value = "Id of user to follow/unfollow", required = true)
            @PathVariable int userToFollowId,
            @ApiParam(value = "boolean value indicating wheather to follow or unfollow", required = true)
            @PathVariable boolean follow){
        userService.followOrUnfollowUser(id, userToFollowId, follow);
    }

}
