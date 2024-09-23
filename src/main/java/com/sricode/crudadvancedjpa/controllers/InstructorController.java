package com.sricode.crudadvancedjpa.controllers;

import com.sricode.crudadvancedjpa.models.InstructorRequest;
import com.sricode.crudadvancedjpa.models.InstructorResponse;
import com.sricode.crudadvancedjpa.services.InstructorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/instructor")
@RequiredArgsConstructor
public class InstructorController {
    private final InstructorService instructorService;
    @PostMapping
    public CompletableFuture<InstructorRequest> saveInstructor(@Valid @RequestBody InstructorRequest instructorRequest){
        return instructorService.saveInstructor(instructorRequest);
    }

    @GetMapping
    public List<InstructorResponse> findAllInstructors(){
        return instructorService.findAll();
    }
    @GetMapping("/{id}")
    public  InstructorRequest getInstructor(@PathVariable("id") Integer id){//CompletableFuture<InstructorRequest>
        return instructorService.getInstructor(id);
        //return instructorService.getInstructoryById(id);
    }
    @DeleteMapping("/{id}")
    public InstructorRequest deleteInstructor(@PathVariable("id") Integer id){
        return instructorService.deleteInstructor(id);
    }

    @GetMapping("/details/{id}")
    public CompletableFuture<InstructorRequest> getInstructorDetails(@PathVariable("id") Integer id){
        return instructorService.getInstructorDetails(id);
    }
    @DeleteMapping("/details/{id}")
    public InstructorRequest deleteInstructorDetails(@PathVariable("id") Integer id){
        return instructorService.deleteInstructorDetails(id);
    }

    @PutMapping("/{id}")
    public InstructorResponse updateInstructor(@RequestBody InstructorRequest instructorRequest,
                                               @PathVariable("id") Integer id){
        return instructorService.updateInstructor(instructorRequest, id);
    }
}
