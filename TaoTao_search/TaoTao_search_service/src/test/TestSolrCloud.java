import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.common.SolrInputDocument;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/9/8 0008 11:10
 * 4
 */
public class TestSolrCloud {

    public void testSolrCloudAddDocument() throws Exception {
        //创建一个CloudSolrServer,构造方法中指定zookeeper的地址列表
        /* **CloudSolrServer cloudSolrServer = new CloudSolrServer("120.78.88.198:2182,120.78.88.198:2183,120.78.88.198:2184");*/
        CloudSolrClient cloudSolrClient = new CloudSolrClient.Builder().withSocketTimeout(60000).withZkHost("120.78.88.198:2182,120.78.88.198:2183,120.78.88.198:2184").build();
        //设置默认Conllection
        cloudSolrClient.setDefaultCollection("collection2");
        //创建一个文档对象
        SolrInputDocument document = new SolrInputDocument();
        //向文档中添加域
        document.setField("id", "test003");
        document.setField("item_title", "测试商品名称3");
        document.setField("item_price", 100);
        //把文档写入索引库
        cloudSolrClient.add(document);
        //提交
        cloudSolrClient.commit();
    }
}
