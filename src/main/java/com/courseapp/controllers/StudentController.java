package com.courseapp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.courseapp.entities.Student;
import com.courseapp.services.StudentService;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@RestController
@AllArgsConstructor
public class StudentController {
	
	@NonNull
	private final StudentService studentService;
	
	@GetMapping("/api/students")
	public List<Student> getAllStudents() {
		return studentService.getStudents();
	}
	
	@GetMapping("/api/student/{id}")
	public Optional<Student> getStudent(@PathVariable("id") final Long id) {
		return studentService.getStudentById(id);
	}
	
	@PostMapping("/api/student")
	public Student addStudent(@RequestBody @NonNull final Student student) {
		final Student addedStudent = studentService.addStudent(student);
		
		return addedStudent;
	}
	
	@PostMapping("/api/students")
	public List<Student> addStudents(@NonNull final List<Student> students) {
		final List<Student> addedStudents = studentService.addStudents(students);
		
		return addedStudents;
	}
	
}
