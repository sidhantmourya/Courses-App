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
import com.courseapp.entities.Student;
import com.courseapp.services.CourseService;

@RestController
public class CourseController {
	
	@Autowired
	private CourseService courseService;
	
	
	@GetMapping("/api/courses")
	public List<Course> findCourses() {
		return courseService.getCourses();
	}
	
	@GetMapping("/api/course/{id}")
	public Optional<Course> findCourseById(@PathVariable("id") Long id) {
		return courseService.getCourseById(id);
	}
		
	@PostMapping("/api/course")
	public Course addCourse(@RequestBody Course course) {
		Course savedCourse = courseService.addCourse(course);
		
		return savedCourse;
	}
	
	@PostMapping("/api/courses")
	public List<Course> addCourse(@RequestBody List<Course> courses) {
		List<Course> savedCourses = courseService.addCourses(courses);
		
		return savedCourses;
	}
	
}
