package com.mybatis.entity;

import java.io.Serializable;

public class Student implements Serializable {


    private static final long serialVersionUID = 1991642052122530042L;
    private Integer id;

    private String name;

    private Department department;

    private Integer sex;

    public Student() {

    }

    public Student(Integer id) {
        this.id = id;
    }

    public Student(Integer id, String name, Integer sex) {
        this.id = id;
        this.name = name;
        this.sex = sex;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department=" + department +
                ", sex=" + sex +
                '}';
    }
}
