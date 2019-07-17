package com.mybatis.chain;

import com.alibaba.fastjson.JSON;
import com.mybatis.entity.Student;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * return true结束
 * return false 进入下一个过滤
 */
public class StudentNameChain implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentNameChain.class);

    @Override
    public boolean execute(Context context) throws Exception {
        Student student = (Student) context.get("student");
        LOGGER.info("StudentNameChain param = {}", JSON.toJSONString(student));
        if (student.getName().contains("张")) {
            return true;
        }
        return false;
    }
}
