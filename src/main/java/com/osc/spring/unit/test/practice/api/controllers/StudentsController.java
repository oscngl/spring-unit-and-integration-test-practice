package com.osc.spring.unit.test.practice.api.controllers;

import com.osc.spring.unit.test.practice.business.abstracts.StudentService;
import com.osc.spring.unit.test.practice.entities.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentsController {

    private final StudentService studentService;

    @GetMapping("/getAll")
    public List<Student> getAllStudents() {
        return studentService.getAll();
    }

    @PostMapping("/add")
    public void addStudent(@RequestBody Student student) {
        studentService.addStudent(student);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteStudent(@PathVariable("id") int id) {
        studentService.deleteStudent(id);
    }

}
