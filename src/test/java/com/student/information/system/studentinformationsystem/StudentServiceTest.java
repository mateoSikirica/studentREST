package com.student.information.system.studentinformationsystem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.student.information.system.Student;
import com.student.information.system.StudentRepository;
import com.student.information.system.StudentService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @MockBean
    private StudentRepository studentRepository;

    private Student ragcrix;
    private Student yigit;

    private final Long ragcrixStudentNumber = 23L;
    private final Long yigitStudentNumber = 91L;
    private final String ragcrixEmail = "ragcrix@rg.com";
    private final String yigitEmail = "yigit@ygt.com";
    private final List<Student> students = new ArrayList<>();



    @Test
    public void testFindAll_thenStudentListShouldBeReturned() {
        Mockito.when(studentRepository.findAll()).thenReturn(students);
        List<Student> foundStudents = studentService.findAll();

        Assert.assertNotNull(foundStudents);
        Assert.assertEquals(2, foundStudents.size());
    }

    @Test
    public void testFindByStudentNumber23_thenRagcrixShouldBeReturned() {
        Mockito.when(studentRepository.findByStudentNumber
                (ragcrixStudentNumber)).thenReturn(ragcrix);
        Student found = studentService.findByStudentNumber(ragcrixStudentNumber);

        Assert.assertNotNull(found);
        Assert.assertEquals(ragcrix.getName(), found.getName());

        Assert.assertEquals(ragcrix.getId(), found.getId());
    }

    @Test
    public void testFindByEmail_thenYigitShouldBeReturned() {
        Mockito.when(studentRepository.findByEmail(yigitEmail))
                .thenReturn(yigit);
        Student found = studentService.findByEmail(yigitEmail);

        Assert.assertNotNull(found);
        Assert.assertEquals(yigit.getName(), found.getName());
        Assert.assertEquals(yigit.getId(), found.getId());
    }

    @Test
    public void testFindAllByOrderByGpaDesc_thenStudentsShouldBeReturned_byGPADesc() {
        Mockito.when(studentRepository.findAllByOrderByGpaDesc())
                .thenReturn(students.stream().sorted(
                        Comparator.comparing(Student::getGpa).reversed()).collect(Collectors.toList()));
        List<Student> foundStudents = studentService.findAllByOrderByGpaDesc();

        Assert.assertNotNull(foundStudents);
        Assert.assertEquals(2, foundStudents.size());
        Assert.assertEquals(yigit.getName(), foundStudents.get(0).getName());
        Assert.assertEquals(yigit.getId(), foundStudents.get(0).getId());
    }

    @Test
    public void testSaveOrUpdateStudent_thenStudentShouldBeReturned() {
        Mockito.when(studentRepository.save(ragcrix)).thenReturn(ragcrix);
        Student found = studentService.saveOrUpdateStudent(ragcrix);

        Assert.assertNotNull(found);
        Assert.assertEquals(ragcrix.getName(), found.getName());
        Assert.assertEquals(ragcrix.getId(), found.getId());
    }

    @Test
    public void testDeleteStudentById() {
        studentService.deleteStudentById(ragcrix.getId());

        Mockito.verify(studentRepository, Mockito.times(1))
                .deleteById(ragcrix.getId());
    }

    @Before
    public void setup()
    {
        ragcrix = new Student();
        Map<String, Integer> courseListR = new HashMap<String, Integer>();
        courseListR.put("Math", 2);
        courseListR.put("Science", 3);
        ragcrix.setId("aBc123");
        ragcrix.setName("ragcrix");
        ragcrix.setEmail(ragcrixEmail);
        ragcrix.setStudentNumber(ragcrixStudentNumber);
        ragcrix.setCourseList(courseListR);
        ragcrix.setGpa(ragcrix.calculateGpa(courseListR));

        yigit = new Student();
        Map<String, Integer> courseListY = new HashMap<String, Integer>();
        courseListY.put("Physics", 5);
        courseListY.put("Science", 3);
        yigit.setId("dEf345");
        yigit.setName("yigit");
        yigit.setEmail(yigitEmail);
        yigit.setStudentNumber(yigitStudentNumber);
        yigit.setCourseList(courseListY);
        yigit.setGpa(yigit.calculateGpa(courseListY));

        students.add(ragcrix);
        students.add(yigit);
    }
}