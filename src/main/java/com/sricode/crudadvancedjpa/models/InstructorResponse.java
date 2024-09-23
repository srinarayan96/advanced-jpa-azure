package com.sricode.crudadvancedjpa.models;

import lombok.Data;

@Data
public class InstructorResponse {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String youtubeChannel;
    private String hobby;
}
