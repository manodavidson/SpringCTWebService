package com.manodavidson.solutions.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.manodavidson.solutions.model.Course;

@Repository
public interface CoursesRepository extends CrudRepository<Course, Integer> {

}
