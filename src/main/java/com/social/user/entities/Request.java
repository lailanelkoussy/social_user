package com.social.user.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
public class Request {

    int requestId;

    int userId;

    Group group;
}

