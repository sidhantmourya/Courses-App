package com.courseapp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.courseapp.entities.Course;
import com.courseapp.entities.Enrollment;
import com.courseapp.entities.Student;
import com.courseapp.services.EnrollmentService;

@RestController
public class EnrollmentController {
	
	private static class EnrollmentRequest {
		private Long courseId;
		private Long studentId;
		
		EnrollmentRequest() {
			
		}
		
		public Long getCourseId() {
			return courseId;
		}
		
		public void setCourseId(Long courseId) {
			this.courseId = courseId;
		}
		public Long getStudentId() {
			return studentId;
		}
		
		public void setStudentId(Long studentId) {
			this.studentId = studentId;
		}
	}
	
	
	@Autowired
	private EnrollmentService enrollmentService;
	
	@GetMapping("/api/enrollments")
	public List<Enrollment> getEnrollments() {
		return enrollmentService.getEnrollments();
	}
	
	@GetMapping("/api/enrollment/{id}")
	public Optional<Enrollment> getEnrollmentById(Long id) {
		return enrollmentService.getEnrollmentById(id);
	}
	
	@PostMapping("/api/enrollment")
	public Enrollment enrollStudent(@RequestBody EnrollmentRequest req) {	
		Long studentId = req.getStudentId();
		Long courseId = req.getCourseId();
		
		return enrollmentService.enrollStudent(studentId, courseId);
	}
	
	
	@GetMapping("/api/course/{id}/students")
	public List<Student> findStudentsOfCourse(@PathVariable("id") Long id) {
		return enrollmentService.findStudentsByCourseId(id);
	}
	

	@GetMapping("/api/student/{id}/courses")
	public List<Course> findCoursesOfStudent(@PathVariable("id") Long id) {
		return enrollmentService.findCoursesByStudentId(id);
	}


}
