package com.social.user.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.social.user.dtos.GroupDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestDTO { //todo this is a useless class

    int requestId;

    int userId;

    @JsonIgnore
    GroupDTO groupDTO;
}

