package service.impl;

import mapper.TbItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import po.TbItem;
import po.TbItemExample;
import service.ItemService;

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

    @Override
    public TbItem getItemById(long itemId) {

        TbItemExample tbItemExample = new TbItemExample();
        TbItemExample.Criteria criteria = tbItemExample.createCriteria();
        criteria.andIdEqualTo(itemId);
        List<TbItem> list = tbItemMapper.selectByExample(tbItemExample);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
