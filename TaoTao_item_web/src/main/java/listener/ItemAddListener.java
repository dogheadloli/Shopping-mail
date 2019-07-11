package listener;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import po.TbItem;
import po.TbItemDesc;
import pojo.Item;
import service.ItemService;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/9/15 0015 16:55
 * 4    生成静态界面
 */


public class ItemAddListener implements MessageListener {
    @Autowired
    private ItemService itemService;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Value("${HOME_OUT_PATH}")
    private String HOME_OUT_PATH;

    @Override
    public void onMessage(Message message){
        try {

            Long itemId = Long.parseLong(message.toString());
            // 查询
            TbItem tbItem = itemService.getItemById(itemId);
            Item item = new Item(tbItem);
            TbItemDesc itemDesc = itemService.getItemDescById(itemId);
            // 使用freemarker生成静态页面
            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            // 1.创建模板
            // 2.加载模板对象
            Template template = configuration.getTemplate("item.ftl");
            // 3.准备数据
            Map date = new HashMap<>();
            date.put("item", item);
            date.put("itemDesc", itemDesc);
            // 4.创建输出目录及文件名
            Writer out = new FileWriter(new File(HOME_OUT_PATH + itemId + ".html"));
            // 5.生成静态界面
            template.process(date, out);

            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
