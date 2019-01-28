package com.mybatis.service;

import com.mybatis.entity.PageModel;
import com.mybatis.entity.Student;

import java.util.List;
import java.util.Map;

public interface IStudentService {

    Student getStudentById(Integer id);

    Student getStudentListByParams(Student student);

    int insert(Student student);

    List<Student> queryStudentByParams(Student student, PageModel pageModel);

    int updateStudent(Student student);

    int deleteStudent(Integer id);

    Map<Integer, Student> getMapStudents();
}
