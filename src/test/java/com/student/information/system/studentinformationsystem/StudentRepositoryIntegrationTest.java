package com.student.information.system.studentinformationsystem;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.student.information.system.Student;
import com.student.information.system.StudentRepository;

//@RunWith(SpringRunner.class)
@DataMongoTest
@RunWith(SpringJUnit4ClassRunner.class)
public class StudentRepositoryIntegrationTest {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void testSaveAndFindStudent() throws Exception {
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
        assertEquals(ragcrix.getName(), studentRepository.findByStudentNumber(studentNumberRagcrix).getName());
        assertEquals(ragcrix.getEmail(), studentRepository.findByStudentNumber(studentNumberRagcrix).getEmail());
        assertEquals(ragcrix.getStudentNumber(), studentRepository.findByStudentNumber(studentNumberRagcrix).getStudentNumber());
        assertEquals(ragcrix.getCourseList(), studentRepository.findByStudentNumber(studentNumberRagcrix).getCourseList());
        assertEquals(ragcrix.getGpa(), studentRepository.findByStudentNumber(studentNumberRagcrix).getGpa());

        assertEquals(yigit.getId(), studentRepository.findByStudentNumber(studentNumberYigit).getId());
        assertEquals(yigit.getName(), studentRepository.findByStudentNumber(studentNumberYigit).getName());
        assertEquals(yigit.getEmail(), studentRepository.findByStudentNumber(studentNumberYigit).getEmail());
        assertEquals(yigit.getStudentNumber(), studentRepository.findByStudentNumber(studentNumberYigit).getStudentNumber());
        assertEquals(yigit.getCourseList(), studentRepository.findByStudentNumber(studentNumberYigit).getCourseList());
        assertEquals(yigit.getGpa(), studentRepository.findByStudentNumber(studentNumberYigit).getGpa());

        Assert.assertNull(studentRepository.findByStudentNumber(1L));
    }
}