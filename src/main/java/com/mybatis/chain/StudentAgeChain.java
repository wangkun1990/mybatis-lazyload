package com.mybatis.chain;

import com.alibaba.fastjson.JSON;
import com.mybatis.entity.Student;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StudentAgeChain implements Command {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentAgeChain.class);
    @Override
    public boolean execute(Context context) throws Exception {
        Student student = (Student) context.get("student");
        LOGGER.info("StudentAgeChain param = {}", JSON.toJSONString(student));
        if (student.getAge() < 18) {
            return true;
        }
        return false;
    }
}
