package com.student.information.system.studentinformationsystem;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.student.information.system.Student;
import com.student.information.system.StudentRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentRestControllerIntegrationTest {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void testSaveAndFindTicket() throws Exception {
        long studentNumberRagcrix = 23L;
        long studentNumberYigit = 24L;
        Student ragcrix = new Student();
        Map<String, Integer> courseListR = new HashMap<String, Integer>();
        courseListR.put("Math", 2);
        courseListR.put("Science", 3);
        ragcrix.setId("aBc123");
        ragcrix.setName("ragcrix");
        ragcrix.setEmail("ragcrixEmail");
        ragcrix.setStudentNumber(studentNumberRagcrix);
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
        yigit.setStudentNumber(studentNumberYigit);
        yigit.setCourseList(courseListY);
        yigit.setGpa(yigit.calculateGpa(courseListY));

        studentRepository.save(yigit);

        assertEquals(ragcrix.getId(), studentRepository.findByStudentNumber(studentNumberRagcrix).getId());
        assertEquals(yigit.getId(), studentRepository.findByStudentNumber(studentNumberYigit).getId());
    }
}