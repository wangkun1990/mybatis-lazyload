package com.mybatis.entity.auc;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

public class NewDeptInfo extends BaseRowModel {

    /**
     * 新公司名称
     */
    @ExcelProperty(index = 0)
    private String newCompanyName;

    @ExcelProperty(index = 1)
    private String newFirstDepName;

    @ExcelProperty(index = 2)
    private String newSecondDepName;

    @ExcelProperty(index = 3)
    private String newThirdDepName;


    /**
     * 新岗位名称
     */
    @ExcelProperty(index = 4)
    private String newPosition;


    /**
     * 新职位全路径
     */
    @ExcelProperty(index = 5)
    private String newJobFullName;

    @ExcelProperty(index = 6)
    private String newDepId;



    @ExcelProperty(index = 7)
    private String newJobId;


    @ExcelProperty(index = 8)
    private String oldCompanyName;

    @ExcelProperty(index = 9)
    private String oldFirstDepName;

    @ExcelProperty(index = 10)
    private String oldSecondDepName;

    /**
     * 旧三级部门
     */
    @ExcelProperty(index = 11)
    private String oldThirdDepName;

    /**
     * 旧岗位
     */
    @ExcelProperty(index = 12)
    private String oldPosition;




    /**
     * 旧职位全路径
     */
    @ExcelProperty(index = 13)
    private String oldJobFullName;

    /**
     * 旧部门id
     */
    @ExcelProperty(index = 14)
    private String oldDepId;



    /**
     * 旧职位id
     */
    @ExcelProperty(index = 15)
    private String oldJobId;


    public String getNewCompanyName() {
        return newCompanyName;
    }

    public void setNewCompanyName(String newCompanyName) {
        this.newCompanyName = newCompanyName;
    }

    public String getNewFirstDepName() {
        return newFirstDepName;
    }

    public void setNewFirstDepName(String newFirstDepName) {
        this.newFirstDepName = newFirstDepName;
    }

    public String getNewSecondDepName() {
        return newSecondDepName;
    }

    public void setNewSecondDepName(String newSecondDepName) {
        this.newSecondDepName = newSecondDepName;
    }

    public String getNewThirdDepName() {
        return newThirdDepName;
    }

    public void setNewThirdDepName(String newThirdDepName) {
        this.newThirdDepName = newThirdDepName;
    }

    public String getNewPosition() {
        return newPosition;
    }

    public void setNewPosition(String newPosition) {
        this.newPosition = newPosition;
    }



    public String getNewDepId() {
        return newDepId;
    }

    public void setNewDepId(String newDepId) {
        this.newDepId = newDepId;
    }

    public String getNewJobFullName() {
        return newJobFullName;
    }

    public void setNewJobFullName(String newJobFullName) {
        this.newJobFullName = newJobFullName;
    }

    public String getNewJobId() {
        return newJobId;
    }

    public void setNewJobId(String newJobId) {
        this.newJobId = newJobId;
    }

    public String getOldCompanyName() {
        return oldCompanyName;
    }

    public void setOldCompanyName(String oldCompanyName) {
        this.oldCompanyName = oldCompanyName;
    }

    public String getOldFirstDepName() {
        return oldFirstDepName;
    }

    public void setOldFirstDepName(String oldFirstDepName) {
        this.oldFirstDepName = oldFirstDepName;
    }

    public String getOldSecondDepName() {
        return oldSecondDepName;
    }

    public void setOldSecondDepName(String oldSecondDepName) {
        this.oldSecondDepName = oldSecondDepName;
    }

    public String getOldThirdDepName() {
        return oldThirdDepName;
    }

    public void setOldThirdDepName(String oldThirdDepName) {
        this.oldThirdDepName = oldThirdDepName;
    }

    public String getOldPosition() {
        return oldPosition;
    }

    public void setOldPosition(String oldPosition) {
        this.oldPosition = oldPosition;
    }

    public String getOldDepId() {
        return oldDepId;
    }

    public void setOldDepId(String oldDepId) {
        this.oldDepId = oldDepId;
    }

    public String getOldJobFullName() {
        return oldJobFullName;
    }

    public void setOldJobFullName(String oldJobFullName) {
        this.oldJobFullName = oldJobFullName;
    }

    public String getOldJobId() {
        return oldJobId;
    }

    public void setOldJobId(String oldJobId) {
        this.oldJobId = oldJobId;
    }
}
