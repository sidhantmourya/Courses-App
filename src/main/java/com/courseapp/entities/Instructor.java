package com.courseapp.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Instructor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NonNull
	private Long instructorId;
	
	@NonNull
	private String instructorName;
	
	@NonNull
	private String instructorEmail;
	
	@OneToMany(mappedBy = "instructor")
	@JsonIgnoreProperties("instructor")
	@NonNull
	private List<Course> courses;
	
}
