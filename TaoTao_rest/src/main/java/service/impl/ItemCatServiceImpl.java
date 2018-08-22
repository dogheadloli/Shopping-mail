package service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import mapper.TbItemCatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import po.TbItemCat;
import po.TbItemCatExample;
import pojo.CatNode;
import pojo.CatResult;
import service.ItemCatService;

import java.util.ArrayList;
import java.util.List;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/8/18 0018 22:22
 * 4
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Override
    public CatResult getItemCatList() {
        CatResult catResult = new CatResult();
        //查询分类列表
        catResult.setData(getCatList(0));
        return null;
    }

    private List<?> getCatList(long patentId) {
        //创建查询条件
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(patentId);
        List<TbItemCat> list = itemCatMapper.selectByExample(example);
        //遍历list创建节点
        List resultList = new ArrayList<>();
        for (TbItemCat itemCat : list) {
            //是否为父结点
            if (itemCat.getIsParent()) {
                CatNode catNode = new CatNode();
                if (patentId == 0) {
                    catNode.setName("<a href='/products/" + itemCat.getId() + ".html'>" +
                            itemCat.getName() + "</a>");
                } else {
                    catNode.setName(itemCat.getName());
                }
                catNode.setUrl("/products/" + itemCat.getId() + ".html");
                catNode.setItem(getCatList(itemCat.getId()));
                resultList.add(catNode);
            } else {
                resultList.add("/products/" + itemCat.getId() + ".html" + itemCat.getName());
            }
        }
        return resultList;
    }
}
