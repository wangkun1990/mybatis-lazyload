package com.mybatis.entity.auc;

import java.io.Serializable;


public class UcDeptJob implements Serializable {

    private static final long serialVersionUID = 3712697001313428910L;


    /**
     * 部门id
     */
    private Integer deptId;

    /**
     * 岗位id
     */
    private Integer jobId;

    /**
     * 岗位名称
     */
    private String jobName;

    /**
     * 部门全称+岗位名称
     */
    private String name;

    /**
     * 部门全称
     */
    private String deptName;

    /**
     * 是否有效 0：有效 1：无效
     */
    private Integer delFlag;

    /**
     * 部门职务id
     */
    private Integer deptJobId;

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getDeptJobId() {
        return deptJobId;
    }

    public void setDeptJobId(Integer deptJobId) {
        this.deptJobId = deptJobId;
    }

    @Override
    public String toString() {
        return "UcDeptJob{" +
                "deptId=" + deptId +
                ", jobId=" + jobId +
                ", jobName='" + jobName + '\'' +
                ", name='" + name + '\'' +
                ", deptName='" + deptName + '\'' +
                ", delFlag=" + delFlag +
                ", deptJobId=" + deptJobId +
                '}';
    }
}