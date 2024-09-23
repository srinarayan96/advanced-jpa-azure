package com.sricode.crudadvancedjpa.controllers;

import com.sricode.crudadvancedjpa.models.StudentRequest;
import com.sricode.crudadvancedjpa.models.StudentResponse;
import com.sricode.crudadvancedjpa.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;
    @PostMapping
    public StudentRequest createStudent(@RequestBody StudentRequest studentRequest){
        return studentService.createStudent(studentRequest);
    }

    @PatchMapping("/{email}")
    public StudentRequest addCourse(@RequestBody StudentRequest studentRequest,
                                    @PathVariable("email") String email){
        return studentService.addCourse(studentRequest, email);
    }

    @PutMapping("/{email}")
    public StudentRequest updateStudent(@RequestBody StudentRequest studentRequest,
                                        @PathVariable("email") String email){
        return studentService.updateStudent(studentRequest, email);
    }

    @GetMapping
    public List<StudentResponse> getStudent(@RequestParam(value = "email", required = false) String email){
        return studentService.getStudent(email);
    }
    @DeleteMapping("{email}")
    public void deleteStudent(@PathVariable("email") String email){
        studentService.deleteStudent(email);
    }
}
