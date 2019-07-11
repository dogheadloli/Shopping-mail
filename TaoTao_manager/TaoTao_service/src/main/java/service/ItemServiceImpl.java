package service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jedis.JedisClient;
import mapper.TbItemDescMapper;
import mapper.TbItemMapper;
import mapper.TbItemParamItemMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import po.*;
import pojo.DataGridResult;
import utils.IDUtils;
import utils.JsonUtils;
import utils.TaotaoResult;

import javax.annotation.Resource;
import javax.jms.*;

import java.util.Date;
import java.util.List;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/8/3 0003 16:39
 * 4    商品管理service
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbItemDescMapper tbItemDescMapper;
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;
    /*@Autowired
    private JmsTemplate jmsTemplate;*/
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Resource(name = "itemAddTopic")
    private Destination destination;
    @Autowired
    private JedisClient jedisClient;
    @Value("${ITEM_INFO}")
    private String ITEM_INFO;
    @Value("${TIME_EXPIRE}")
    private Integer TIME_EXPIRE;

    @Override
    // 查询商品
    public TbItem getItemById(long itemId) {
        // 先查询缓存
        try {
            String json = jedisClient.get(ITEM_INFO + ":" + itemId + ":" + "BASE");
            if (StringUtils.isNotBlank(json)) {
                TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
                return item;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 缓存中没有，查询数据库
        TbItem item = tbItemMapper.selectByPrimaryKey(itemId);
        // 添加到换缓存
        try {
            // 添加到缓存
            jedisClient.set(ITEM_INFO + ":" + itemId + ":" + "BASE", JsonUtils.objectToJson(item));
            // 设置过期时间，提高利用率
            jedisClient.expire(ITEM_INFO + ":" + itemId + ":" + "BASE", TIME_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    // 分页查询
    public DataGridResult getItemList(int page, int rows) {
        // 商品查询列表
        TbItemExample tbItemExample = new TbItemExample();
        // 分页处理
        PageHelper.startPage(page, rows);
        List<TbItem> list = tbItemMapper.selectByExample(tbItemExample);
        // 创建返回值对象
        DataGridResult result = new DataGridResult();
        result.setRows(list);
        // 取记录总条数
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        result.setTotal((int) pageInfo.getTotal());
        return result;
    }

    @Override
    // 添加
    public TaotaoResult createItem(TbItem item, String desc, String itemParam) throws Exception {
        // 补全item
        long itemId = IDUtils.genItemId();
        item.setId(itemId);
        item.setStatus((byte) 1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        // 插入到数据库
        tbItemMapper.insert(item);
        // 添加商品描述
        TaotaoResult result = insertItemDesc(itemId, desc);
        if (result.getStatus() != 200) {
            throw new Exception();
        }
        // 添加规格参数
        result = insertItemParamItem(itemId, itemParam);
        if (result.getStatus() != 200) {
            throw new Exception();
        }
        /*// 向activemq发送消息
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage message = session.createTextMessage(itemId + "");
                return message;
            }
        });*/
        // 向rabbitmq发消息
        rabbitTemplate.convertAndSend(itemId + "");

        return TaotaoResult.ok();
    }

    @Override
    // 查询详情
    public TbItemDesc getItemDescById(long itemId) {
        // 先查询缓存
        try {
            String json = jedisClient.get(ITEM_INFO + ":" + itemId + ":" + "DESC");
            if (StringUtils.isNotBlank(json)) {
                TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
                return itemDesc;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 缓存中没有，查数据库
        TbItemDesc itemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
        // 添加到换缓存
        try {
            // 添加到缓存
            jedisClient.set(ITEM_INFO + ":" + itemId + ":" + "DESC", JsonUtils.objectToJson(itemDesc));
            // 设置过期时间，提高利用率
            jedisClient.expire(ITEM_INFO + ":" + itemId + ":" + "DESC", TIME_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemDesc;
    }

    /**
     * 添加商品描述富文本框
     */
    private TaotaoResult insertItemDesc(Long itemId, String Desc) {
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(itemId);
        tbItemDesc.setItemDesc(Desc);
        tbItemDesc.setCreated(new Date());
        tbItemDesc.setUpdated(new Date());
        tbItemDescMapper.insert(tbItemDesc);
        return TaotaoResult.ok();
    }

    /**
     * 添加规格参数
     */
    private TaotaoResult insertItemParamItem(Long itemId, String itemParam) {
        // 补全pojo
        TbItemParamItem itemParamItem = new TbItemParamItem();
        itemParamItem.setItemId(itemId);
        itemParamItem.setParamData(itemParam);
        itemParamItem.setCreated(new Date());
        itemParamItem.setUpdated(new Date());
        itemParamItemMapper.insert(itemParamItem);
        return TaotaoResult.ok();
    }
}