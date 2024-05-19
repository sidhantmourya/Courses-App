package com.courseapp.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.courseapp.entities.Student;
import com.courseapp.services.StudentService;

@RestController
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@GetMapping("/api/students")
	public List<Student> getAllStudents() {
		return studentService.getStudents();
	}
	
	@GetMapping("/api/student/{id}")
	public Optional<Student> getStudent(@PathVariable("id") Long id) {
		return studentService.getStudentById(id);
	}
	
	@PostMapping("/api/student")
	public Student addStudent(@RequestBody Student student) {
		Student addedStudent = studentService.addStudent(student);
		
		return addedStudent;
	}
	
	@PostMapping("/api/students")
	public List<Student> addStudents(List<Student> students) {
		List<Student> addedStudents = studentService.addStudents(students);
		
		return addedStudents;
	}
	
}
