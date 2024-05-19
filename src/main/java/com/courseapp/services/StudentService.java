package com.courseapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.courseapp.entities.Student;
import com.courseapp.repositories.StudentRepository;

@Service
public class StudentService {
	
	@Autowired
	private StudentRepository studentRepo;
	
	
	public List<Student> getStudents() {
		return studentRepo.findAll();
	}

	public Optional<Student> getStudentById(Long id) {
		return studentRepo.findById(id);
	}
	
	public Student addStudent(Student student) {
		Student savedStudent = studentRepo.save(student);
		
		return savedStudent;
	}
	
	public List<Student> addStudents(List<Student> students) {
		List<Student> savedStudents = studentRepo.saveAll(students);
		
		return savedStudents;
	}
	
	
	
}
