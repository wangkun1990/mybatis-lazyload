package com.mybatis.service;

import com.mybatis.entity.PageModel;
import com.mybatis.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IStudentService {

    Logger LOGGER = LoggerFactory.getLogger(IStudentService.class);

    /**
     *
     * @param id
     * @return
     */
    Student getStudentById(Integer id);

    /**
     *
     * @param student
     * @return
     */
    Student getStudentListByParams(Student student);

    /**
     *
     * @param student
     * @return
     */
    int insert(Student student);

    /**
     *
     * @param student
     * @param pageModel
     * @return
     */
    List<Student> queryStudentByParams(Student student, PageModel pageModel);

    /**
     *
     * @param student
     * @return
     */
    int updateStudent(Student student);

    /**
     *
     * @param id
     * @return
     */
    int deleteStudent(Integer id);

    /**
     *
     * @return
     */
    Map<Integer, Student> getMapStudents();

    /**
     *
     * @param id
     * @return
     */
    Student selectOne(Integer id);

    /**
     *
     * @param name
     * @return
     */
    List<Student> selectByName(String name);

    /**
     *
     * @param id
     * @param name
     * @return
     */
    Student selectByIdAndName(Integer id, String name);

    /**
     *
     * @param ids
     * @return
     */
    List<Student> getByIds(List<Integer> ids);

    List<Student> selectByParam(Map<String, Object> param);

    List<Student> getByIdsSet(Set<Integer> ids);

    List<Student> getByIdsCollection(Collection<Integer> ids);

    List<Student> getByIdsArray(Integer[] ids);

    List<Student> selectByNames(List<String> names);

    int batchUpdate(List<Student> students);

    List<Student> selectAll();

    int batchInsert(List<Student> students);

    /**
     *
     * @param name
     * @param sex
     * @return
     */
    List<Student> selectByMulParam(String name, Integer sex);

    /**
     *
     * @param paramMap
     * @return
     */
    List<Student> selectByMap(Map<String, Object> paramMap);

    default void defaultMethod(String name) {
        LOGGER.info("defaultMethod param = {}", name);
    }

    default void defaultMethod2() {

    }

    void filterStudent(Student student) throws Exception;
}
