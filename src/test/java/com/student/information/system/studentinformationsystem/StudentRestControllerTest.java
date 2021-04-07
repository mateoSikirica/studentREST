package com.student.information.system.studentinformationsystem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.information.system.Student;
import com.student.information.system.StudentService;

@RunWith(SpringRunner.class)
@WebMvcTest
public class StudentRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StudentService studentService;

    private ObjectMapper objectMapper = new ObjectMapper();


    private Student ragcrix;
    private Student yigit;

    private final Long ragcrixStudentNumber = 23L;
    private final Long yigitStudentNumber = 91L;
    private final String ragcrixEmail = "ragcrix@rg.com";
    private final String yigitEmail = "yigit@ygt.com";

    @Test
    public void givenStudents_whenGetAllStudents_thenReturnJsonArray() throws Exception {
        given(studentService.findAll()).willReturn(Arrays.asList(ragcrix));

        mvc.perform(get("/students/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(ragcrix.getName())));
    }

    @Test
    public void givenStudent_whenFindByStudentNumber_thenReturnJsonArray() throws Exception {
        given(studentService.findByStudentNumber(ragcrixStudentNumber)).willReturn(ragcrix);

        mvc.perform(get("/students/byStudentNumber/{studentNumber}", ragcrixStudentNumber)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(ragcrix.getName())));
    }

    @Test
    public void givenStudent_whenFindAllByOrderByGpaDesc_thenReturnJsonArray() throws Exception {
        given(studentService.findAllByOrderByGpaDesc()).willReturn(Arrays.asList(yigit, ragcrix));

        mvc.perform(get("/students/orderByGpa/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(yigit.getName())));
    }

    @Test
    public void saveStudent_itShouldReturnStatusOk() throws Exception {
        given(studentService.saveOrUpdateStudent(any(Student.class))).willReturn(yigit);

        String jsonString = objectMapper.writeValueAsString(yigit);

        mvc.perform(post("/students/save/")
                .contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteStudentByStudentNumber_itShouldReturnStatusOk() throws Exception {
        given(studentService.findByStudentNumber(ragcrixStudentNumber)).willReturn(ragcrix);
        Mockito.doNothing().when(studentService).deleteStudentById(any(String.class));

        mvc.perform(delete("/students/delete/{studentNumber}", ragcrixStudentNumber)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
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
    }
}