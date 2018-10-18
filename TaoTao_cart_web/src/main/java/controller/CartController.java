package controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import po.TbItem;
import service.ItemService;
import utils.CookieUtils;
import utils.JsonUtils;
import utils.TaotaoResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/9/20 0020 18:47
 * 4    购物车controller
 */
@Controller
public class CartController {

    @Value("${CART_KEY}")
    private String CART_KEY;
    @Value("${CART_EXPIER}")
    private Integer CART_EXPIER;
    @Autowired
    private ItemService itemService;

    /**
     * 向购物车添加商品
     */
    @RequestMapping("/cart/add/{itemId}")
    public String addItemCart(@PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer num,
                              HttpServletRequest request, HttpServletResponse response) {
        //取购物车商品类表
        List<TbItem> cartItemList = getCartItemList(request);
        //判断商品在购物车是否存在
        boolean flag = false;
        for (TbItem item : cartItemList) {
            if (item.getId() == itemId.longValue()) {
                //如果存在就数量+1
                item.setNum(item.getNum() + num);
                flag = true;
                break;
            }
        }
        //不存在添加一个新商品
        if (!flag) {
            //获取商品信息
            TbItem item = itemService.getItemById(itemId);
            //设置购买数量
            item.setNum(num);
            //取一张图片
            String image = item.getImage();
            if (StringUtils.isNotBlank(image)) {
                String[] images = image.split(",");
                item.setImage(images[0]);
            }
            //添加到购物车
            cartItemList.add(item);
        }
        //写入cookie
        CookieUtils.setCookie(request, response, CART_KEY, JsonUtils.objectToJson(cartItemList)
                , CART_EXPIER, true);
        //返回添加成功页面
        return "cartSuccess";
    }

    /**
     * 查询购物车
     */
    @RequestMapping("/cart/cart")
    public String showCartList(HttpServletRequest request) {
        //从cookie中取购物车列表
        List<TbItem> cartItemList = getCartItemList(request);
        //传递给jsp
        request.setAttribute("cartList", cartItemList);
        //返回逻辑试图
        return "cart";
    }

    /**
     * 修改商品数量
     */
    @RequestMapping("/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public TaotaoResult upDateItemNum(@PathVariable Long itemId, @PathVariable Integer num
            , HttpServletRequest request, HttpServletResponse response) {
        //从cookie中取购物车列表
        List<TbItem> cartItemList = getCartItemList(request);
        //查询商品
        for (TbItem item : cartItemList) {
            if (item.getId() == itemId.longValue()) {
                //更新数量
                item.setNum(num);
                break;
            }
        }
        //写入cookie
        CookieUtils.setCookie(request, response, CART_KEY, JsonUtils.objectToJson(cartItemList)
                , CART_EXPIER, true);
        //返回
        return TaotaoResult.ok();
    }

    /**
     * 删除商品
     */
    @RequestMapping("/cart/delete/{itemId}")
    public String deleteCartItem(@PathVariable Long itemId
            , HttpServletRequest request, HttpServletResponse response) {
        //从cookie中取购物车列表
        List<TbItem> cartItemList = getCartItemList(request);
        //查询商品
        for (TbItem item : cartItemList) {
            if (item.getId() == itemId.longValue()) {
                //删除商品
                cartItemList.remove(item);
                break;
            }
        }
        //写入cookie
        CookieUtils.setCookie(request, response, CART_KEY, JsonUtils.objectToJson(cartItemList)
                , CART_EXPIER, true);
        return "redirect:/cart/cart";
    }

    /**
     * 查询商品
     */
    private List<TbItem> getCartItemList(HttpServletRequest request) {
        //从cookie取商品列表
        String json = CookieUtils.getCookieValue(request, CART_KEY, true);
        if (StringUtils.isBlank(json)) {
            //没有内容
            return new ArrayList<>();
        }
        List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
        return list;
    }
}
