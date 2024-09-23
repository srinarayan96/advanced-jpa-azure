package com.sricode.crudadvancedjpa.controllers;

import com.sricode.crudadvancedjpa.models.CourseRequest;
import com.sricode.crudadvancedjpa.models.CourseResponse;
import com.sricode.crudadvancedjpa.models.CourseStudentRequest;
import com.sricode.crudadvancedjpa.services.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    @GetMapping
    public List<CourseResponse> getCourses(@RequestParam(value = "id", required = false) Integer id){
        return courseService.getCourses(id);
    }
    @PostMapping
    public CourseRequest addCourse(@Valid @RequestBody CourseRequest courseRequest){
        return courseService.saveCourse(courseRequest);
    }
    @PutMapping("/{courseTitle}")
    public CourseRequest updateCourse(@Valid @RequestBody CourseRequest courseRequest,
                                      @PathVariable("courseTitle") String courseTitle){
        return courseService.updateCourseInstructor(courseRequest, courseTitle);
    }

    @DeleteMapping("/{courseTitle}")
    public CourseRequest deleteCourse(@PathVariable("courseTitle") String courseTitle){
        return courseService.deleteCourse(courseTitle);
    }

    @PostMapping("/student")
    public CourseStudentRequest addCourseStudent(@RequestBody CourseStudentRequest courseStudentRequest){
        return courseService.addCourseStudent(courseStudentRequest);
    }
}
