package com.sricode.crudadvancedjpa.repositories;

import com.sricode.crudadvancedjpa.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    List<Course> findByInstructorId(Integer instructorId);
    Optional<Course> findByTitle(String title);
    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.reviews where c.id = :data")
    Optional<Course> findCourseWithReviews(@Param("data") Integer id);
    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.reviews")
    List<Course> findAllCoursesWithReviews();
    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.students where c.id = :data")
    Optional<Course> findCourseWithStudents(@Param("data") Integer id);
    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.students")
    List<Course> findAllCourseWithStudents();
}
