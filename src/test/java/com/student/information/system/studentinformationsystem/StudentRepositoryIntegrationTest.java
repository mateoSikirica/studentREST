package com.student.information.system.studentinformationsystem;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.student.information.system.Student;
import com.student.information.system.StudentRepository;

@RunWith(SpringRunner.class)
@DataMongoTest
public class StudentRepositoryIntegrationTest {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void testSaveAndFindStudent() throws Exception {

        Student ragcrix = new Student();
        Map<String, Integer> courseListR = new HashMap<String, Integer>();
        courseListR.put("Math", 2);
        courseListR.put("Science", 3);
        ragcrix.setId("aBc123");
        ragcrix.setName("ragcrix");
        ragcrix.setEmail("ragcrixEmail");
        ragcrix.setStudentNumber(23L);
        ragcrix.setCourseList(courseListR);
        ragcrix.setGpa(ragcrix.calculateGpa(courseListR));

        studentRepository.save(ragcrix);

        Student yigit = new Student();
        Map<String, Integer> courseListY = new HashMap<String, Integer>();
        courseListY.put("Physics", 5);
        courseListY.put("Science", 3);
        yigit.setId("dEf345");
        yigit.setName("yigit");
        yigit.setEmail("yigitEmail");
        yigit.setStudentNumber(24L);
        yigit.setCourseList(courseListY);
        yigit.setGpa(yigit.calculateGpa(courseListY));

        studentRepository.save(yigit);

        assertEquals(ragcrix, studentRepository.findByStudentNumber(23L));
        assertEquals(yigit, studentRepository.findByStudentNumber(24L));
        Assert.assertNull(studentRepository.findByStudentNumber(1L));
    }
}