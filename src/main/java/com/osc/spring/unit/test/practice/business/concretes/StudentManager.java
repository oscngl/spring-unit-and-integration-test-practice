package com.osc.spring.unit.test.practice.business.concretes;

import com.osc.spring.unit.test.practice.business.abstracts.StudentService;
import com.osc.spring.unit.test.practice.dataAccess.StudentDao;
import com.osc.spring.unit.test.practice.entities.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentManager implements StudentService {

    private final StudentDao studentDao;

    @Override
    public List<Student> getAll() {
        return studentDao.findAll();
    }

    @Override
    public void addStudent(Student student) {
        if (!studentDao.selectExistsEmail(student.getEmail())) {
            studentDao.save(student);
        }
    }

    @Override
    public void deleteStudent(int id) {
        if(studentDao.existsById(id)) {
            studentDao.deleteById(id);
        }
    }

}
