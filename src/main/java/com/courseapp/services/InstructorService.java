package com.courseapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.courseapp.entities.Course;
import com.courseapp.entities.Instructor;
import com.courseapp.repositories.InstructorRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class InstructorService {
	
	@NonNull
	private final InstructorRepository instructorRepo;
	
	public List<Instructor> getInstructors() {
		log.info("Fetching all instructors");
		return this.instructorRepo.findAll();
	}
	
	public Optional<Instructor> getInstructor(final Long id) {
		log.info("Fetching instructor with id={}", id);
		return this.instructorRepo.findById(id);
	}
	
	public Instructor addInstructor(@NonNull final Instructor instructor) {
		final Instructor savedInstructor = this.instructorRepo.save(instructor);
		log.info("Saved an instructor with id={}", savedInstructor.getInstructorId());
		
		return savedInstructor;
	}

	public List<Instructor> addInstructors(@NonNull final List<Instructor> instructors) {
		final List<Instructor> savedInstructors = this.instructorRepo.saveAll(instructors);
		log.info("Saved {} instructors", savedInstructors.size());
		
		return savedInstructors;
	}
}
