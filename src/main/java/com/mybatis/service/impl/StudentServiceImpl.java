package com.mybatis.service.impl;

import com.mybatis.entity.PageModel;
import com.mybatis.entity.Student;
import com.mybatis.mapper.StudentMapper;
import com.mybatis.service.IStudentService;
import org.apache.commons.chain.Chain;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class StudentServiceImpl implements IStudentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    private StudentMapper studentMapper;

    @Resource
    private Chain chainBase;

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

    @Override
    public Student selectOne(Integer id) {
        LOGGER.info("selectOne param = {}, thread id = {}", id, Thread.currentThread().getId());
        return studentMapper.selectOne(id);
    }

    @Override
    public List<Student> selectByName(String name) {
        return studentMapper.selectByName(name);
    }

    @Override
    public Student selectByIdAndName(Integer id, String name) {
        return studentMapper.selectByIdAndName(id, name);
    }

    @Override
    public List<Student> getByIds(List<Integer> ids) {
        return studentMapper.getByIds(ids);
    }

    @Override
    public List<Student> selectByParam(Map<String, Object> param) {
        return studentMapper.selectByParam(param);
    }

    @Override
    public List<Student> getByIdsSet(Set<Integer> ids) {
        return studentMapper.getByIdsSet(ids);
    }

    @Override
    public List<Student> getByIdsCollection(Collection<Integer> ids) {
        return studentMapper.getByIdsCollection(ids);
    }

    @Override
    public List<Student> getByIdsArray(Integer[] ids) {
        return studentMapper.getByIdsArray(ids);
    }

    @Override
    public List<Student> selectByNames(List<String> names) {
        return studentMapper.selectByNames(names);
    }

    @Override
    public int batchUpdate(List<Student> students) {
        return studentMapper.batchUpdate(students);
    }

    @Override
    public List<Student> selectAll() {
        return studentMapper.selectAll();
    }

    @Override
    public int batchInsert(List<Student> students) {
        return studentMapper.batchInsert(students);
    }

    @Override
    public List<Student> selectByMulParam(String name, Integer sex) {
        return studentMapper.selectByMulParam(name, sex);
    }

    @Override
    public List<Student> selectByMap(Map<String, Object> paramMap) {
        return studentMapper.selectByMap(paramMap);
    }

    /*@Override
    public void defaultMethod(String name) {
        System.out.println("hello " + name);
    }*/

    @Override
    public void filterStudent(Student student) throws Exception {
        Context context = new ContextBase();
        context.put("student", student);
        chainBase.execute(context);
    }
}
