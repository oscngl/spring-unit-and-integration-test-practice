package com.osc.spring.unit.test.practice.business.concretes;

import com.osc.spring.unit.test.practice.dataAccess.StudentDao;
import com.osc.spring.unit.test.practice.entities.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentManagerTest {

    @Mock
    private StudentDao studentDao;
    private StudentManager studentManager;

    @BeforeEach
    void setUp() {
        studentManager = new StudentManager(studentDao);
    }

    @Test
    void canGetAll() {

        // when
        studentManager.getAll();

        // then
        verify(studentDao).findAll();

    }

    @Test
    void canAddStudent() {

        // given
        Student student = new Student("Omer", "oscngl@gmail.com");

        // when
        studentManager.addStudent(student);

        // then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentDao).save(studentArgumentCaptor.capture());
        Student capturedStudent = studentArgumentCaptor.getValue();
        assertThat(capturedStudent).isEqualTo(student);

    }

    @Test
    void canDeleteStudent() {

        // given
        int id = 10;
        given(studentDao.existsById(id)).willReturn(true);

        // when
        studentManager.deleteStudent(id);

        // then
        verify(studentDao).deleteById(id);

    }

}