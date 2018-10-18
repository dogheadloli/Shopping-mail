package pojo;

import po.TbOrder;
import po.TbOrderItem;
import po.TbOrderShipping;

import java.io.Serializable;
import java.util.List;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/9/30 0030 19:05
 * 4    订单
 */
public class OrderInfo extends TbOrder implements Serializable {
    private List<TbOrderItem> orderItems;
    private TbOrderShipping orderShipping;

    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }
}
