package com.courseapp.services;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.courseapp.entities.Instructor;
import com.courseapp.repositories.InstructorRepository;

public class InstructorServiceTests {
	
	@Mock
	private InstructorRepository instructorRepository;
	
	@InjectMocks
	private InstructorService instructorService;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	private List<Instructor> instructors = List.of(
			Instructor.builder()
				.instructorId(1L)
				.instructorName("instructor1Name")
				.instructorEmail("instructor1@email.com")
				.courses(List.of())
				.build(),
				
				Instructor.builder()
				.instructorId(2L)
				.instructorName("instructo2rName")
				.instructorEmail("instructor2@email.com")
				.courses(List.of())
				.build()
	);
	
	@Test
	public void test_getAllInstructors_success() {
		Mockito.when(instructorRepository.findAll())
			.thenReturn(instructors);
		
		final List<Instructor> fetchedInstructors = instructorService.getInstructors();
		
		Assertions.assertNotNull(fetchedInstructors);
		Assertions.assertEquals(instructors.size(), fetchedInstructors.size());
		
		Mockito.verify(instructorRepository, Mockito.times(1)).findAll();
	}
	
	@Test
	public void test_getInstructorById_success() {
		final Long id = 1L;
		
		final Optional<Instructor> filteredInstructorOptional = instructors.stream()
				.filter(instructor -> instructor.getInstructorId().equals(id))
				.findFirst();
		
		final Instructor filteredInstructor = filteredInstructorOptional.get();
		
		Mockito.when(instructorRepository.findById(id))
			.thenReturn(filteredInstructorOptional);
		
		final Optional<Instructor> fetchedInstructorOptional = instructorService.getInstructor(id);
		
		final Instructor fetchedInstructor = fetchedInstructorOptional.get();
		
		Assertions.assertNotNull(fetchedInstructor);
		Assertions.assertEquals(fetchedInstructor, filteredInstructor);
		
		Mockito.verify(instructorRepository, Mockito.times(1)).findById(id);
	}
	
	@Test
	public void test_addInstructor_success() {
		final Instructor instructor = instructors.get(0);
		
		Mockito.when(instructorRepository.save(Mockito.any(Instructor.class)))
			.thenReturn(instructor);
		
		final Instructor savedInstructor = instructorService.addInstructor(instructor);
		
		Assertions.assertNotNull(savedInstructor);
		
		Mockito.verify(instructorRepository, Mockito.times(1))
			.save(Mockito.any(Instructor.class));
		
	}
	
	@Test
	public void test_addInstructors_success() {
		final Instructor instructor = instructors.get(0);
		
		Mockito.when(instructorRepository.save(Mockito.any(Instructor.class)))
			.thenReturn(instructor);
			
		final List<Instructor> savedInstructors = instructorService.addInstructors(instructors);
		final int numberOfInstructorsSaved = savedInstructors.size();
		
		Assertions.assertNotNull(savedInstructors);
		Assertions.assertEquals(numberOfInstructorsSaved, instructors.size());
		
		Mockito.verify(instructorRepository, Mockito.times(numberOfInstructorsSaved))
			.save(Mockito.any(Instructor.class));
	}
}
