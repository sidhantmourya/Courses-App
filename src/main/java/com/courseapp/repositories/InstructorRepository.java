package com.courseapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.courseapp.entities.Instructor;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {

}
