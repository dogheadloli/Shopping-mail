package service.impl;

import mapper.TbItemCatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import po.TbItemCat;
import po.TbItemCatExample;
import po.TbItemCatExample.Criteria;
import service.ItemCatService;

import java.util.List;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/8/14 0014 15:26
 * 4
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Override
    public List<TbItemCat> getItemCatList(Long parentId) {

        TbItemCatExample example = new TbItemCatExample();
        //设置查询条件
        Criteria criteria = example.createCriteria();
        //根据parentid查询子节点
        criteria.andParentIdEqualTo(parentId);
        //返回子节点列表
        List<TbItemCat> list = itemCatMapper.selectByExample(example);
        return list;

    }
}
