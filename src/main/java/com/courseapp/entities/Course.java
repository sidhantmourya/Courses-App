package com.courseapp.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@NoArgsConstructor
public class Course {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long courseId;
	
	@NonNull
	private String courseName;
	
	private Integer courseDuration; // duration in hours
	
	
	@ManyToOne
	@JoinColumn(name = "instructorId", nullable = false)
	@JsonIgnoreProperties("courses")
	@NonNull
	private Instructor instructor;
}
