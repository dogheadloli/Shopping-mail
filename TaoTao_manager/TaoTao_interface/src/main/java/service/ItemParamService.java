package service;

import po.TbItemParam;
import utils.TaotaoResult;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/8/16 0016 17:00
 * 4    商品规格模板
 */
public interface ItemParamService {

    /**
     * 根据id查询商品规格模板
     * @param cid
     * @return
     */
    TaotaoResult getItemParamByCid(Long cid);

    /**
     * 添加商品规格模板
     * @param itemParam
     * @return
     */
    TaotaoResult insertItemParamt(TbItemParam itemParam);
}
