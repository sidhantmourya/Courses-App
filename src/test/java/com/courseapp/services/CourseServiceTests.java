package com.courseapp.services;

import java.util.ArrayList;
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
	
	@Mock
	private InstructorService instructorService;
	
	@InjectMocks
	private CourseService courseService;
	
	private final Instructor instructor = Instructor.builder()
			.instructorId(1L)
			.instructorName("instructorName")
			.instructorEmail("instructor@email.com")
			.courses(new ArrayList<>())
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
	
	@Test
	public void test_assignInstructorToCourse_success() {
		final Course course = courses.get(0);
		
		Mockito.when(courseRepository.findById(Mockito.anyLong()))
			.thenReturn(Optional.of(course));
		
		Mockito.when(instructorService.getInstructor(Mockito.anyLong()))
			.thenReturn(Optional.of(instructor));
		
		Mockito.when(courseRepository.save(Mockito.any(Course.class)))
			.thenReturn(course);		
		
		final Course updatedCourse = courseService.assignInstructorToCourse(1L, 1L);
		
		Assertions.assertNotNull(updatedCourse);
								
		Mockito.verify(courseRepository, Mockito.times(1))
			.save(Mockito.any(Course.class));
	}
	
	@Test
	public void test_assignInstructorToCourse_courseIsNull() {
		Mockito.when(courseRepository.findById(Mockito.anyLong()))
			.thenReturn(Optional.empty());
	
		Mockito.when(instructorService.getInstructor(Mockito.anyLong()))
			.thenReturn(Optional.of(instructor));
		
		final Course updatedCourse = courseService.assignInstructorToCourse(99L, 99L);
		
		Assertions.assertNull(updatedCourse);
		
		Mockito.verify(courseRepository, Mockito.never())
			.save(Mockito.any(Course.class));
	}
	
	@Test
	public void test_assignInstructorToCourse_InstructorIsNull() {
		final Course course = courses.get(0);
		
		Mockito.when(courseRepository.findById(Mockito.anyLong()))
			.thenReturn(Optional.of(course));
		
		Mockito.when(instructorService.getInstructor(Mockito.anyLong()))
			.thenReturn(Optional.empty());
		
		final Course updatedCourse = courseService.assignInstructorToCourse(99L, 99L);
		
		Assertions.assertNull(updatedCourse);
		
		Mockito.verify(courseRepository, Mockito.never())
			.save(Mockito.any(Course.class));
	}
	
	@Test
	public void test_assignInstructorToCourse_instructorAlreadyAssigned() {
		final Course course = courses.get(0);
		
		final Instructor instructor = this.instructor;
		instructor.setCourses(courses);
		
		Mockito.when(courseRepository.findById(Mockito.anyLong()))
			.thenReturn(Optional.of(course));
		
		Mockito.when(instructorService.getInstructor(Mockito.anyLong()))
			.thenReturn(Optional.of(instructor));
		
		final Course updatedCourse = courseService.assignInstructorToCourse(1L, 1L);
		
		Assertions.assertNotNull(updatedCourse);
		
		Mockito.verify(courseRepository, Mockito.times(1))
			.save(Mockito.any(Course.class));
	}
}
