package com.sricode.crudadvancedjpa.repositories;

import com.sricode.crudadvancedjpa.entities.CourseStudent;
import com.sricode.crudadvancedjpa.entities.CourseStudentId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseStudentRepository extends JpaRepository<CourseStudent, CourseStudentId> {
    List<CourseStudent> findByCourseId(Integer courseId);
}
