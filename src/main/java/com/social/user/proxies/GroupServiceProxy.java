package com.social.user.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;



@FeignClient(name = "GroupService", url = "http://localhost:8083")
@RequestMapping(value = "/groups")
public interface GroupServiceProxy {

    @PatchMapping(value = "/all/user/{userId}/activate/{activate}")
    void activateOrDeactivateGroupsOfUser(
            @PathVariable int userId,
            @PathVariable boolean activate,
            @RequestHeader(value = "X-HTTP-Method-Override", defaultValue = "PATCH") String xHttpMethodOverride);

}
