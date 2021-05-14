package com.student.information.system.studentinformationsystem;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.student.information.system.Student;
import com.student.information.system.StudentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@AutoConfigureDataMongo
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RestControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private StudentRepository studentRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Student ragcrix;
    private Student yigit;
    private Student stipe;

    @Test
    public void test_getAllStudents_successful() throws Exception {
        mvc.perform(get("/students/").param("id", "1")).andExpect(status().isOk()).andExpect(content().string(
                "[{\"id\":\"aBc123\",\"name\":\"ragcrix\",\"studentNumber\":23,\"email\":\"ragcrix@rg.com\",\"courseList\":{\"Science\":3,\"Math\":2},\"gpa\":2.5},{\"id\":\"dEf345\",\"name\":\"yigit\",\"studentNumber\":91,\"email\":\"yigit@ygt.com\",\"courseList\":{\"Science\":3,\"Physics\":5},\"gpa\":4.0}]"));
    }

    @Test
    public void test_getByStudentNumber_successful() throws Exception {
        mvc.perform(get("/students/byStudentNumber/23").param("id", "1")).andExpect(status().isOk()).andExpect(content().string(
                "{\"id\":\"aBc123\",\"name\":\"ragcrix\",\"studentNumber\":23,\"email\":\"ragcrix@rg.com\","
                        + "\"courseList\":{\"Science\":3,\"Math\":2},\"gpa\":2.5}"));
    }

    @Test
    public void test_getByEmail_successful() throws Exception {
        mvc.perform(get("/students/byEmail/ragcrix@rg.com").param("id", "1")).andExpect(status().isOk()).andExpect(content().string(
                "{\"id\":\"aBc123\",\"name\":\"ragcrix\",\"studentNumber\":23,\"email\":\"ragcrix@rg.com\","
                        + "\"courseList\":{\"Science\":3,\"Math\":2},\"gpa\":2.5}"));
    }

    @Test
    public void test_delete_successful() throws Exception
    {
        mvc.perform(delete("/students/delete/23")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void test_post_successful() throws Exception {
        Long stipeStudentNumber = 333L;
        String stipeEmail = "stipe@s.com";
        stipe = new Student();
        Map<String, Integer> courseListS = new HashMap<String, Integer>();
        courseListS.put("Science", 3);
        courseListS.put("Math", 3);
        stipe.setId("333");
        stipe.setName("stipe");
        stipe.setEmail(stipeEmail);
        stipe.setStudentNumber(stipeStudentNumber);
        stipe.setCourseList(courseListS);
        stipe.setGpa(stipe.calculateGpa(courseListS));

        String jsonString = objectMapper.writeValueAsString(stipe);

        mvc.perform(post("/students/save/")
                .contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isOk());

        assertEquals(stipe.getId(), studentRepository.findByStudentNumber(stipeStudentNumber).getId());
        assertEquals(stipe.getName(), studentRepository.findByStudentNumber(stipeStudentNumber).getName());
        assertEquals(stipe.getEmail(), studentRepository.findByStudentNumber(stipeStudentNumber).getEmail());
        assertEquals(stipe.getStudentNumber(), studentRepository.findByStudentNumber(stipeStudentNumber).getStudentNumber());
        assertEquals(stipe.getCourseList(), studentRepository.findByStudentNumber(stipeStudentNumber).getCourseList());
        assertEquals(stipe.getGpa(), studentRepository.findByStudentNumber(stipeStudentNumber).getGpa());
    }

    @BeforeEach
    public void setup()
    {
        studentRepository.deleteAll();

        Long ragcrixStudentNumber = 23L;
        Long yigitStudentNumber = 91L;

        String ragcrixEmail = "ragcrix@rg.com";
        String yigitEmail = "yigit@ygt.com";

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

        studentRepository.save(ragcrix);
        studentRepository.save(yigit);
    }
}

