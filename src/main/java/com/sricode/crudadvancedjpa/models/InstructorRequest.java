package com.sricode.crudadvancedjpa.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InstructorRequest {
    private String firstName;
    private String lastName;
    @Email
    private String email;
    private String youtubeChannel;
    private String hobby;
    private List<String> courseTitles = new ArrayList<>();
}
