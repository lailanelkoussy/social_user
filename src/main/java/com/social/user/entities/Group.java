package com.social.user.entities;

import lombok.Data;

import javax.persistence.Id;

@Data
public class Group {

    int id;

    String name;

    String description;

    int creatorId;


}
