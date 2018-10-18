package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import po.TbContent;
import pojo.DataGridResult;
import service.ContentService;
import utils.TaotaoResult;


/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/8/28 0028 21:04
 * 4    内容
 */
@RestController
@RequestMapping("/content")
public class ContentController {
    @Autowired
    ContentService contentService;

    @RequestMapping("/query/list")
    public DataGridResult getContentList(Long categoryId, Integer page, Integer rows) {
        DataGridResult result = contentService.getContentList(categoryId, page, rows);
        return result;
    }

    @RequestMapping("/save")
    public TaotaoResult addContent(TbContent content) {
        TaotaoResult result = contentService.addContent(content);
        return result;
    }
}
