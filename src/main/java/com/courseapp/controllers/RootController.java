package com.courseapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

	@GetMapping("/")
	public String index() {
		return "This is the home page of Course App API";
	}
	
	
	
}
