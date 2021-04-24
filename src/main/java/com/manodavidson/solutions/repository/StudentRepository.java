package com.manodavidson.solutions.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.manodavidson.solutions.model.Student;

@Repository
public interface StudentRepository extends CrudRepository<Student, Integer> {
	public List<Student> findByCoursesCourseId(Integer courseId);
}
