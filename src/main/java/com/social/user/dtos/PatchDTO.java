package com.social.user.dtos;

import lombok.Data;

import java.util.List;

@Data
public class PatchDTO {
    List<Integer> follow;
    int activate = -1;

}
