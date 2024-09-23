package com.sricode.crudadvancedjpa.services;

import com.sricode.crudadvancedjpa.ExceptionHandling.CourseException;
import com.sricode.crudadvancedjpa.entities.Course;
import com.sricode.crudadvancedjpa.entities.Student;
import com.sricode.crudadvancedjpa.models.CourseDetails;
import com.sricode.crudadvancedjpa.models.StudentRequest;
import com.sricode.crudadvancedjpa.models.StudentResponse;
import com.sricode.crudadvancedjpa.repositories.CourseRepository;
import com.sricode.crudadvancedjpa.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;
    private final CourseRepository courseRepository;
    public StudentRequest createStudent(StudentRequest studentRequest) {
        Student student = modelMapper.map(studentRequest, Student.class);
        String courseTitle = studentRequest.getCourseTitle();
        Course course = courseRepository.findByTitle(courseTitle).orElseThrow(() ->
                new CourseException("No course with title " + courseTitle +" was found"));
        course.addStudent(student);
        student.addCourse(course);
        try {
            System.out.println("student = " + student);
            System.out.println("course = " + course);
            studentRepository.save(student);
            //courseRepository.save(course);
            return studentRequest;
        }catch (Exception e) {
            e.printStackTrace();
            throw new CourseException(e.getMessage());
        }
    }

    public StudentRequest updateStudent(StudentRequest studentRequest, String email) {
        return studentRequest;
    }

    public StudentRequest addCourse(StudentRequest studentRequest, String email) {
        Student student = studentRepository.findByEmail(email).orElseThrow(() -> new CourseException(
                "Student with email " + email + " not found"));
        String courseTitle = studentRequest.getCourseTitle();
        Course course = courseRepository.findByTitle(courseTitle).orElseThrow(() ->
                new CourseException("No course with title " + courseTitle +" was found"));
        student.addCourse(course);
        course.addStudent(student);
        try {
            studentRepository.save(student);
            return studentRequest;
        }catch (Exception e){
            e.printStackTrace();
            throw new CourseException(e.getMessage());
        }
    }

    public List<StudentResponse> getStudent(String email) {
        if(email!= null) {
            Student student = studentRepository.findByEmailWithCourses(email).orElseThrow(() -> new CourseException(
                    "Student with email " + email + " not found"));
            StudentResponse studentResponse = modelMapper.map(student, StudentResponse.class);
            List<CourseDetails> courseDetails = student.getCourses().stream().map(course ->
                    CourseDetails.builder().id(course.getId()).instructorId(course.getInstructor().getId())
                            .title(course.getTitle()).build()).toList();
            studentResponse.setEnrolledCourses(courseDetails);
            return List.of(studentResponse);
        } else {
            List<Student> students = studentRepository.findAllByEmailWithCourses();
            return students.stream().map(student -> {
                StudentResponse studentResponse = modelMapper.map(student, StudentResponse.class);
                List<CourseDetails> courseDetails = student.getCourses().stream().map(course ->
                        CourseDetails.builder().id(course.getId()).instructorId(course.getInstructor().getId())
                                .title(course.getTitle()).build()).toList();
                studentResponse.setEnrolledCourses(courseDetails);
                return studentResponse;
            }).toList();
        }
    }

    public void deleteStudent(String email) {
        Student student = studentRepository.findByEmail(email).orElseThrow(() -> new CourseException(
                "Student with email " + email + " not found"));
        try {
            studentRepository.delete(student);
        }catch (Exception e){
            e.printStackTrace();
            throw new CourseException(e.getMessage());
        }
    }
}
