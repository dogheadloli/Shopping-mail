package service;

import dao.SearchDao;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.SearchResult;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/9/5 0005 21:04
 * 4    商品查询
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchDao searchDao;

    @Override
    public SearchResult search(String queryString, int page, int rows) throws Exception {
        SolrQuery solrQuery = new SolrQuery();
        //根据跳进拼装一个查询对象
        solrQuery.setQuery(queryString);
        //设置分页
        if (page < 1) {
            page = 1;
        }
        solrQuery.setStart((page - 1) * rows);
        if (rows < 1) {
            rows = 10;
        }
        solrQuery.setRows(rows);
        //设置搜索域
        solrQuery.set("df", "item_title");
        //设置高亮
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("item_title");
        solrQuery.setHighlightSimplePre("<font color='red'>");
        solrQuery.setHighlightSimplePost("</font>");
        //调用dao
        SearchResult searchResult = searchDao.search(solrQuery);
        //计算总页数
        long recordCount = searchResult.getRecordCount();
        Long pages = recordCount / rows;
        if (recordCount % rows > 0) {
            pages++;
        }
        searchResult.setTotalPages(pages);
        return searchResult;
    }
}
