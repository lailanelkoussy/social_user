package com.social.user.proxies;

import com.social.user.entities.Group;
import com.social.user.entities.Request;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "GroupService")
@RequestMapping(value = "/groups")
public interface GroupServiceProxy {

    //Groups

//    @GetMapping
//    public List<Group> getAllGroups();
//
//    @PostMapping
//    public ResponseEntity<Object> addNewGroup(@RequestBody Group group);
//
//    @GetMapping(value = "/{id}")
//    public Group getGroup(@PathVariable int id);
//
//    @DeleteMapping(value = "/{groupId}/{userId}")
//    public ResponseEntity<Object> deleteGroup(@PathVariable int groupId, @PathVariable int userId);
//
    @GetMapping(value = "/user/{id}/")
    public List<Group> getUserGroups(@PathVariable int id);
//
//    @PatchMapping(value = "/{id}/name")
//    public ResponseEntity<Object> renameGroup(@PathVariable int id, @RequestBody String newName);
//
//    @PatchMapping(value = "/{id}/description")
//    public ResponseEntity<Object> changeGroupDescription(@PathVariable int id, @RequestBody String newDescription);
//
//    //Members
//
//    @PatchMapping(value = "/{id}/members")
//    public ResponseEntity<Object> addNewMembersToGroup(@PathVariable int id, @RequestBody List<Integer> userIds);
//
//    @PatchMapping(value = "/{id}/members/remove")
//    public ResponseEntity<Object> removeMembersFromgroup(@PathVariable int id, int removerId, @RequestBody List<Integer> userIds);
//
//
//    //Requests
//
//    @GetMapping(value = "/{id}/requests")
//    public List<Request> viewRequests(@PathVariable int id, @RequestBody int userId);
//
//    @PatchMapping(value = "/request/{userId}/{requestId}/")
//    public ResponseEntity<Object> acceptRequest(@PathVariable int requestId, @RequestBody int userId);
//
//    @DeleteMapping(value = "/request/{userId}/{requestId}/")
//    public ResponseEntity<Object> declineRequest(@PathVariable int requestId, @RequestBody int userId);
//
//    @PostMapping(value = "/{id}/requests/{userId}")
//    public void sendRequest(@PathVariable int id, @PathVariable int userId);
//
//    //User
    @PatchMapping(value = "/user/{id}/{activate}")
    public void activateOrDeactivateGroupsOfUser(@PathVariable int userId, @PathVariable boolean activate) ;


}
