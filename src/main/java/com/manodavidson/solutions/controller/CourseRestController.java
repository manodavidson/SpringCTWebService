package com.manodavidson.solutions.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.manodavidson.solutions.model.Course;
import com.manodavidson.solutions.repository.CoursesRepository;

@RestController
public class CourseRestController {
	@Autowired
	CoursesRepository courseRepository;

	/*
	 * Add a course to the database via Rest call
	 */
	@RequestMapping(value = "/addCourse", method = RequestMethod.POST)
	public String addCourse(@RequestBody Course course) {
		String responseBody;
		Course returnedData = courseRepository.save(course);
		if (returnedData != null) {
			responseBody = "Successfully added";
		} else {
			responseBody = "Could not insert your data added";
		}
		return responseBody;
	}

	/*
	 * Delete a given course
	 */
	@DeleteMapping("/deletCourse/{id}")
	public String deleteCourse(@PathVariable("id") Integer courseId) {
		courseRepository.deleteById(courseId);
		String responseBody;
		if (courseRepository.existsById(courseId)) {
			responseBody = "Could not delete your data";
		} else {
			responseBody = "Successfully deleted";
		}
		return responseBody;
	}

	/*
	 * Retrieve all the courses via Rest call
	 */
	@RequestMapping("/courses")
	public List<Course> getAllCourses() {
		List<Course> courseList = new ArrayList<Course>();
		courseRepository.findAll().forEach(courseList::add);
		return courseList;
	}
}
