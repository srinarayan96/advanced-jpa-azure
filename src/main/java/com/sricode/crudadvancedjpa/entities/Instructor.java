package com.sricode.crudadvancedjpa.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "instructor")
@Data
@ToString
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(unique = true, name = "email")
    private String email;
   /* @Column(name = "instructor_detail_id")
    private Integer detailsId;*/
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "instructor_detail_id" )
    @ToString.Exclude
    private InstructorDetails instructorDetails;
    @OneToMany(mappedBy = "instructor")
    @ToString.Exclude
    private List<Course> courses;

    public void addCourse(Course course){
        if(this.courses == null){
            this.courses = new ArrayList<>();
        }
        course.setInstructor(this);
        this.courses.add(course);
    }
}
