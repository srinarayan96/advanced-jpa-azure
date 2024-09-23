package com.sricode.crudadvancedjpa.controllers;

import com.sricode.crudadvancedjpa.models.CourseReview;
import com.sricode.crudadvancedjpa.services.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/course-review")
@RequiredArgsConstructor
public class CourseReviewController {
    private final ReviewService reviewService;
    @PostMapping
    public CourseReview addCourseReview(@Valid @RequestBody CourseReview courseReview){
        return reviewService.addCourseReview(courseReview);
    }
}
