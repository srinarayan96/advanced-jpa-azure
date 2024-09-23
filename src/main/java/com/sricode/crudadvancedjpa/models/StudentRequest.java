package com.sricode.crudadvancedjpa.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String courseTitle;
}
