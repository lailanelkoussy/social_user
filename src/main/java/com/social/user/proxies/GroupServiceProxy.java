package com.social.user.proxies;

import com.social.user.dtos.GroupDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "GroupService", url = "http://localhost:8083")
@RequestMapping(value = "/groups")
public interface GroupServiceProxy {

    @GetMapping(value = "/user/{id}/")
    List<GroupDTO> getUserGroups(@PathVariable int id); //todo what is this?????????????????????????????????????????

    @PatchMapping(value = "/user/{userId}/{activate}")
    void activateOrDeactivateGroupsOfUser(@PathVariable int userId, @PathVariable boolean activate, @RequestHeader(value="X-HTTP-Method-Override", defaultValue="PATCH") String xHttpMethodOveride); //todo this line is too long, please make the line max length 80 chars, let the parameters be a single one on each line

}
