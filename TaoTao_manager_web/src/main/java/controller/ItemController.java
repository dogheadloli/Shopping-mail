package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import po.TbItem;
import pojo.DataGridResult;
import service.ItemService;
import utils.TaotaoResult;


/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/8/3 0003 16:49
 * 4    商品
 */
@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/{itemId}")
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId) {
        TbItem tbItem = itemService.getItemById(itemId);
        return tbItem;
    }

    @RequestMapping("/list")
    @ResponseBody
    public DataGridResult getItemList(Integer page, Integer rows) {
        DataGridResult result = itemService.getItemList(page, rows);
        return result;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult createItem(TbItem item, String desc, String itemParams) throws Exception {
        TaotaoResult result = itemService.createItem(item, desc, itemParams);
        return result;
    }
}
