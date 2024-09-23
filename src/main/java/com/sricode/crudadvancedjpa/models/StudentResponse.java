package com.sricode.crudadvancedjpa.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class StudentResponse {
    private String firstName;
    private String lastName;
    private String email;
    private List<CourseDetails> enrolledCourses;
}

