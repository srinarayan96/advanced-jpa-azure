package com.sricode.crudadvancedjpa.services;

import com.sricode.crudadvancedjpa.ExceptionHandling.CourseException;
import com.sricode.crudadvancedjpa.entities.Course;
import com.sricode.crudadvancedjpa.entities.Review;
import com.sricode.crudadvancedjpa.models.CourseReview;
import com.sricode.crudadvancedjpa.repositories.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final CourseRepository courseRepository;
    public CourseReview addCourseReview(CourseReview courseReview) {
        Course course = courseRepository.findById(courseReview.getCourseId())
                .orElseThrow(() -> new CourseException("No course found with id: " + courseReview.getCourseId()));
        Review review = Review.builder().comment(courseReview.getComment()).build();
        course.addReview(review);
        try {
            courseRepository.save(course);
            return courseReview;
        }catch (Exception e){
            e.printStackTrace();
            throw new CourseException(e.getMessage());
        }
    }
}
