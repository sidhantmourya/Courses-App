package com.courseapp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.courseapp.entities.Course;
import com.courseapp.entities.Enrollment;
import com.courseapp.entities.Student;
import com.courseapp.services.EnrollmentService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
public class EnrollmentController {
	
	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	static class EnrollmentRequest {
		private Long courseId;
		private Long studentId;
	}
	
	
	@NonNull
	private final EnrollmentService enrollmentService;
	
	@GetMapping("/api/enrollments")
	public List<Enrollment> getEnrollments() {
		return enrollmentService.getEnrollments();
	}
	
	@GetMapping("/api/enrollment/{id}")
	public Optional<Enrollment> getEnrollmentById(@PathVariable("id") final Long id) {
		return enrollmentService.getEnrollmentById(id);
	}
	
	@PostMapping("/api/enrollment")
	public Enrollment enrollStudent(@RequestBody @NonNull final EnrollmentRequest req) {	
		final Long studentId = req.getStudentId();
		final Long courseId = req.getCourseId();
		
		
		return enrollmentService.enrollStudent(studentId, courseId);
	}
	
	
	@GetMapping("/api/course/{id}/students")
	public List<Student> findStudentsOfCourse(@PathVariable("id") final Long id) {
		return enrollmentService.findStudentsByCourseId(id);
	}
	

	@GetMapping("/api/student/{id}/courses")
	public List<Course> findCoursesOfStudent(@PathVariable("id") final Long id) {
		return enrollmentService.findCoursesByStudentId(id);
	}


}
