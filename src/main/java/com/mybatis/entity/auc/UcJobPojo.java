package com.mybatis.entity.auc;

/**
 * UC 岗位pojo
 */
public class UcJobPojo {

    /**
     * 岗位id
     **/
    private int id;

    /**
     * 岗位名称
     **/
    private String name;

    /**
     * 是否有效 0：有效 1：无效
     **/
    private int mark;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UcJobPojo ucJobPojo = (UcJobPojo) o;

        if (id != ucJobPojo.id) {
            return false;
        }
        return name != null ? name.equals(ucJobPojo.name) : ucJobPojo.name == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UcJobPojo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mark=" + mark +
                '}';
    }
}
