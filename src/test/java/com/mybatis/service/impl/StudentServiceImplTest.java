package com.mybatis.service.impl;

import com.mybatis.entity.Department;
import com.mybatis.entity.PageModel;
import com.mybatis.entity.Student;
import com.mybatis.service.IStudentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-service.xml"})
public class StudentServiceImplTest {

    private final Logger logger = LoggerFactory.getLogger(StudentServiceImplTest.class);

    @Autowired
    private IStudentService studentService;
    @Test
    public void getStudentById() {
        Student student = studentService.getStudentById(1);
        logger.info("Student Class = {}", student.getClass());
        student.getDepartment().getId();
    }

    @Test
    public void getStudentListByParams() {
        Student student = new Student();
        student.setId(1);
        Student result = studentService.getStudentListByParams(student);
        logger.info("getStudentListByParams result = {}", result);
    }

    @Test
    public void insert() {
        Student student = new Student();
        Department department = new Department();
        department.setId(1);
        student.setDepartment(department);
        student.setName("tom");
        student.setSex(0);
        studentService.insert(student);
    }

    @Test
    public void queryStudentByParams() {
        Student student = new Student();
        student.setSex(1);
        PageModel pageModel = new PageModel();
        List<Student> students = studentService.queryStudentByParams(student, pageModel);
        logger.info("queryStudentByParams result = {}", students);
    }

    @Test
    public void updateStudent() {
        Student student = new Student();
        student.setSex(0);
        student.setId(1);
        //student.setName("hello");
        int result = studentService.updateStudent(student);
        logger.info("updateStudent result = {}", result);
    }

    @Test
    public void deleteStudent() {
        int result = studentService.deleteStudent(18);
        logger.info("deleteStudent result = {}", result);
    }

    @Test
    public void getMapStudents() {
        Map<Integer, Student> result = studentService.getMapStudents();
        logger.info("getMapStudents result = {}", result);
    }
}