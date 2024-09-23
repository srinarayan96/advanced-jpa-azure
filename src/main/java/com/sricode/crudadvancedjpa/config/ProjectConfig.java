package com.sricode.crudadvancedjpa.config;

import com.sricode.crudadvancedjpa.entities.Course;
import com.sricode.crudadvancedjpa.entities.Instructor;
import com.sricode.crudadvancedjpa.entities.InstructorDetails;
import com.sricode.crudadvancedjpa.models.CourseRequest;
import com.sricode.crudadvancedjpa.models.CourseStudentRequest;
import com.sricode.crudadvancedjpa.models.InstructorRequest;
import com.sricode.crudadvancedjpa.models.InstructorResponse;
import org.modelmapper.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectConfig {
    @Bean
    public ModelMapper getModelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        Condition<?, ?> notNull = Conditions.isNotNull();
        TypeMap<InstructorDetails, InstructorResponse> typeMap = modelMapper.createTypeMap(InstructorDetails.class,
                InstructorResponse.class);
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setPropertyCondition(notNull);
        typeMap.addMappings(mapper -> {
            mapper.skip(InstructorResponse::setId); // Skip the id property
        });

        TypeMap<CourseRequest, Course> typeMapCourse = modelMapper.createTypeMap(CourseRequest.class,
                Course.class);
        typeMapCourse.addMappings(mapper -> mapper.skip(Course::setId));
        TypeMap<CourseRequest, Instructor> typeMapCourseInstructor = modelMapper.createTypeMap(CourseRequest.class, Instructor.class);
        typeMapCourseInstructor.addMappings(mapper -> mapper.skip(Instructor::setId));
        TypeMap<InstructorRequest, Instructor> typeMapInstInstructor = modelMapper.createTypeMap(InstructorRequest.class,
                Instructor.class);
        typeMapInstInstructor.addMappings(mapper -> mapper.skip(Instructor::setId));

        TypeMap<CourseStudentRequest, Course> typeMapCourseStuCourse = modelMapper.createTypeMap(CourseStudentRequest.class,
                Course.class);
        typeMapCourseStuCourse.addMappings(mapper -> mapper.skip(Course::setId));
        return modelMapper;
    }
}
