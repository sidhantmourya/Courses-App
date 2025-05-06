package com.courseapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.courseapp.entities.Student;
import com.courseapp.entities.Course;
import com.courseapp.entities.Enrollment;
import com.courseapp.repositories.EnrollmentRepository;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@AllArgsConstructor
public class EnrollmentService {

	@NonNull
	private final EnrollmentRepository enrollmentRepo;

	@NonNull
	private final StudentService studentService;

	@NonNull
	private final CourseService courseService;

	public List<Enrollment> getEnrollments() {
		return enrollmentRepo.findAll();
	}

	public Optional<Enrollment> getEnrollmentById(final Long id) {
		return enrollmentRepo.findById(id);
	}

	public Enrollment enrollStudent(final Long studentId, final Long courseId) {

		final Optional<Student> studentOptional = studentService.getStudentById(studentId);
		final Optional<Course> courseOptional = courseService.getCourseById(courseId);

		if (studentOptional.isEmpty() || courseOptional.isEmpty()) {
			return null;
		}

		final Student student = studentOptional.get();
		final Course course = courseOptional.get();

		final Enrollment enrollment = new Enrollment();
		enrollment.setStudent(student);
		enrollment.setCourse(course);

		final Enrollment savedEnrollment = enrollmentRepo.save(enrollment);
		return savedEnrollment;
	}

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

		List<Student> students = studentsOptional.get();

		return students;
	}

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

		return courses;
	}

}
