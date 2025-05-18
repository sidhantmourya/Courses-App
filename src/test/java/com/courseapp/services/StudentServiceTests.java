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

import com.courseapp.entities.Student;
import com.courseapp.repositories.StudentRepository;

public class StudentServiceTests {
	
	@Mock
	private StudentRepository studentRepository;
	
	@InjectMocks
	private StudentService studentService;
	
	private final List<Student> students = List.of(
			new Student(1L, "testName1", "testEmail1@mail.com", "M"),
			new Student(2L, "testName2", "testEmail2@mail.com", "F")
	);
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void test_getAllStudents_success() {
		Mockito.when(studentRepository.findAll())
			.thenReturn(students);
		
		final List<Student> fetchedStudents = studentService.getStudents();
		
		Assertions.assertNotNull(fetchedStudents);
		Assertions.assertEquals(fetchedStudents.size(), students.size());
		
		Mockito.verify(studentRepository, Mockito.times(1))
			.findAll();
	}

	@Test
	public void test_getStudentById_success() {
		final Long id = 1L;
		
		final Optional<Student> filteredStudent = students.stream()
				.filter(student -> student.getStudentId().equals(id))
				.findFirst();
		
		Mockito.when(studentRepository.findById(id))
			.thenReturn(filteredStudent);
		
		final Optional<Student> fetchedStudentOptional = studentService.getStudentById(id);
		final Student fetchedStudent = fetchedStudentOptional.get();
		
		Assertions.assertNotNull(fetchedStudentOptional);
		Assertions.assertNotNull(fetchedStudent);
		Assertions.assertEquals(fetchedStudent.getStudentId(), id);
		
		Mockito.verify(studentRepository, Mockito.times(1))
			.findById(id);
	}
	
	@Test
	public void test_addStudent_success() {
		final Student student = students.get(0);
		
		Mockito.when(studentRepository.save(student))
			.thenReturn(student);
		
		final Student savedStudent = studentService.addStudent(student);
		
		Assertions.assertNotNull(savedStudent);
		
		Mockito.verify(studentRepository, Mockito.times(1))
			.save(student);
	}
	
	@Test
	public void test_addStudent_returnsNull() {
		final Student student = students.get(0);
		
		Mockito.when(studentRepository.findStudentByStudentNameAndAddressAndSex(
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
		.thenReturn(student);
		
		Student savedStudent = studentService.addStudent(student);
		
		Assertions.assertNull(savedStudent);
		
		Mockito.verify(studentRepository, Mockito.times(1))
			.findStudentByStudentNameAndAddressAndSex(
					Mockito.anyString(), Mockito.anyString(), Mockito.anyString()
			);
		
		Mockito.verify(studentRepository, Mockito.never())
			.save(student);
	}
	
	@Test
	public void test_addStudents_success() {
		final Student student = students.get(0);
		
		Mockito.when(studentRepository.findStudentByStudentNameAndAddressAndSex(
				Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
		.thenReturn(student);
		
		final List<Student> savedStudents = studentService.addStudents(students);
		
		Assertions.assertEquals(savedStudents.size(), 0);
		
		Mockito.verify(studentRepository, Mockito.never())
			.save(student);
	}
}
