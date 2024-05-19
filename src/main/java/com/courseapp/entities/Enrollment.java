package com.courseapp.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Enrollment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long enrollmentId;
	
	@ManyToOne
	@JoinColumn(name = "studentId")
	private Student student;
	
	@ManyToOne
	@JoinColumn(name = "courseId")
	private Course course;
	
	public Long getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	@Override
	public String toString() {
		return "Enrollment [enrollmentId=" + enrollmentId + ", student=" + student.toString() + ", course=" + course.toString() + "]";
	}
	
	
}
