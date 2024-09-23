package com.sricode.crudadvancedjpa.repositories;

import com.sricode.crudadvancedjpa.entities.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InstructorRepository extends JpaRepository<Instructor, Integer> {
    @Query(value = "SELECT i FROM Instructor i JOIN FETCH i.courses where i.id = :data")
    Optional<Instructor> findByIdReturnCourse(@Param("data")Integer id);
}
