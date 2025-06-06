package com.courseapp.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	@Transactional(readOnly = true)
	public List<Instructor> getInstructors() {
		log.info("Fetching all instructors");
		return this.instructorRepo.findAll();
	}
	
	@Transactional(readOnly = true)
	public Optional<Instructor> getInstructor(final Long id) {
		log.info("Fetching instructor with id={}", id);
		return this.instructorRepo.findById(id);
	}
	
	@Transactional
	public Instructor addInstructor(@NonNull final Instructor instructor) {
		final Instructor savedInstructor = this.instructorRepo.save(instructor);
		log.info("Saved an instructor with id={}", savedInstructor.getInstructorId());
		
		return savedInstructor;
	}

	@Transactional
	public List<Instructor> addInstructors(@NonNull final List<Instructor> instructors) {
		final List<Instructor> savedInstructors = instructors.stream()
			.filter(Objects::nonNull)
			.map(this::addInstructor)
			.toList();
		
		log.info("Saved {} instructors", savedInstructors.size());
		
		return savedInstructors;
	}
}
