package service;

import po.TbItemCat;

import java.util.List;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/8/14 0014 15:25
 * 4    商品分类
 */
public interface ItemCatService {

    /**
     * 根据parentId查询子类
     * @param parentId
     * @return
     */
    List<TbItemCat> getItemCatList(Long parentId);

}
