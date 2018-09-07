import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/9/4 0004 17:21
 * 4
 */
public class TestSolrJ {

    public void testAddDocument() throws Exception {
        //创建SolrServer
        //指定服务url
        SolrServer solrServer = new HttpSolrServer("http://120.78.88.198:8080/solr/new_core");
        //创建文档对象SolrInputDocument
        SolrInputDocument document = new SolrInputDocument();
        //向文档中添加域，必须有id域，域的名称必须在schema.xml定义
        document.addField("id", "test002");
        document.addField("item_title", "测试商品2");
        document.addField("item_price", 1000);
        solrServer.add(document);
        //提交
        solrServer.commit();
    }


    public void deleteDocumentById() throws Exception {
        //创建SolrServer
        //指定服务url
        SolrServer solrServer = new HttpSolrServer("http://120.78.88.198:8080/solr/new_core");
        solrServer.deleteById("test001");
        solrServer.commit();
    }


    public void deleteByQuery() throws Exception {
        //创建SolrServer
        //指定服务url
        SolrServer solrServer = new HttpSolrServer("http://120.78.88.198:8080/solr/new_core");
        solrServer.deleteByQuery("item_title:测试商品2");
        solrServer.commit();
    }

    @Test
    public void searchDocument() throws Exception {
        //创建一个SoleServer
        SolrServer solrServer = new HttpSolrServer("http://120.78.88.198:8080/solr/new_core");
        //创建一个SolrQuery对象
        SolrQuery solrQuery = new SolrQuery();
        //设置查询条件。分页条件，排序条件，高亮条件
        solrQuery.setQuery("手机");
        solrQuery.setStart(30);
        solrQuery.setRows(20);
        //设置默认搜索域
        solrQuery.set("df", "item_keywords");
        //设置高亮
        solrQuery.setHighlight(true);
        //设置高亮显示的域
        solrQuery.addHighlightField("item_title");
        solrQuery.setHighlightSimplePre("<div>");
        solrQuery.setHighlightSimplePre("</div>");
        //执行查询，得到response
        QueryResponse response = solrServer.query(solrQuery);
        //取查询结果
        SolrDocumentList list = response.getResults();
        //取总记录数
        System.out.println("中记录" + list.getNumFound());
        for (SolrDocument solrDocument : list) {
            System.out.println(solrDocument.get("id"));
            //取高亮显示
            Map<String, Map<String, List<String>>> highLighting = response.getHighlighting();
            List<String> list1 = highLighting.get(solrDocument.get("id")).get("item_title");
            String itemTitle = "";
            if (list != null && list.size() > 0) {
                itemTitle = list1.get(0);
            } else {
                itemTitle = (String) solrDocument.get("item_title");
            }
            System.out.println(itemTitle);
            System.out.println(solrDocument.get("item_title"));
            System.out.println(solrDocument.get("item_sell_point"));
            System.out.println(solrDocument.get("item_image"));
            System.out.println("+++++++++++++++++++++++++++++++");
        }

    }
}
