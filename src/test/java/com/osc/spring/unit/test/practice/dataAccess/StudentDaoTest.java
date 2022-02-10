package com.osc.spring.unit.test.practice.dataAccess;

import com.osc.spring.unit.test.practice.entities.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StudentDaoTest {

    @Autowired
    private StudentDao studentDao;

    @AfterEach
    void tearDown() {
        studentDao.deleteAll();
    }

    @Test
    void itShouldCheckWhenStudentEmailExists() {

        // given
        String email = "oscngl@gmail.com";
        Student student = new Student("Omer", email);
        studentDao.save(student);

        // when
        boolean expected = studentDao.selectExistsEmail(email);

        // then
        assertThat(expected).isTrue();

    }

    @Test
    void itShouldCheckWhenStudentEmailDoesNotExists() {

        // given
        String email = "oscngl@gmail.com";

        // when
        boolean expected = studentDao.selectExistsEmail(email);

        // then
        assertThat(expected).isFalse();

    }

}