package com.sricode.crudadvancedjpa.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "course_student")
@IdClass(CourseStudentId.class)
public class CourseStudent {
    @Id
    @Column(name = "course_id")
    private Integer courseId;
    @Id
    @Column(name = "student_id")
    private Integer studentId;
}
