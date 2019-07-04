package com.social.user.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
public class Request {

    int requestId;

    int userId;

    @JsonIgnore
    Group group;
}

