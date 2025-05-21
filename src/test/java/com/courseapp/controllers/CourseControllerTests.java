package com.courseapp.controllers;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.courseapp.entities.Course;
import com.courseapp.entities.Instructor;
import com.courseapp.services.CourseService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CourseController.class)
public class CourseControllerTests {
	
	@MockBean
	private CourseService courseService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private final Instructor instructor = Instructor.builder()
			.instructorId(1L)
			.instructorName("instructorName")
			.instructorEmail("instructor@email.com")
			.courses(List.of())
			.build();
	
	private final List<Course> courses = List.of(
			Course.builder()
				.courseId(1L)
				.courseName("courseName")
				.courseDuration(10)
				.instructor(instructor)
				.build()
	);
	
	@Test
	public void test_getAllCourses_success() throws Exception {
		Mockito.when(courseService.getCourses())
			.thenReturn(courses);
		
		mockMvc.perform(
				MockMvcRequestBuilders.get("/api/courses").contentType(MediaType.APPLICATION_JSON)
		)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].courseId").value(courses.get(0).getCourseId()))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].courseName").value(courses.get(0).getCourseName()))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].courseDuration").value(courses.get(0).getCourseDuration()));
		
		Mockito.verify(courseService, Mockito.times(1))
			.getCourses();
	}
	
	@Test
	public void test_getCourseById_success() throws Exception {
		final Course course = courses.get(0);
		
		Mockito.when(courseService.getCourseById(Mockito.anyLong()))
			.thenReturn(Optional.of(course));
		
		mockMvc.perform(
				MockMvcRequestBuilders.get("/api/course/1").contentType(MediaType.APPLICATION_JSON)
		)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.courseId").value(course.getCourseId()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.courseName").value(course.getCourseName()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.courseDuration").value(course.getCourseDuration()));
		
		Mockito.verify(courseService, Mockito.times(1))
				.getCourseById(Mockito.anyLong());
	}
	
	@Test
	public void test_addCourse_success() throws Exception {
		final Course course = courses.get(0);
		
		Mockito.when(courseService.addCourse(Mockito.any(Course.class)))
			.thenReturn(course);
		
		mockMvc.perform(
				MockMvcRequestBuilders.post("/api/course")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(course))
		)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.courseId").value(course.getCourseId()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.courseName").value(course.getCourseName()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.courseDuration").value(course.getCourseDuration()));
		
		Mockito.verify(courseService, Mockito.times(1))
			.addCourse(Mockito.any(Course.class));
	}
}
