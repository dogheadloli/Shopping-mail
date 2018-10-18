package service;

import po.TbItem;
import po.TbItemDesc;
import pojo.DataGridResult;
import utils.TaotaoResult;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/8/3 0003 16:38
 * 4    商品
 */
public interface ItemService {

    /**
     * 根据id查询商品
     * @param itemId
     * @return
     */
    TbItem getItemById(long itemId);

    /**
     * 分页查询商品
     * @param page
     * @param rows
     * @return
     */
    DataGridResult getItemList(int page,int rows);

    /**
     * 添加商品
     * @param item
     * @param desc
     * @param itemParam
     * @return
     * @throws Exception
     */
    TaotaoResult createItem(TbItem item,String desc,String itemParam) throws Exception;

    /**
     * 根据id查询商品详情
     * @param itemId
     * @return
     */
    TbItemDesc getItemDescById(long itemId);
}
