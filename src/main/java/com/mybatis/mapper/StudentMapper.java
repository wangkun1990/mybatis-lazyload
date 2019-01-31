package com.mybatis.mapper;

import com.mybatis.entity.PageModel;
import com.mybatis.entity.Student;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface StudentMapper {

    /**
     *
     * @param id
     * @return
     */
    Student getStudentById(Integer id);

    /**
     *
     * @param id
     * @return
     */
    Student selectOne(Integer id);

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
    Student selectByIdAndName(@Param("id") Integer id, @Param("name") String name);


    /**
     *
     * @param ids
     * @return
     */
    List<Student> getByIds(List<Integer> ids);

    List<Student> getByIdsSet(Set<Integer> ids);

    List<Student> getByIdsCollection(Collection<Integer> ids);

    List<Student> getByIdsArray(Integer[] ids);

    /**
     *
     * @param param
     * @return
     */
    List<Student> selectByParam(Map<String, Object> param);

    /**
     *
     * @param names
     * @return
     */
    List<Student> selectByNames(List<String> names);
}
