package controller;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/9/15 0015 15:08
 * 4    网页静态化处理controller
 */
@Controller
public class HtmlGenController {
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @RequestMapping("/genhtml")
    @ResponseBody
    //生成静态界面
    public String genHtml() throws IOException, TemplateException {
        //生成静态界面
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Template template = configuration.getTemplate("hello.ftl");
        Map data = new HashMap<>();
        data.put("hello", "hello Spring");
        Writer out = new FileWriter(new File("F:\\logs\\helloSP.txt"));
        template.process(data, out);
        out.close();
        //返回结果
        return "ok";
    }
}
