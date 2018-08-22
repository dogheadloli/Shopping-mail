package service;

import po.TbItemCat;

import java.util.List;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/8/14 0014 15:25
 * 4
 */
public interface ItemCatService {

    List<TbItemCat> getItemCatList(Long parentId);

}
