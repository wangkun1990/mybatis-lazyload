package com.mybatis.service.impl;

import com.mybatis.entity.PageModel;
import com.mybatis.entity.Student;
import com.mybatis.mapper.StudentMapper;
import com.mybatis.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements IStudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Student getStudentById(Integer id) {
        return studentMapper.getStudentById(id);
    }

    @Override
    public Student getStudentListByParams(Student student) {
        return studentMapper.getStudentListByParams(student);
    }

    @Override
    public int insert(Student student) {
        return studentMapper.insert(student);
    }

    @Override
    public List<Student> queryStudentByParams(Student student, PageModel pageModel) {
        return studentMapper.queryStudentByParams(student, pageModel);
    }

    @Override
    public int updateStudent(Student student) {
        return studentMapper.updateStudent(student);
    }

    @Override
    public int deleteStudent(Integer id) {
        return studentMapper.deleteStudent(id);
    }

    @Override
    public Map<Integer, Student> getMapStudents() {
        return studentMapper.getMapStudents();
    }
}
