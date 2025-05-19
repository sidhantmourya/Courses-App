package com.courseapp.services;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.courseapp.entities.Course;
import com.courseapp.entities.Instructor;
import com.courseapp.repositories.CourseRepository;

public class CourseServiceTests {
	
	@Mock
	private CourseRepository courseRepository;
	
	@InjectMocks
	private CourseService courseService;
	
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
			.build(),
			
			Course.builder()
			.courseId(2L)
			.courseName("course2Name")
			.courseDuration(20)
			.instructor(instructor)
			.build()
	);
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void test_getAllCourses_success() {
		Mockito.when(courseRepository.findAll()).thenReturn(courses);
		
		final List<Course> fetchedCourses = courseService.getCourses();
		
		Assertions.assertNotNull(fetchedCourses);
		Assertions.assertEquals(fetchedCourses.size(), courses.size());
		
		Mockito.verify(courseRepository, Mockito.times(1)).findAll();
	}
	
	@Test
	public void test_getCourseById_success() {
		final Long id = 1L;
		
		final Optional<Course> filteredCourseOptional = courses.stream()
				.filter(course -> course.getCourseId().equals(id))
				.findFirst();
		
		Mockito.when(courseRepository.findById(Mockito.anyLong()))
			.thenReturn(filteredCourseOptional);
		
		final Optional<Course> fetchedCourseOptional = courseService.getCourseById(id);
		final Course fetchedCourse = fetchedCourseOptional.get();
		final Course filteredCourse = filteredCourseOptional.get();
		
		Assertions.assertNotNull(fetchedCourseOptional);
		Assertions.assertNotNull(fetchedCourse);
		Assertions.assertEquals(fetchedCourse, filteredCourse);
	}
	
	@Test
	public void test_addCourse_success() {
		final Course course = courses.get(0);
		
		Mockito.when(courseRepository.save(Mockito.any(Course.class)))
			.thenReturn(course);
		
		final Course savedCourse = courseService.addCourse(course);
		
		Assertions.assertNotNull(savedCourse);
		
		Mockito.verify(courseRepository, Mockito.times(1))
			.save(Mockito.any(Course.class));
	}
}
