package com.sricode.crudadvancedjpa.services;

import com.sricode.crudadvancedjpa.ExceptionHandling.CourseException;
import com.sricode.crudadvancedjpa.ExceptionHandling.InstructorException;
import com.sricode.crudadvancedjpa.entities.*;
import com.sricode.crudadvancedjpa.models.CourseRequest;
import com.sricode.crudadvancedjpa.models.CourseResponse;
import com.sricode.crudadvancedjpa.models.CourseStudentRequest;
import com.sricode.crudadvancedjpa.repositories.CourseRepository;
import com.sricode.crudadvancedjpa.repositories.CourseStudentRepository;
import com.sricode.crudadvancedjpa.repositories.InstructorRepository;
import com.sricode.crudadvancedjpa.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final CourseStudentRepository courseStudentRepository;
    private final ModelMapper modelMapper;
    public CourseRequest saveCourse(CourseRequest courseRequest) {
        Course course = modelMapper.map(courseRequest, Course.class);
        Instructor instructor = instructorRepository.findById(courseRequest.getInstructorId())
                .orElseThrow(() -> new InstructorException("Instructor with given ID not found: " + courseRequest.getInstructorId()));
        instructor.addCourse(course);
        try {
            courseRepository.save(course);
        } catch (Exception e){
            throw new CourseException(e.getMessage());
        }
        return courseRequest;
    }
    public CourseRequest updateCourseInstructor(CourseRequest courseRequest, String courseTitle) {
        Course course = courseRepository.findByTitle(courseTitle).orElseThrow(() -> new CourseException("Can't find course with title: " + courseTitle));
        Instructor newInstructor = instructorRepository.findById(courseRequest.getInstructorId()).orElseThrow(() -> new InstructorException("Instructor with given ID not found: " + courseRequest.getInstructorId()));
        course.setTitle(courseRequest.getTitle());
        course.setInstructor(newInstructor);
        try {
            courseRepository.save(course);
            return courseRequest;
        }catch (Exception e){
            e.printStackTrace();
            throw new CourseException(e.getMessage());
        }
    }

    public CourseRequest deleteCourse(String courseTitle){
        Course course = courseRepository.findByTitle(courseTitle).orElseThrow(() -> new CourseException("Can't find course with title: " + courseTitle));
        List<CourseStudent> courseStudents = courseStudentRepository.findByCourseId(course.getId());
        try {
            courseStudentRepository.deleteAll(courseStudents);
            courseRepository.delete(course);
            return modelMapper.map(course, CourseRequest.class);
        }catch (Exception e){
            throw new CourseException(e.getMessage());
        }
    }

    public List<CourseResponse> getCourses(Integer id) {
        if(id != null){
            Course course = courseRepository.findCourseWithReviews(id).orElseThrow(() -> new CourseException("Can't find course with id: " + id));
            CourseResponse courseResponse = modelMapper.map(course, CourseResponse.class);
            System.out.println("courseResponse = " + courseResponse);
            List<String> comments = course.getReviews().stream().map(Review::getComment).toList();
            courseResponse.setComments(comments);

            Course searchCoursesWithStudents = courseRepository.findCourseWithStudents(id).orElseThrow(() -> new CourseException("Can't find course with id: " + id));
            List<String> studentEmails = searchCoursesWithStudents.getStudents().stream().map(Student::getEmail).toList();
            courseResponse.setStudentsEmail(studentEmails);
            return List.of(courseResponse);
        } else {
            List<Course> courses = courseRepository.findAllCoursesWithReviews();
            List<CourseResponse> courseResponses = courses.stream().map(course -> {
               CourseResponse courseResponse = modelMapper.map(course, CourseResponse.class);
                List<String> comments = course.getReviews().stream().map(Review::getComment).toList();
                courseResponse.setComments(comments);
                return courseResponse;
            }).sorted(Comparator.comparing(CourseResponse::getId)).toList();

            List<Course> searchCoursesWithStudents = courseRepository.findAllCourseWithStudents().stream().sorted(Comparator.comparing(Course::getId))
                    .toList();
            for (int i = 0; i < courseResponses.size(); i++) {
                List<String> emails = searchCoursesWithStudents.get(i).getStudents().stream().map(Student::getEmail).toList();
                courseResponses.get(i).setStudentsEmail(emails);
            }
            return courseResponses;
        }
    }

    public CourseStudentRequest addCourseStudent(CourseStudentRequest courseStudentRequest) {
        Optional<Course> searchCourse = courseRepository.findByTitle(courseStudentRequest.getCourseTitle());
        if(searchCourse.isPresent()){
            throw new CourseException("Course with title " +courseStudentRequest.getCourseTitle() +" already present");
        }
        Course course = modelMapper.map(courseStudentRequest, Course.class);
        Instructor instructor = instructorRepository.findById(courseStudentRequest.getInstructorId())
                .orElseThrow(() -> new InstructorException("Instructor with given ID not found: " + courseStudentRequest.getInstructorId()));
        course.setInstructor(instructor);
        Student student = Student.builder().email(courseStudentRequest.getEmail())
                .firstName(courseStudentRequest.getFirstName()).lastName(courseStudentRequest.getLastName())
                .build();
        student.addCourse(course);
        course.addStudent(student);

        try {
            studentRepository.save(student);
            return courseStudentRequest;
        }catch (Exception e){
            e.printStackTrace();
            throw new CourseException(e.getMessage());
        }
    }
}
