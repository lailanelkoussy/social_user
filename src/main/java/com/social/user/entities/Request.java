package com.social.user.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
public class Request {//todo what is this?

    int requestId;

    int userId;

    @JsonIgnore
    Group group;
}

