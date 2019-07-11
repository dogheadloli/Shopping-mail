package controller;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import po.TbItem;
import po.TbUser;
import pojo.OrderInfo;
import service.OrderService;
import utils.CookieUtils;
import utils.JsonUtils;
import utils.TaotaoResult;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/9/28 0028 20:09
 * 4    订单确认页面
 */
@Controller
public class OrderCartController {
    @Value("${CART_KEY}")
    private String CART_KEY;
    @Autowired
    private OrderService orderService;

    @RequestMapping("/order/order-cart")
    public String showOrderCart(HttpServletRequest request) {
        // 必须是登录状态
        // 取用户id
        TbUser user = (TbUser) request.getAttribute("user");
        // 根据用户信息取收货地址列表
        // 传递给页面
        // 从cookie取购物车
        List<TbItem> cartList = getCartItemList(request);
        request.setAttribute("cartList", cartList);
        // 返回逻辑视图
        return "order-cart";
    }

    private List<TbItem> getCartItemList(HttpServletRequest request) {
        // 从cookie取商品列表
        String json = CookieUtils.getCookieValue(request, CART_KEY, true);
        if (StringUtils.isBlank(json)) {
            // 没有内容
            return new ArrayList<>();
        }
        List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
        return list;
    }

    /**
     * 生成订单
     */
    @RequestMapping(value = "/order/create", method = RequestMethod.POST)
    public String createOrder(OrderInfo orderInfo, Model model) {
        // 生成订单
        TaotaoResult result = orderService.createOrder(orderInfo);
        // 返回逻辑视图
        model.addAttribute("orderId", result.getData().toString());
        model.addAttribute("payment", orderInfo.getPayment());
        DateTime dateTime = new DateTime();
        dateTime = dateTime.plusDays(3);
        model.addAttribute("date", dateTime.toString("yyyy-MM-dd"));
        return "success";
    }
}
