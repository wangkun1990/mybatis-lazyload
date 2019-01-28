package com.mybatis.mapper;

import com.mybatis.entity.PageModel;
import com.mybatis.entity.Student;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StudentMapper {

    Student getStudentById(Integer id);

    /**
     *
     * @param student
     * @return
     */
    Student getStudentListByParams(@Param("student") Student student);

    int insert(Student student);

    /**
     * 根据条件查询
     * @param student
     * @param pageModel
     * @return
     */
    List<Student> queryStudentByParams(Student student, PageModel pageModel);

    int updateStudent(Student student);

    int deleteStudent(Integer id);

    @MapKey("id")
    Map<Integer, Student> getMapStudents();
}
