package service;

import po.TbItemParam;
import utils.TaotaoResult;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/8/16 0016 17:00
 * 4
 */
public interface ItemParamService {

    TaotaoResult getItemParamByCid(Long cid);

    TaotaoResult insertItemParamt(TbItemParam itemParam);
}
