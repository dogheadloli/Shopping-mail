package service;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/8/16 0016 22:20
 * 4    商品规格详细
 */
public interface ItemParamItemService {
    /**
     * 根据id查询商品规格详细
     * @param itemId
     * @return
     */
    String getItemParamByItemId(Long itemId);
}
