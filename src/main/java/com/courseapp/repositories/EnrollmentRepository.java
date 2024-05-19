package com.courseapp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.courseapp.entities.Course;
import com.courseapp.entities.Enrollment;
import com.courseapp.entities.Student;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
	
	@Query("SELECT e.student FROM Enrollment e WHERE e.course = :course")
	public Optional<List<Student>> findByCourse(@Param("course") Course course);
	
	@Query("SELECT e.course FROM Enrollment e WHERE e.student = :student")
	public Optional<List<Course>> findByStudent(@Param("student") Student student);
	
}
