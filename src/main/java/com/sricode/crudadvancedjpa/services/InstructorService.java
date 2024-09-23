package com.sricode.crudadvancedjpa.services;

import com.sricode.crudadvancedjpa.ExceptionHandling.InstructorException;
import com.sricode.crudadvancedjpa.entities.Course;
import com.sricode.crudadvancedjpa.entities.Instructor;
import com.sricode.crudadvancedjpa.entities.InstructorDetails;
import com.sricode.crudadvancedjpa.models.InstructorRequest;
import com.sricode.crudadvancedjpa.models.InstructorResponse;
import com.sricode.crudadvancedjpa.repositories.CourseRepository;
import com.sricode.crudadvancedjpa.repositories.InstructorDetailsRepository;
import com.sricode.crudadvancedjpa.repositories.InstructorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class InstructorService {
    private final InstructorDetailsRepository instructorDetailsRepository;
    private final InstructorRepository instructorRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    @Transactional()
    public CompletableFuture<InstructorRequest> saveInstructor(InstructorRequest instructorRequest) {
        InstructorDetails instructorDetails = modelMapper.map(instructorRequest, InstructorDetails.class);
        Instructor instructor = modelMapper.map(instructorRequest, Instructor.class);
        instructor.setInstructorDetails(instructorDetails);
        instructorDetails.setInstructor(instructor);
        List<Course> courses = instructorRequest.getCourseTitles().stream().map(s -> Course.builder().title(s).build()).toList();
        courses.forEach(instructor::addCourse);
         return CompletableFuture.supplyAsync(() -> instructorRepository.save(instructor))
                .whenComplete((result, throwable) -> {
                    if(throwable!=null) {
                        throw new InstructorException(throwable.getMessage());
                    }
                }).thenApply(instructor1 -> instructorRequest);
    }

    public InstructorRequest getInstructor(Integer id) {
        return CompletableFuture.supplyAsync(() -> instructorRepository.findById(id))
                .thenApply(instructorOptional -> instructorOptional.map(instructor -> {
                    InstructorRequest instructorRequest = modelMapper.map(instructor, InstructorRequest.class);
                    if(instructor.getInstructorDetails() != null) {
                        modelMapper.map(instructor.getInstructorDetails(), instructorRequest);
                    }
                    List<Course> courses = courseRepository.findByInstructorId(id);
                    List<String> courseTitles = courses.stream().map(Course::getTitle).toList();
                    instructorRequest.setCourseTitles(courseTitles);
                    /*List<String> courseTitles = instructor.getCourses().stream().map(Course::getTitle).toList();
                    instructorRequest.setCourseTitles(courseTitles);*/
                    return instructorRequest;
                }).orElseThrow(() -> new InstructorException("Instructor not found with given id: " +id))).join();
    }


    public InstructorRequest deleteInstructor(Integer id) {
        Instructor instructor = instructorRepository.findById(id).orElseThrow(() -> new InstructorException("Instructor not found with given id: " +id));
        InstructorRequest response = modelMapper.map(instructor, InstructorRequest.class);
        List<Course> courses = courseRepository.findByInstructorId(id);
        courses.forEach(course -> course.setInstructor(null));
        courseRepository.saveAll(courses);
        instructorRepository.delete(instructor);
        return response;
    }

    public CompletableFuture<InstructorRequest> getInstructorDetails(Integer id) {
        System.out.println("current thread =" + Thread.currentThread().getName());
        return CompletableFuture.supplyAsync(() -> instructorDetailsRepository.findById(id))
                .thenApply(instructorDetOptional -> instructorDetOptional.map(instructorDet -> {
                    System.out.println("current thread in =" + Thread.currentThread().getName());
                    InstructorRequest instructorRequest = modelMapper.map(instructorDet, InstructorRequest.class);
                    modelMapper.map(instructorDet.getInstructor(), instructorRequest);
                    return instructorRequest;
                }).orElseThrow(() -> new InstructorException("Instructor Details not found with given id: " +id)));
    }

    public InstructorRequest deleteInstructorDetails(Integer id) {
        InstructorDetails instructorDetails = instructorDetailsRepository.findById(id).orElseThrow(() -> new InstructorException("Instructor Details not found with given id: " +id));
        InstructorRequest response = modelMapper.map(instructorDetails, InstructorRequest.class);
        instructorDetails.getInstructor().setInstructorDetails(null);
        instructorDetailsRepository.delete(instructorDetails);
        return response;
    }

    public List<InstructorResponse> findAll() {
        List<Instructor> instructors = instructorRepository.findAll();
        return instructors.stream().map(instructor -> {
            InstructorResponse instructorResponse = modelMapper.map(instructor, InstructorResponse.class);
            System.out.println("instructor id = " + instructorResponse.getId());
            if(instructor.getInstructorDetails() != null) {
                modelMapper.map(instructor.getInstructorDetails(), instructorResponse);
                System.out.println("instructor id = " + instructorResponse.getId());
                System.out.println("-".repeat(50));
            }
            return instructorResponse;
        }).toList();
    }

    public InstructorResponse updateInstructor(InstructorRequest instructorRequest, Integer id) {
        Instructor instructor = instructorRepository.findById(id)
                .orElseThrow(() -> new InstructorException("Instructor not found with given email: " +instructorRequest.getEmail()));
        modelMapper.map(instructorRequest, instructor);
        modelMapper.map(instructorRequest, instructor.getInstructorDetails());
        try {
            instructorRepository.save(instructor);
            InstructorResponse instructorResponse = modelMapper.map(instructor, InstructorResponse.class);
            if(instructor.getInstructorDetails() != null) {
                modelMapper.map(instructor.getInstructorDetails(), instructorResponse);
            }
            return instructorResponse;
        }catch (Exception e){
            throw new InstructorException("Error in updating the instructor");
        }
    }
}
