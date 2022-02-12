package com.osc.spring.unit.test.practice.api.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.osc.spring.unit.test.practice.dataAccess.StudentDao;
import com.osc.spring.unit.test.practice.entities.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StudentsControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StudentDao studentDao;

    private final Faker faker = new Faker();

    @Test
    void canRegisterNewStudent() throws Exception {

        // given
        String name = String.format(
                "%s %s",
                faker.name().firstName(),
                faker.name().lastName()
        );

        Student student = new Student(
                name,
                String.format("%s@gmail.com",
                        StringUtils.trimAllWhitespace(name.trim().toLowerCase()))
        );

        // when
        ResultActions resultActions = mockMvc
                .perform(post("/api/students/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student))
                );

        // then
        resultActions.andExpect(status().isOk());
        List<Student> students = studentDao.findAll();
        assertThat(students)
                .usingElementComparatorIgnoringFields("id")
                .contains(student);

    }

    @Test
    void canDeleteStudent() throws Exception {

        // given
        String name = String.format(
                "%s %s",
                faker.name().firstName(),
                faker.name().lastName()
        );

        String email = String.format("%s@gmail.com",
                StringUtils.trimAllWhitespace(name.trim().toLowerCase()));

        Student student = new Student(name, email);

        mockMvc.perform(post("/api/students/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                        .andExpect(status().isOk());

        MvcResult getStudentResult = mockMvc.perform(get("/api/students/getAll")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();

        String contentAsString = getStudentResult
                .getResponse()
                .getContentAsString();

        List<Student> students = objectMapper.readValue(
                contentAsString,
                new TypeReference<>() {}
        );

        int id = students.stream()
                .filter(s -> s.getEmail().equals(student.getEmail()))
                .map(Student::getId)
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException("Student with email: " + email + " not found"));

        // when
        ResultActions resultActions = mockMvc
                .perform(delete(("/api/students/delete/" + id)));

        // then
        resultActions.andExpect(status().isOk());
        boolean exists = studentDao.existsById(id);
        assertThat(exists).isFalse();

    }

}