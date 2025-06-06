package com.courseapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.courseapp.entities.Student;
import com.courseapp.entities.Course;
import com.courseapp.entities.Enrollment;
import com.courseapp.repositories.EnrollmentRepository;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

@Service
@AllArgsConstructor
@Log4j2
public class EnrollmentService {

	@NonNull
	private final EnrollmentRepository enrollmentRepo;

	@NonNull
	private final StudentService studentService;

	@NonNull
	private final CourseService courseService;

	@Transactional(readOnly = true)
	public List<Enrollment> getEnrollments() {
		log.info("Fetching all enrollments");
		return enrollmentRepo.findAll();
	}

	@Transactional(readOnly = true)
	public Optional<Enrollment> getEnrollmentById(final Long id) {
		log.info("Fetching enrollment by id={}", id);
		return enrollmentRepo.findById(id);
	}

	@Transactional
	public Enrollment enrollStudent(final Long studentId, final Long courseId) {

		final Optional<Student> studentOptional = studentService.getStudentById(studentId);
		final Optional<Course> courseOptional = courseService.getCourseById(courseId);

		if (studentOptional.isEmpty() || courseOptional.isEmpty()) {
			return null;
		}

		final Student student = studentOptional.get();
		final Course course = courseOptional.get();
		
		log.info("Enrolling student id={} into course id={}", student.getStudentId(), course.getCourseId());

		final Enrollment enrollment = new Enrollment();
		enrollment.setStudent(student);
		enrollment.setCourse(course);

		final Enrollment savedEnrollment = enrollmentRepo.save(enrollment);
		log.info("Enrollment saved: {}", enrollment.getEnrollmentId());
		return savedEnrollment;
	}

	@Transactional(readOnly = true)
	public List<Student> findStudentsByCourseId(final Long id) {
		final Optional<Course> courseOptional = courseService.getCourseById(id);

		if (courseOptional.isEmpty()) {
			return null;
		}

		final Course course = courseOptional.get();

		Optional<List<Student>> studentsOptional = enrollmentRepo.findByCourse(course);

		if (studentsOptional.isEmpty()) {
			return null;
		}

		final List<Student> students = studentsOptional.get();
		
		log.info("Course id={} has the following students={}", course.getCourseId(), students);

		return students;
	}
	
	@Transactional(readOnly = true)
	public List<Course> findCoursesByStudentId(final Long id) {
		final Optional<Student> studentOptional = studentService.getStudentById(id);

		if (studentOptional.isEmpty()) {
			return null;
		}

		final Student student = studentOptional.get();

		final Optional<List<Course>> coursesOptional = enrollmentRepo.findByStudent(student);

		if (coursesOptional.isEmpty()) {
			return null;
		}

		final List<Course> courses = coursesOptional.get();
		
		log.info("Student id={} is enrolled in {}", student.getStudentId(), courses);

		return courses;
	}

}
