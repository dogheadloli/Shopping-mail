package freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/9/15 0015 13:14
 * 4
 */
public class TestFreeMarker {
    @Test
    public void testFreemarker() throws IOException, TemplateException {
        //1.创建模板文件
        //2.创建一个Configuration对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        //3.设置模板所在路径
        configuration.setDirectoryForTemplateLoading(new File("E:\\Workspace\\TaoTao_parent\\TaoTao_item_web\\src\\main\\webapp\\WEB-INF\\ftl"));
        //4.设置字符集utf-8
        configuration.setDefaultEncoding("utf-8");
        //5.使用Configuration对象加载一个文件,需要制定模板文件文件名
        Template template = configuration.getTemplate("hello.ftl");
        //6.创建一个数据集,可以是pojo或map
        Map date = new HashMap<>();
        date.put("hello", "hello freemarker");
        //7.创建一个Writter对象，制定输出文件路径及文件名
        Writer out = new FileWriter(new File("F:\\logs\\hello.txt"));
        //8.使用模板对象的process方法输出文件
        template.process(date, out);
        //9.关闭流
        out.close();
    }
}
