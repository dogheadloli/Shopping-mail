package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.SearchItemService;
import utils.TaotaoResult;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/9/4 0004 21:00
 * 4
 */
@Controller
public class IndexManagerController {
    @Autowired
    private SearchItemService searchItemService;

    @RequestMapping("/index/import")
    @ResponseBody
    public TaotaoResult importIndex() {
        TaotaoResult taotaoResult = searchItemService.importItemsToIndex();
        return taotaoResult;
    }
}
