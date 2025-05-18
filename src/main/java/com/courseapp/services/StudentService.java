package com.courseapp.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.courseapp.entities.Student;
import com.courseapp.repositories.StudentRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class StudentService {
	
	@NonNull
	private final StudentRepository studentRepo;
	
	
	public List<Student> getStudents() {
		log.info("Fetching all students");
		return studentRepo.findAll();
	}

	public Optional<Student> getStudentById(final Long id) {
		log.info("Fetching student with id={}", id);
		return studentRepo.findById(id);
	}
	
	public boolean checkIfStudentExists(@NonNull final Student student) {
		final Student studentFound = studentRepo.findStudentByStudentNameAndAddressAndSex(
				student.getStudentName(), student.getAddress(), student.getSex()
		);
		
		return !Objects.isNull(studentFound);
	}
	
	public Student addStudent(@NonNull final Student student) {
		// check if a student is already registered
		
		if (!checkIfStudentExists(student)) {
			final Student savedStudent = studentRepo.save(student);
			
			log.info("Saved a student with id={}", savedStudent.getStudentId());
			return savedStudent;
		}
		
		return null;
	}
	
	public List<Student> addStudents(@NonNull final List<Student> students) {
		final List<Student> distinctStudents = students.stream()
				.filter(Objects::nonNull)
				.filter(student -> !checkIfStudentExists(student))
				.toList();
		
		final List<Student> savedStudents = distinctStudents.stream()
			.map(this::addStudent)
			.filter(Objects::nonNull)
			.toList();
		
		
		log.info("Saved {} students", savedStudents.size());
		return savedStudents;
	}
	
	
	
}
