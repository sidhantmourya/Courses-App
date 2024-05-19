package com.courseapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.courseapp.entities.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
