package listener;

import mapper.SearchItemMapper;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import pojo.SearchItem;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.IOException;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/9/12 0012 10:51
 * 4    监听商品添加时间，同步索引库
 */
public class ItemAddMessageListener implements MessageListener {

    @Autowired
    private SearchItemMapper searchItemMapper;
    @Autowired
    private HttpSolrClient httpSolrClient;

    @Override
    public void onMessage(Message message) {
        // 从消息总取商品id
        try {
            TextMessage textMessage = (TextMessage) message;
            long itemId = Long.parseLong(textMessage.getText());
            // 根据id查数据库，取商品信息
            Thread.sleep(1000);
            SearchItem searchItem = searchItemMapper.getItemById(itemId);
            // 创建文档对象
            SolrInputDocument document = new SolrInputDocument();
            // 向文档对象中添加域
            document.addField("id", searchItem.getId());
            document.addField("item_title", searchItem.getTitle());
            document.addField("item_sell_point", searchItem.getSell_point());
            document.addField("item_price", searchItem.getPrice());
            document.addField("item_image", searchItem.getImage());
            document.addField("item_category_name", searchItem.getCategory_name());
            document.addField("item_desc", searchItem.getItem_desc());
            // 把文件写入索引库
            httpSolrClient.add(document);
            // 提交
            httpSolrClient.commit();
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
