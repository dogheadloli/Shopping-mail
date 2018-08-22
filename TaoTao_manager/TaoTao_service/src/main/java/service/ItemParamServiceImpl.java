package service;

import mapper.TbItemParamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import po.TbItemParam;
import po.TbItemParamExample;
import utils.TaotaoResult;

import java.util.Date;
import java.util.List;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/8/16 0016 17:01
 * 4    商品规格参数模板
 */
@Service
public class ItemParamServiceImpl implements ItemParamService {
    @Autowired
    private TbItemParamMapper tbItemParamMapper;

    @Override
    public TaotaoResult getItemParamByCid(Long cid) {
        TbItemParamExample tbItemParamExample = new TbItemParamExample();
        TbItemParamExample.Criteria criteria = tbItemParamExample.createCriteria();
        criteria.andItemCatIdEqualTo(cid);
        List<TbItemParam> list = tbItemParamMapper.selectByExampleWithBLOBs(tbItemParamExample);
        //判断是否有结果
        if (list != null && list.size() > 0) {
            return TaotaoResult.ok(list.get(0));
        } else {
            return TaotaoResult.ok();
        }
    }

    @Override
    public TaotaoResult insertItemParamt(TbItemParam itemParam) {
        //补全pojo
        itemParam.setCreated(new Date());
        itemParam.setUpdated(new Date());
        //插入到表中
        tbItemParamMapper.insert(itemParam);
        return TaotaoResult.ok();
    }
}
