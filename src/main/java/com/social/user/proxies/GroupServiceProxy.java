package com.social.user.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "GroupService")
public interface GroupServiceProxy {

    @PatchMapping(value = "/groups/all/user/{userId}/activate/{activate}")
    void activateOrDeactivateGroupsOfUser(
            @PathVariable int userId,
            @PathVariable boolean activate,
            @RequestHeader(value = "X-HTTP-Method-Override", defaultValue = "PATCH") String xHttpMethodOverride);

    @DeleteMapping("/requests/all/user/{userId}")
    void deleteUsersRequests(
            @PathVariable int userId);

    @DeleteMapping("/groups/all/user/{userId}")
    void deleteUsersGroupsAndMembers(@PathVariable int userId);
}
