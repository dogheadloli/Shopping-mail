package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import po.TbItem;
import po.TbItemDesc;
import pojo.Item;
import service.ItemService;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/9/12 0012 20:33
 * 4    商品详情controller
 */
@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    //查询商品详情
    public String showItem(@PathVariable Long itemId, Model model) {
        //取基本信息
        TbItem tbItem = itemService.getItemById(itemId);
        Item item = new Item(tbItem);
        //取商品详情
        TbItemDesc itemDesc = itemService.getItemDescById(itemId);
        model.addAttribute("item", item);
        model.addAttribute("itemDesc", itemDesc);
        return "item";
    }
}
