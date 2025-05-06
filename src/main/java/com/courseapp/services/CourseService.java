package com.courseapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.courseapp.entities.Course;
import com.courseapp.repositories.CourseRepository;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@AllArgsConstructor
public class CourseService {
	
	@NonNull
	private final CourseRepository courseRepo;
	
	
	public List<Course> getCourses() {
		return courseRepo.findAll();
	}
	
	public Optional<Course> getCourseById(final Long id) {
		return courseRepo.findById(id);
	}
	
	public Course addCourse(@NonNull final Course course) {
		Course savedCourse = courseRepo.save(course);
		return savedCourse;
	}
	
	public List<Course> addCourses(@NonNull final List<Course> courses) {
		List<Course> savedCourses = courseRepo.saveAll(courses);
		
		return savedCourses;
	}
	
	
}
