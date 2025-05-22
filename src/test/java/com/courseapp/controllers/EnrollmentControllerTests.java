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

import com.courseapp.entities.Course;
import com.courseapp.entities.Enrollment;
import com.courseapp.entities.Instructor;
import com.courseapp.entities.Student;
import com.courseapp.services.EnrollmentService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(EnrollmentController.class)
public class EnrollmentControllerTests {
	
	@MockBean
	private EnrollmentService enrollmentService;
	
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
	
	private final Course course = Course.builder()
			.courseId(1L)
			.courseName("courseName")
			.courseDuration(10)
			.instructor(instructor)
			.build();
	
	private final Student student = Student.builder()
			.studentId(1L)
			.studentName("studentName")
			.address("address")
			.sex("M")
			.build();
	
	private final List<Enrollment> enrollments = List.of(
			Enrollment.builder()
				.enrollmentId(1L)
				.student(student)
				.course(course)
				.build()
	);
	
	@Test
	public void test_getAllEnrollments_success() throws Exception {
		Mockito.when(enrollmentService.getEnrollments())
			.thenReturn(enrollments);
		
		mockMvc.perform(
				MockMvcRequestBuilders.get("/api/enrollments").contentType(MediaType.APPLICATION_JSON)
		)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].enrollmentId").value(enrollments.get(0).getEnrollmentId()))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].student.studentId").value(enrollments.get(0).getStudent().getStudentId()))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].student.studentName").value(enrollments.get(0).getStudent().getStudentName()))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].course.courseId").value(enrollments.get(0).getCourse().getCourseId()))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].course.courseName").value(enrollments.get(0).getCourse().getCourseName()))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].course.courseDuration").value(enrollments.get(0).getCourse().getCourseDuration()));
		
		Mockito.verify(enrollmentService, Mockito.times(1))
			.getEnrollments();
	}
	
	@Test
	public void test_getEnrollmentById_success() throws Exception {
		final Enrollment enrollment = enrollments.get(0);
		
		Mockito.when(enrollmentService.getEnrollmentById(Mockito.anyLong()))
			.thenReturn(Optional.of(enrollment));
		
		mockMvc.perform(
				MockMvcRequestBuilders.get("/api/enrollment/1").contentType(MediaType.APPLICATION_JSON)
		)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.enrollmentId").value(enrollment.getEnrollmentId()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.student.studentId").value(enrollment.getStudent().getStudentId()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.student.studentName").value(enrollment.getStudent().getStudentName()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.course.courseId").value(enrollment.getCourse().getCourseId()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.course.courseName").value(enrollment.getCourse().getCourseName()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.course.courseDuration").value(enrollment.getCourse().getCourseDuration()));
		
		Mockito.verify(enrollmentService, Mockito.times(1))
			.getEnrollmentById(Mockito.anyLong());
	}
	
	@Test
	public void test_enrollStudent_success() throws Exception {
		final Enrollment enrollment = enrollments.get(0);
		
		Mockito.when(enrollmentService.enrollStudent(Mockito.anyLong(), Mockito.anyLong()))
			.thenReturn(enrollment);
		
		final String enrollmentRequestJson = objectMapper.writeValueAsString(
			EnrollmentController.EnrollmentRequest.builder()
				.studentId(enrollment.getStudent().getStudentId())
				.courseId(enrollment.getCourse().getCourseId())
				.build()
		);
		
		mockMvc.perform(
				MockMvcRequestBuilders.post("/api/enrollment")
					.contentType(MediaType.APPLICATION_JSON)
					.content(enrollmentRequestJson)
		)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.enrollmentId").value(enrollment.getEnrollmentId()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.student.studentId").value(enrollment.getStudent().getStudentId()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.student.studentName").value(enrollment.getStudent().getStudentName()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.course.courseId").value(enrollment.getCourse().getCourseId()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.course.courseName").value(enrollment.getCourse().getCourseName()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.course.courseDuration").value(enrollment.getCourse().getCourseDuration()));
		
		Mockito.verify(enrollmentService, Mockito.times(1))
			.enrollStudent(Mockito.anyLong(), Mockito.anyLong());
	}
}
