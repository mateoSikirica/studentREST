package com.student.information.system;

import java.util.List;
import java.util.Map;

/**
 * @author ragcrix
 */
public class StudentDTO {

    private String id;
    private String name;
    private long studentNumber;
    private String email;
    private Map<String, Integer> courseList;
    private float gpa;

    public StudentDTO() {
    }

    public StudentDTO(String id, String name, long studentNumber, String email,
            Map<String, Integer> courseList, float gpa) {
        this.id = id;
        this.name = name;
        this.studentNumber = studentNumber;
        this.email = email;
        this.courseList = courseList;
        this.gpa = calculateGpa(courseList);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(long studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, Integer> getCourseList() {
        return courseList;
    }

    public void setCourseList(Map<String, Integer> courseList) {
        this.courseList = courseList;
    }

    public float getGpa() {
        return gpa;
    }

    public void setGpa(float gpa) {
        this.gpa = gpa;
    }

    public float calculateGpa(Map<String, Integer> courseList) {
        float sum = 0;
        int numberOfSubjects = 0;
        for (String subject: courseList.keySet()) {
            Integer grade = courseList.get(subject);
            sum += grade;
            numberOfSubjects++;
        }
        return (sum / numberOfSubjects);
    }

}
