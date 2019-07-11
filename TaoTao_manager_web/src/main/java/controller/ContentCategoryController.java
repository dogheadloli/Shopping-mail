package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import po.TbContentCategory;
import service.ContentCategoryService;
import utils.TaotaoResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/8/21 0021 19:46
 * 4    内容分类
 */
@RestController
@RequestMapping("/content/category")
public class ContentCategoryController {
    @Autowired
    ContentCategoryService contentCategoryService;

    @RequestMapping("/list")
    public List getContentCategoryList(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
        List catList = new ArrayList();
        List<TbContentCategory> list = contentCategoryService.getContentCategoryList(parentId);
        for (TbContentCategory tbContentCategory : list) {
            Map node = new HashMap<>();
            node.put("id", tbContentCategory.getId());
            node.put("text", tbContentCategory.getName());
            // 如果是父节点的话就设置成关闭状态，如果是叶子节点就是open状态
            node.put("state", tbContentCategory.getIsParent() ? "closed" : "open");
            catList.add(node);
        }
        return catList;
    }

    @RequestMapping("/create")
    @ResponseBody
    public TaotaoResult addContentCategory(Long parentId, String name) {
        TaotaoResult result = contentCategoryService.addContentCategory(parentId, name);
        return result;
    }

    @RequestMapping("/update")
    @ResponseBody
    public TaotaoResult updateContentCategory(Long id, String name) {
        TaotaoResult result = contentCategoryService.updateContentCategory(id, name);
        return result;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public TaotaoResult deleteContenxtCategory(Long id) {
        TaotaoResult result = contentCategoryService.deleteContentCategory(id);
        return result;
    }
}
