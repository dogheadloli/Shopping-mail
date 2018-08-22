package service;

import mapper.TbItemParamItemMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import po.TbItemParamItem;
import po.TbItemParamItemExample;
import utils.JsonUtils;

import java.util.List;
import java.util.Map;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/8/20 0020 17:51
 * 4    商品规格参数展示
 */
@Service
public class ItemParamItemServiceImpl implements ItemParamItemService {
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

    @Override
    public String getItemParamByItemId(Long itemId) {
        //根据商品id查询规格参数
        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
        if (list != null && list.size() > 0) {
            //取参数
            TbItemParamItem itemParamItem = list.get(0);
            String paramData = itemParamItem.getParamData();

            //把json数据转换成java对象
            List<Map> paramList = JsonUtils.jsonToList(paramData, Map.class);
            //将参数信息转换成html
            StringBuffer sb = new StringBuffer();
            //sb.append("<div>");
            sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"1\" class=\"Ptable\">\n");
            sb.append("    <tbody>\n");
            for (Map map : paramList) {
                sb.append("        <tr>\n");
                sb.append("            <th class=\"tdTitle\" colspan=\"2\">" + map.get("group") + "</th>\n");
                sb.append("        </tr>\n");
                List<Map> params = (List<Map>) map.get("params");
                for (Map map2 : params) {
                    sb.append("        <tr>\n");
                    sb.append("            <td class=\"tdTitle\">" + map2.get("k") + "</td>\n");
                    sb.append("            <td>" + map2.get("v") + "</td>\n");
                    sb.append("        </tr>\n");
                }
            }
            return sb.toString();
        } else {
            return "";
        }
    }
}
