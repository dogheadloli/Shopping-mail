package service;

import utils.TaotaoResult;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/9/4 0004 18:16
 * 4    商品数据导入solr
 */
public interface SearchItemService {
    TaotaoResult importItemsToIndex();
}
