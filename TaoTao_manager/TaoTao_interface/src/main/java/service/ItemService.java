package service;

import po.TbItem;
import pojo.DataGridResult;
import utils.TaotaoResult;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/8/3 0003 16:38
 * 4
 */
public interface ItemService {
    TbItem getItemById(long itemId);

    DataGridResult getItemList(int page,int rows);

    TaotaoResult createItem(TbItem item,String desc,String itemParam) throws Exception;
}
