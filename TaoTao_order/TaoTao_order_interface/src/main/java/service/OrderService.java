package service;

import pojo.OrderInfo;
import utils.TaotaoResult;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/10/9 0009 12:29
 * 4    订单
 */
public interface OrderService {
    /**
     * 创建订单
     * @param orderInfo
     * @return
     */
    TaotaoResult createOrder(OrderInfo orderInfo);

}
