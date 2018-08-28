package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pojo.DataGridResult;
import service.ContentService;


/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/8/28 0028 21:04
 * 4
 */
@RestController
@RequestMapping("/content/query")
public class ContentController {
    @Autowired
    ContentService contentService;

    @RequestMapping("/list")
    public DataGridResult getContentList(Long categoryId, Integer page, Integer rows) {
        DataGridResult result = contentService.getContentList(categoryId, page, rows);
        return result;
    }
}
