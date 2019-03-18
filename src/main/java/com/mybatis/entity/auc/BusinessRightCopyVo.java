package com.mybatis.entity.auc;


import java.util.Date;
import java.util.List;

public class BusinessRightCopyVo {


    private Integer id;

    /**
     * 鉴权类型id
     */
    private List<Integer> objUserIds;

    private List<Integer> srcUserIds;

    /**
     * 鉴权类型名称
     */
    private List<UcDeptJob> objPositions;

    private List<UcDeptJob> srcPositions;

    private List<UcJobPojo> objJobs;

    private List<UcJobPojo> srcJobs;

    private Integer rightType;

    private Integer operaterId;

    private String operaterName;

    private Date addedTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Integer> getObjUserIds() {
        return objUserIds;
    }

    public void setObjUserIds(List<Integer> objUserIds) {
        this.objUserIds = objUserIds;
    }

    public List<Integer> getSrcUserIds() {
        return srcUserIds;
    }

    public void setSrcUserIds(List<Integer> srcUserIds) {
        this.srcUserIds = srcUserIds;
    }

    public List<UcDeptJob> getObjPositions() {
        return objPositions;
    }

    public void setObjPositions(List<UcDeptJob> objPositions) {
        this.objPositions = objPositions;
    }

    public List<UcDeptJob> getSrcPositions() {
        return srcPositions;
    }

    public void setSrcPositions(List<UcDeptJob> srcPositions) {
        this.srcPositions = srcPositions;
    }

    public List<UcJobPojo> getObjJobs() {
        return objJobs;
    }

    public void setObjJobs(List<UcJobPojo> objJobs) {
        this.objJobs = objJobs;
    }

    public List<UcJobPojo> getSrcJobs() {
        return srcJobs;
    }

    public void setSrcJobs(List<UcJobPojo> srcJobs) {
        this.srcJobs = srcJobs;
    }

    public Integer getRightType() {
        return rightType;
    }

    public void setRightType(Integer rightType) {
        this.rightType = rightType;
    }

    public Integer getOperaterId() {
        return operaterId;
    }

    public void setOperaterId(Integer operaterId) {
        this.operaterId = operaterId;
    }

    public String getOperaterName() {
        return operaterName;
    }

    public void setOperaterName(String operaterName) {
        this.operaterName = operaterName;
    }

    public Date getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(Date addedTime) {
        this.addedTime = addedTime;
    }

    @Override
    public String toString() {
        return "BusinessRightCopyVo{" +
                "id=" + id +
                ", objUserIds=" + objUserIds +
                ", srcUserIds=" + srcUserIds +
                ", objPositions=" + objPositions +
                ", srcPositions=" + srcPositions +
                ", objJobs=" + objJobs +
                ", srcJobs=" + srcJobs +
                ", rightType=" + rightType +
                ", operaterId=" + operaterId +
                ", operaterName='" + operaterName + '\'' +
                ", addedTime=" + addedTime +
                '}';
    }
}
