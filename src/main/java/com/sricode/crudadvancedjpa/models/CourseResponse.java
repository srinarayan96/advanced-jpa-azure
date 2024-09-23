package com.sricode.crudadvancedjpa.models;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class CourseResponse {
    private Integer id;
    private Integer instructorId;
    private String title;
    private List<String> comments;
    private List<String> studentsEmail;
}
