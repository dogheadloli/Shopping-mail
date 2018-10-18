package mapper;


import pojo.SearchItem;

import java.util.List;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/9/4 0004 11:30
 * 4    商品搜索mapper
 */
public interface SearchItemMapper {

    public List<SearchItem> getItemList();
    SearchItem getItemById(long itemId);
}
