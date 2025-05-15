package com.courseapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.courseapp.entities.Course;
import com.courseapp.repositories.CourseRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class CourseService {
	
	@NonNull
	private final CourseRepository courseRepo;
	
	
	public List<Course> getCourses() {
		log.info("Fetching all courses");
		return courseRepo.findAll();
	}
	
	public Optional<Course> getCourseById(final Long id) {
		log.info("Fetching course with id={}", id);
		return courseRepo.findById(id);
	}
	
	public Course addCourse(@NonNull final Course course) {
		Course savedCourse = courseRepo.save(course);
		log.info("Saved a course with id={}", savedCourse.getCourseId());
		return savedCourse;
	}
	
	public List<Course> addCourses(@NonNull final List<Course> courses) {
		List<Course> savedCourses = courseRepo.saveAll(courses);
		log.info("Saved {} courses", courses.size());
		
		return savedCourses;
	}
	
	
}
