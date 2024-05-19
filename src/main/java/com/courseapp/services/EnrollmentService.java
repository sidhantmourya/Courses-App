package com.courseapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.courseapp.entities.Student;
import com.courseapp.entities.Course;
import com.courseapp.entities.Enrollment;
import com.courseapp.repositories.EnrollmentRepository;

@Service
public class EnrollmentService {

	@Autowired
	private EnrollmentRepository enrollmentRepo;

	@Autowired
	private StudentService studentService;

	@Autowired
	private CourseService courseService;

	public List<Enrollment> getEnrollments() {
		return enrollmentRepo.findAll();
	}

	public Optional<Enrollment> getEnrollmentById(Long id) {
		return enrollmentRepo.findById(id);
	}

	public Enrollment enrollStudent(Long studentId, Long courseId) {

		Optional<Student> studentOptional = studentService.getStudentById(studentId);
		Optional<Course> courseOptional = courseService.getCourseById(courseId);

		if (studentOptional.isEmpty() || courseOptional.isEmpty()) {
			return null;
		}

		Student student = studentOptional.get();
		Course course = courseOptional.get();

		Enrollment enrollment = new Enrollment();
		enrollment.setStudent(student);
		enrollment.setCourse(course);

		Enrollment savedEnrollment = enrollmentRepo.save(enrollment);
		return savedEnrollment;
	}

	public List<Student> findStudentsByCourseId(Long id) {
		Optional<Course> courseOptional = courseService.getCourseById(id);

		if (courseOptional.isEmpty()) {
			return null;
		}

		Course course = courseOptional.get();

		Optional<List<Student>> studentsOptional = enrollmentRepo.findByCourse(course);

		if (studentsOptional.isEmpty()) {
			return null;
		}

		List<Student> students = studentsOptional.get();

		return students;
	}

	public List<Course> findCoursesByStudentId(Long id) {
		Optional<Student> studentOptional = studentService.getStudentById(id);

		if (studentOptional.isEmpty()) {
			return null;
		}

		Student student = studentOptional.get();

		Optional<List<Course>> coursesOptional = enrollmentRepo.findByStudent(student);

		if (coursesOptional.isEmpty()) {
			return null;
		}

		List<Course> courses = coursesOptional.get();

		return courses;
	}

}
