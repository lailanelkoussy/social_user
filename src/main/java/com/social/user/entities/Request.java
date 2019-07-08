package com.social.user.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.social.user.dtos.GroupDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Request {//todo what is this?

    int requestId;

    int userId;

    @JsonIgnore
    GroupDTO groupDTO;
}

