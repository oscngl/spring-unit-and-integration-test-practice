package com.osc.spring.unit.test.practice.business.abstracts;

import com.osc.spring.unit.test.practice.entities.Student;

import java.util.List;

public interface StudentService {

    List<Student> getAll();
    void addStudent(Student student);
    void deleteStudent(int id);

}
