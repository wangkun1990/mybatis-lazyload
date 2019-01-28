package com.mybatis.support;

import org.mvel2.MVEL;
import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.TemplateCompiler;
import org.mvel2.templates.TemplateRuntime;

import java.util.HashMap;
import java.util.Map;

public class MvelExpressTest {

    public static void main(String[] args) {
        String template = "Hello, my name is @{name.toUpperCase()}";
        Map<String, String> vars = new HashMap();
        vars.put("name", "Michael");

        String output = (String) TemplateRuntime.eval(template, vars);
        System.out.println(output);

        String template1 = "1 + 1 = @{3+3}";

        // compile the template
        CompiledTemplate compiled = TemplateCompiler.compileTemplate(template1);

        // execute the template
        String output1 = (String) TemplateRuntime.execute(compiled);
        System.out.println(output1);



        Map<String, Object> param = new HashMap();
        param.put("MvelExpress1",MvelExpress.class);
        param.put("c", 1);
        param.put("d", 3);
        String calculate = MVEL.evalToString("MvelExpress1.add(c,d)", param);
        System.out.println(calculate);


    }
}

