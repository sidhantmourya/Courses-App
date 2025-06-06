package com.courseapp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.courseapp.entities.Instructor;
import com.courseapp.services.InstructorService;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class InstructorController {
	
	@NonNull
	private final InstructorService instructorService;
	
	@GetMapping("/api/instructors")
	public List<Instructor> findInstructors() {
		return this.instructorService.getInstructors();
	}

	@GetMapping("/api/instructor/{id}")
	public Optional<Instructor> findInstructorById(@PathVariable("id") final Long id) {
		return this.instructorService.getInstructor(id);
	}
	
	@PostMapping("/api/instructor")
	public Instructor addInstructor(@RequestBody @NonNull final Instructor instructor) {
		final Instructor savedInstructor = this.instructorService.addInstructor(instructor);
		
		return savedInstructor;
	}
	
	@PostMapping("/api/instructors")
	public List<Instructor> addInstructor(@RequestBody @NonNull final List<Instructor> instructors) {
		final List<Instructor> savedInstructors = this.instructorService.addInstructors(instructors);
		
		return savedInstructors;
	}
}
