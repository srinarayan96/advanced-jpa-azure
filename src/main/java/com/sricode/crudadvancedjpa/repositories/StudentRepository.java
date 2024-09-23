package com.sricode.crudadvancedjpa.repositories;

import com.sricode.crudadvancedjpa.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Optional<Student> findByEmail(String email);
    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.courses WHERE email=:data")
    Optional<Student> findByEmailWithCourses(@Param("data") String email);

    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.courses")
    List<Student> findAllByEmailWithCourses();
    //List<Student> findByCourseId(Integer courseId);
}
