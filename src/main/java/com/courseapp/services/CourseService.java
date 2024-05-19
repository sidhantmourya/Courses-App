package com.courseapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.courseapp.entities.Course;
import com.courseapp.repositories.CourseRepository;

@Service
public class CourseService {
	
	@Autowired
	private CourseRepository courseRepo;
	
	
	public List<Course> getCourses() {
		return courseRepo.findAll();
	}
	
	public Optional<Course> getCourseById(Long id) {
		return courseRepo.findById(id);
	}
	
	public Course addCourse(Course course) {
		Course savedCourse = courseRepo.save(course);
		return savedCourse;
	}
	
	public List<Course> addCourses(List<Course> courses) {
		List<Course> savedCourses = courseRepo.saveAll(courses);
		
		return savedCourses;
	}
	
	
}
