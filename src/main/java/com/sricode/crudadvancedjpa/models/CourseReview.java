package com.sricode.crudadvancedjpa.models;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseReview {
    private String comment;
    @NotNull
    private Integer courseId;
}
