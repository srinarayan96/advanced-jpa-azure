package com.sricode.crudadvancedjpa.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseDetails{
    private Integer id;
    private String title;
    private Integer instructorId;
}