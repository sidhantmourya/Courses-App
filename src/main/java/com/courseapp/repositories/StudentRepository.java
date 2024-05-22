package com.courseapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.courseapp.entities.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
	public Student findStudentByStudentNameAndAddressAndSex(String name, String address, String sex);
}
