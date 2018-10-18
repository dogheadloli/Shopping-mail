package service;

import jedis.JedisClient;
import mapper.TbOrderItemMapper;
import mapper.TbOrderMapper;
import mapper.TbOrderShippingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import po.TbOrderItem;
import po.TbOrderShipping;
import pojo.OrderInfo;
import utils.TaotaoResult;

import java.util.Date;
import java.util.List;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/10/9 0009 12:30
 * 4 订单处理
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private TbOrderMapper orderMapper;
    @Autowired
    private TbOrderItemMapper orderItemMapper;
    @Autowired
    private TbOrderShippingMapper orderShippingMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${ORDER_ID_GEN}")
    private String ORDER_ID_GEN;
    @Value("${ORDER_ID_BEGIN_VALUE}")
    private String ORDER_ID_BEGIN_VALUE;
    @Value("${ORDER_ITEM_ID_GEN_KEY}")
    private String ORDER_ITEM_ID_GEN_KEY;

    @Override
    public TaotaoResult createOrder(OrderInfo orderInfo) {
        //生成订单号,使用redis的incr
        if (!jedisClient.exists(ORDER_ID_GEN)) {
            jedisClient.set(ORDER_ID_GEN, ORDER_ID_BEGIN_VALUE);
        }
        String orderId = jedisClient.incr(ORDER_ID_GEN).toString();
        //补全属性
        orderInfo.setOrderId(orderId);
        orderInfo.setPostFee("0");
        orderInfo.setStatus(1);
        orderInfo.setCreateTime(new Date());
        orderInfo.setUpdateTime(new Date());
        //向订单表插入
        orderMapper.insert(orderInfo);
        //向订单明细插入
        List<TbOrderItem> orderItems = orderInfo.getOrderItems();
        for (TbOrderItem orderItem : orderItems) {
            String oid = jedisClient.incr(ORDER_ITEM_ID_GEN_KEY).toString();
            orderItem.setId(oid);
            orderItem.setOrderId(orderId);

            orderItemMapper.insert(orderItem);
        }
        //向订单物流表插入
        TbOrderShipping orderShipping = orderInfo.getOrderShipping();
        orderShipping.setOrderId(orderId);
        orderShipping.setCreated(new Date());
        orderShipping.setUpdated(new Date());

        orderShippingMapper.insert(orderShipping);
        //返回订单号
        return TaotaoResult.ok(orderId);
    }
}
