package dao;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pojo.SearchItem;
import pojo.SearchResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/9/5 0005 20:40
 * 4    查询索引库商品dao
 */
@Repository
public class SearchDao {

    @Autowired
    private HttpSolrClient httpSolrClient;

    public SearchResult search(SolrQuery query) throws Exception {
        //查询
        QueryResponse response = httpSolrClient.query(query);
        //取结果
        SolrDocumentList solrDocumentList = response.getResults();
        //总记录数
        long numFound = solrDocumentList.getNumFound();

        SearchResult result = new SearchResult();
        result.setRecordCount(numFound);
        List<SearchItem> itemList = new ArrayList<>();
        //封装结果
        for (SolrDocument solrDocument : solrDocumentList) {
            SearchItem item = new SearchItem();
            item.setCategory_name((String) solrDocument.get("item_category_name"));
            item.setId((String) solrDocument.get("id"));
            item.setImage((String) solrDocument.get("item_image"));
            item.setPrice((long) solrDocument.get("item_price"));
            item.setSell_point((String) solrDocument.get("item_sell_point"));
            //高亮显示
            Map<String, Map<String, List<String>>> highlingting = response.getHighlighting();
            List<String> list = highlingting.get(solrDocument.get("id")).get("item_title");
            String title = "";
            if (list != null && list.size() > 0) {
                title = list.get(0);
            } else {
                title = (String) solrDocument.get("item_title");
            }
            item.setTitle((String) solrDocument.get("item_title"));
            itemList.add(item);
        }
        result.setItemList(itemList);
        return result;
    }
}
