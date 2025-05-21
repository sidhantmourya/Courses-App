package com.courseapp.controllers;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.courseapp.entities.Instructor;
import com.courseapp.services.InstructorService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(InstructorController.class)
public class InstructorControllerTests {
	
	@MockBean
	private InstructorService instructorService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private MockMvc mockMvc;
	
	private final List<Instructor> instructors = List.of(
			Instructor.builder()
				.instructorId(1L)
				.instructorName("instructorName")
				.instructorEmail("instructor@email.com")
				.courses(List.of())
				.build()
	);
	
	@Test
	public void test_getAllInstructors_success() throws Exception {
		Mockito.when(instructorService.getInstructors())
		.thenReturn(instructors);
	
		mockMvc.perform(
				MockMvcRequestBuilders.get("/api/instructors").contentType(MediaType.APPLICATION_JSON)
		)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].instructorId").value(instructors.get(0).getInstructorId()))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].instructorName").value(instructors.get(0).getInstructorName()))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].instructorEmail").value(instructors.get(0).getInstructorEmail()));
		
		Mockito.verify(instructorService, Mockito.times(1))
			.getInstructors();
	}
	
	@Test
	public void test_getAllInstructorById_success() throws Exception {
		final Instructor instructor = instructors.get(0);
		
		Mockito.when(instructorService.getInstructor(Mockito.anyLong()))
		.thenReturn(Optional.of(instructor));
	
		mockMvc.perform(
				MockMvcRequestBuilders.get("/api/instructor/1").contentType(MediaType.APPLICATION_JSON)
		)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.instructorId").value(instructor.getInstructorId()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.instructorName").value(instructor.getInstructorName()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.instructorEmail").value(instructor.getInstructorEmail()));
		
		Mockito.verify(instructorService, Mockito.times(1))
			.getInstructor(Mockito.anyLong());
	}
	
	@Test
	public void test_addInstructor_success() throws Exception {
		final Instructor instructor = instructors.get(0);
		
		Mockito.when(instructorService.addInstructor(Mockito.any(Instructor.class)))
			.thenReturn(instructor);
		
		mockMvc.perform(
				MockMvcRequestBuilders.post("/api/instructor")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(instructor))
		)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.instructorId").value(instructor.getInstructorId()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.instructorName").value(instructor.getInstructorName()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.instructorEmail").value(instructor.getInstructorEmail()));
		
		Mockito.verify(instructorService, Mockito.times(1))
			.addInstructor(Mockito.any(Instructor.class));
	}
}
