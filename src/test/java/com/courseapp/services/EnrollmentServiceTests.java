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
import com.courseapp.entities.Enrollment;
import com.courseapp.entities.Instructor;
import com.courseapp.entities.Student;
import com.courseapp.repositories.EnrollmentRepository;

public class EnrollmentServiceTests {
	
	@Mock
	private EnrollmentRepository enrollmentRepository;
	
	@Mock
	private StudentService studentService;
	
	@Mock
	private CourseService courseService;
	
	@InjectMocks
	private EnrollmentService enrollmentService;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	private final Student student = Student.builder()
			.studentId(1L)
			.studentName("studentName")
			.sex("M")
			.address("address")
			.build();
	
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
	
	private final List<Enrollment> enrollments = List.of(
			Enrollment.builder()
				.enrollmentId(1L)
				.student(student)
				.course(course)
				.build()
	);
	
	@Test
	public void test_getAllEnrollments_success() {		
		Mockito.when(enrollmentRepository.findAll())
			.thenReturn(enrollments);
		
		final List<Enrollment> fetchedEnrollments = enrollmentService.getEnrollments();
		
		Assertions.assertNotNull(fetchedEnrollments);
		Assertions.assertEquals(enrollments.size(), fetchedEnrollments.size());
		Assertions.assertEquals(enrollments, fetchedEnrollments);
		
		Mockito.verify(enrollmentRepository, Mockito.times(1)).findAll();
	}
	
	@Test
	public void test_getEnrollmentById_success() {
		final Long id = 1L;
		
		final Optional<Enrollment> filteredEnrollmentOptional = enrollments.stream()
				.filter(enrollment -> enrollment.getEnrollmentId().equals(id))
				.findFirst();
		
		final Enrollment filteredEnrollment = filteredEnrollmentOptional.get();
		
		Mockito.when(enrollmentRepository.findById(Mockito.anyLong()))
			.thenReturn(filteredEnrollmentOptional);
		
		final Optional<Enrollment> fetchedEnrollmentOptional = enrollmentService.getEnrollmentById(id);
		
		final Enrollment fetchedEnrollment = fetchedEnrollmentOptional.get();
		
		Assertions.assertNotNull(fetchedEnrollmentOptional);
		Assertions.assertNotNull(fetchedEnrollment);
		Assertions.assertEquals(filteredEnrollment, fetchedEnrollment);
		
		Mockito.verify(enrollmentRepository, Mockito.times(1))
			.findById(Mockito.anyLong());
	}
	
	@Test
	public void test_enrollStudent_success() {
		final Enrollment enrollment = enrollments.get(0);
		final Long studentId = enrollment.getStudent().getStudentId();
		final Long courseId = enrollment.getCourse().getCourseId();
		
		Mockito.when(studentService.getStudentById(Mockito.anyLong()))
			.thenReturn(Optional.of(student));

		Mockito.when(courseService.getCourseById(Mockito.anyLong()))
			.thenReturn(Optional.of(course));
		
		Mockito.when(enrollmentRepository.save(Mockito.any(Enrollment.class)))
			.thenReturn(enrollment);
		
		final Enrollment savedEnrollment = enrollmentService.enrollStudent(studentId, courseId);
		
		Assertions.assertNotNull(savedEnrollment);
		Assertions.assertEquals(savedEnrollment.getEnrollmentId(), enrollment.getEnrollmentId());
		
		Mockito.verify(enrollmentRepository, Mockito.times(1)).save(Mockito.argThat(capturedEnrollment ->
			capturedEnrollment.getStudent().equals(student) &&
			capturedEnrollment.getCourse().equals(course)
		));
		
		Mockito.verify(studentService, Mockito.times(1))
			.getStudentById(studentId);
		
		Mockito.verify(courseService, Mockito.times(1))
			.getCourseById(courseId);
	}
}
