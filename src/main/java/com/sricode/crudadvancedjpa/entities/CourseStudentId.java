package com.sricode.crudadvancedjpa.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseStudentId implements Serializable {
    private Integer courseId;
    private Integer studentId;
}
