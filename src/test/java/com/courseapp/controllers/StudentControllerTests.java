package com.courseapp.controllers;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.courseapp.entities.Student;
import com.courseapp.services.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(StudentController.class)
public class StudentControllerTests {
	
	@MockBean
	private StudentService studentService;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private MockMvc mockMvc;
	
	private final List<Student> students = List.of(
			Student.builder()
				.studentId(1L)
				.studentName("studentName")
				.address("address")
				.sex("M")
				.build()
	);
	
	@Test
	public void test_getAllStudents_success() throws Exception {
		Mockito.when(studentService.getStudents())
			.thenReturn(students);
			
		mockMvc.perform(
				MockMvcRequestBuilders.get("/api/students").contentType(MediaType.APPLICATION_JSON)
				)
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].studentId").value(1L))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].studentName").value("studentName"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].address").value("address"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].sex").value("M"));
		
		Mockito.verify(studentService, Mockito.times(1))
			.getStudents();
	}
	
	@Test
	public void test_getStudentById_success() throws Exception {
		final Student student = students.get(0);
		
		Mockito.when(studentService.getStudentById(Mockito.anyLong()))
			.thenReturn(Optional.of(student));
		
		mockMvc.perform(
				MockMvcRequestBuilders.get("/api/student/1").contentType(MediaType.APPLICATION_JSON)
			)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.studentId").value(1L))
		.andExpect(MockMvcResultMatchers.jsonPath("$.studentName").value("studentName"));
		
		Mockito.verify(studentService, Mockito.times(1))
			.getStudentById(Mockito.anyLong());
	}
	
	@Test
	public void test_addStudent_success() throws Exception {
		final Student student = students.get(0);
		
		Mockito.when(studentService.addStudent(Mockito.any(Student.class)))
			.thenReturn(student);
		
		mockMvc.perform(
				MockMvcRequestBuilders.post("/api/student")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(student))
		)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.studentId").value(1L))
		.andExpect(MockMvcResultMatchers.jsonPath("$.studentName").value("studentName"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("address"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.sex").value("M"));
		
		Mockito.verify(studentService, Mockito.times(1))
			.addStudent(Mockito.any(Student.class));
	}
}
