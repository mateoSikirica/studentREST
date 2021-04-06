package com.student.information.system.studentinformationsystem;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import com.student.information.system.Student;
import com.student.information.system.StudentRepository;
import com.student.information.system.StudentService;

@RunWith(SpringRunner.class)
@DataMongoTest
public class StudentRepositoryIntegrationTest {


    private final Long ragcrixStudentNumber = 23L;
    private final String ragcrixEmail = "ragcrix@rg.com";

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void whenFindByName_thenReturnEmployee() {

        Student ragcrix = new Student();
        Map<String, Integer> courseListR = new HashMap<String, Integer>();
        courseListR.put("Math", 2);
        courseListR.put("Science", 3);
        ragcrix.setId("aBc123");
        ragcrix.setName("ragcrix");
        ragcrix.setEmail(ragcrixEmail);
        ragcrix.setStudentNumber(ragcrixStudentNumber);
        ragcrix.setCourseList(courseListR);
        ragcrix.setGpa(ragcrix.calculateGpa(courseListR));
        entityManager.persist(ragcrix);
        entityManager.flush();

        // when
        Student found = studentRepository.findByStudentNumber(ragcrix.getStudentNumber());

        // then
        Assert.assertEquals(found.getStudentNumber()
                ,ragcrix.getStudentNumber());
    }
}