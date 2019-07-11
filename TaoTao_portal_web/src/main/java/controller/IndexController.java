package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import po.TbContent;
import pojo.AD1Node;
import service.ContentService;
import utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/8/18 0018 11:22
 * 4    首页处理
 */
@Controller
public class IndexController {
    @Value("${AD1_CATEGORY_ID}")
    private Integer AD1_CATEGORY_ID;
    @Value("${AD1_WIDTH}")
    private Integer AD1_WIDTH;
    @Value("${AD1_WIDTH_B}")
    private Integer AD1_WIDTH_B;
    @Value("${AD1_HEIGHT}")
    private Integer AD1_HEIGHT;
    @Value("${AD1_HEIGHT_B}")
    private Integer AD1_HEIGHT_B;
    @Autowired
    private ContentService contentService;

    @RequestMapping("/index")
    public String showIndex(Model model) {
        // 根据cid查询内容列表
        List<TbContent> contentList = contentService.getContentByCid(AD1_CATEGORY_ID);
        // 把列表转换
        List<AD1Node> ad1Nodes = new ArrayList<>();
        for (TbContent tbContent : contentList) {
            AD1Node node = new AD1Node();
            node.setAlt(tbContent.getTitle());
            node.setHeight(AD1_HEIGHT);
            node.setHeightB(AD1_HEIGHT_B);
            node.setWidth(AD1_WIDTH);
            node.setWidthB(AD1_WIDTH_B);
            node.setSrc(tbContent.getPic());
            node.setSrcB(tbContent.getPic2());
            node.setHref(tbContent.getUrl());
            ad1Nodes.add(node);
        }
        // 转换成json
        String ad1Jaon = JsonUtils.objectToJson(ad1Nodes);
        model.addAttribute("ad1", ad1Jaon);
        return "index";
    }
}
