package service;


import mapper.SearchItemMapper;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.SearchItem;
import utils.TaotaoResult;

import java.util.List;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/9/4 0004 18:17
 * 4    商品数据导入solr
 */
@Service
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    SearchItemMapper searchItemMapper;
    @Autowired
    private HttpSolrClient httpSolrClient;

    @Override
    public TaotaoResult importItemsToIndex() {
        try {
            //查询商品数据
            List<SearchItem> itemList = searchItemMapper.getItemList();
            //遍历商品数据添加到索引库
            for (SearchItem searchItem : itemList) {
                SolrInputDocument document = new SolrInputDocument();
                document.addField("id", searchItem.getId());
                document.addField("item_title", searchItem.getTitle());
                document.addField("item_sell_point", searchItem.getSell_point());
                document.addField("item_price", searchItem.getPrice());
                document.addField("item_image", searchItem.getImage());
                document.addField("item_category_name", searchItem.getCategory_name());
                document.addField("item_desc", searchItem.getItem_desc());
                httpSolrClient.add(document);
                //提交
                httpSolrClient.commit();
            }
        } catch (Exception e) {
            return TaotaoResult.build(500, "失败");
        }
        return TaotaoResult.ok();
    }
}
