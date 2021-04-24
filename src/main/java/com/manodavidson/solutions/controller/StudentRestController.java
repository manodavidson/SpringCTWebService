package com.manodavidson.solutions.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manodavidson.solutions.model.Course;
import com.manodavidson.solutions.model.Student;
import com.manodavidson.solutions.repository.CoursesRepository;
import com.manodavidson.solutions.repository.StudentRepository;

@RestController
public class StudentRestController {

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	CoursesRepository coursesRepository;

	/*
	 * Adding a student to the Database via Rest call
	 */
	@PostMapping("/addStudent")
	public String addStudent(@RequestBody Student student) {
		String responseBody;
		Student returnedData = studentRepository.save(student);
		if (returnedData != null) {
			responseBody = "Successfully added";
		} else {
			responseBody = "Could not insert your data added";
		}
		return responseBody;
	}

	/*
	 * Delete a given Student
	 */
	@DeleteMapping("/deleteStudent/{id}")
	public String deleteStudent(@PathVariable("id") Integer studentId) {
		studentRepository.deleteById(studentId);
		String responseBody;
		if (studentRepository.existsById(studentId)) {
			responseBody = "Could not delete your data";
		} else {
			responseBody = "Successfully deleted";
		}
		return responseBody;
	}

	/*
	 * Allocate a Student to one or more Course
	 */
	@PutMapping("/addCoursesToStudent/{studentId}/{listOfCourses[]}")
	public void addCoursesToStudent(@PathVariable(value = "studentId") Integer studentId,
			@PathVariable(value = "listOfCourses[]") Integer[] listOfCourses) {

		Optional<Student> dbStudent = studentRepository.findById(studentId);
		if (dbStudent.isPresent()) {
			List<Course> courseList = new ArrayList<Course>();
			Student student = dbStudent.get();
			List<Course> studentAlreadyExistingCourses = student.getCourses();

			// If student doesn't have any courses to begin with
			if (studentAlreadyExistingCourses.isEmpty()) {
				for (int i = 0; i < listOfCourses.length; i++) {
					courseList.add(coursesRepository.findById(listOfCourses[i]).get());
				}
				student.setCourses(courseList);
			} else {
//				ListIterator<Course> iteratorCourse = studentAlreadyExistingCourses.listIterator();
//				while(iteratorCourse.hasNext()) {
//					Course iterableCourse = iteratorCourse.next();
//					for (int i = 0; i < listOfCourses.length; i++) {
//						
//					}
//				}
			}
			studentRepository.save(student);

		}
	}

	/*
	 * Retrieving List of Students for a particular course
	 */
	@GetMapping(value = "/getStudentsByCourse/{id}")
	public List<Student> getStudentDataFromCourse(@PathVariable("id") Integer courseId) {
		List<Student> studentList = studentRepository.findByCoursesCourseId(courseId);
		return studentList;
	}

	/*
	 * Retrieve all the students via Rest call
	 */
	@RequestMapping("/students")
	public List<Student> getAllStudents() {
		List<Student> studentList = new ArrayList<>();
		studentRepository.findAll().forEach(studentList::add);
		return studentList;
	}
}
