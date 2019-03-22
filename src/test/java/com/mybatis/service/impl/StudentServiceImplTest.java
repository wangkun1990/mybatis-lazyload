package com.mybatis.service.impl;

import com.mybatis.entity.Department;
import com.mybatis.entity.PageModel;
import com.mybatis.entity.Student;
import com.mybatis.service.IStudentService;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-service.xml"})
public class StudentServiceImplTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(StudentServiceImplTest.class);

    @Autowired
    private IStudentService studentService;
    @Test
    public void getStudentById() {
        Student student = studentService.getStudentById(1);
        LOGGER.info("Student Class = {}", student.getClass());
        student.getDepartment().getId();
    }

    @Test
    public void getStudentListByParams() {
        Student student = new Student();
        student.setId(1);
        Student result = studentService.getStudentListByParams(student);
        LOGGER.info("getStudentListByParams result = {}", result);
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
        LOGGER.info("queryStudentByParams result = {}", students);
    }

    @Test
    public void updateStudent() {
        Student student = new Student();
        student.setSex(0);
        student.setId(1);
        //student.setName("hello");
        int result = studentService.updateStudent(student);
        LOGGER.info("updateStudent result = {}", result);
    }

    @Test
    public void deleteStudent() {
        int result = studentService.deleteStudent(18);
        LOGGER.info("deleteStudent result = {}", result);
    }

    @Test
    public void getMapStudents() {
        Map<Integer, Student> result = studentService.getMapStudents();
        LOGGER.info("getMapStudents result = {}", result);
    }

    @Test
    public void selectOne() {
        Student student = studentService.selectOne(1);
        LOGGER.info("selectOne = {}", student);
    }

    @Test
    public void selectByName() {
        List<Student> students = studentService.selectByName("tom");
        LOGGER.info("selectByName = {}", students);
    }

    @Test
    public void selectByIdAndName() {
        Student student = studentService.selectByIdAndName(1, "tom");
        LOGGER.info("selectByIdAndName = {}", student);
    }

    @Test
    public void getByIds() {
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        ids.add(4);
        List<Student> students = studentService.getByIds(ids);
        LOGGER.info("getByIds = {}", students);
    }

    @Test
    public void getByIdsSet() {
        Set<Integer> set = new HashSet<>();
        set.add(1);
        set.add(2);
        List<Student> students = studentService.getByIdsSet(set);
        LOGGER.info("getByIdsSet = {}", students);
    }


    @Test
    public void getByIdsCollection() {
        Collection<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        List<Student> students = studentService.getByIdsCollection(ids);
        LOGGER.info("getByIdsCollection = {}", students);
    }

    @Test
    public void getByIdsArray() {
        Integer[] ids = new Integer[] {1, 2};
        List<Student> students = studentService.getByIdsArray(ids);
        LOGGER.info("getByIdsArray = {}", students);
    }


    @Test
    public void selectByParam() {
        Map<String, Object> map = new HashMap<>();
        map.put("stuId", "1");
        map.put("name", "tom");
        List<Student> students = studentService.selectByParam(map);
        LOGGER.info("selectByParam = {}", students);
    }

    @Test
    public void selectByNames() {
        List<String> names = new ArrayList<>();
        names.add("tom");
        names.add("zhangsan");
        List<Student> students = studentService.selectByNames(names);
        LOGGER.info("selectByNames = {}", students);
    }

    @Test
    public void batchUpdate() {
        List<Student> students = studentService.selectAll();
        for (Student student : students) {
            student.setName(student.getName() + 1);
        }
        int count = studentService.batchUpdate(students);
        LOGGER.info("batchUpdate count = {}", count);
    }

    @Test
    public void batchInsert() {
        List<Student> students = new ArrayList<>();
        Student student = new Student();
        student.setName("qidelong");
        student.setSex(1);
        students.add(student);

        student = new Student();
        student.setName("zhaoben");
        student.setSex(2);
        students.add(student);
        int count = studentService.batchInsert(students);
        LOGGER.info("batchInsert count = {}", count);
    }

    @Test
    public void selectByMulParam() {
        List<Student> students = studentService.selectByMulParam("qidelong", 1);
        LOGGER.info("selectByMulParam = {}", students);
    }

    @Test
    public void selectByMap() {
        Map<String, Object> param = new HashMap<>();
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        ids.add(3);
        param.put("ids", ids);
        List<Integer> sex = new ArrayList<>();
        sex.add(1);
        sex.add(2);
        param.put("sex", sex);
        param.put("name", "jim");
        List<String> names = new ArrayList<>();
        names.add("jim");
        names.add("qidelong");
        names.add("zhaoben");
        names.add("tom");
        param.put("names", names);
        List<Student> students = studentService.selectByMap(param);
        LOGGER.info("selectByMap = {}", students);
    }

    @Test
    public void defaultMethod() {
        studentService.defaultMethod("tom");
    }
}