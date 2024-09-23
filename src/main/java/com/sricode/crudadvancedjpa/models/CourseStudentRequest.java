package com.sricode.crudadvancedjpa.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseStudentRequest {
    @NotBlank
    private String courseTitle;
    @NotNull
    private Integer instructorId;
    private String firstName;
    private String lastName;
    @NotBlank
    @NotNull
    private String email;
}
