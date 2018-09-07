package service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jedis.JedisClient;
import mapper.TbContentMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import po.TbContent;
import po.TbContentExample;
import pojo.DataGridResult;
import utils.JsonUtils;
import utils.TaotaoResult;

import java.util.Date;
import java.util.List;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/8/28 0028 18:36
 * 4    内容查询
 */
@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    TbContentMapper contentMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${INDEX_CONTENT}")
    private String INDEX_CONTENT;

    @Override
    public DataGridResult getContentList(Long id, Integer page, Integer rows) {
        TbContentExample contentExample = new TbContentExample();
        TbContentExample.Criteria criteria = contentExample.createCriteria();
        criteria.andCategoryIdEqualTo(id);
        PageHelper.startPage(page, rows);
        List<TbContent> list = contentMapper.selectByExample(contentExample);
        //创建返回值对象
        DataGridResult result = new DataGridResult();
        result.setRows(list);
        //取记录总条数
        PageInfo<TbContent> pageInfo = new PageInfo<>(list);
        result.setTotal((int) pageInfo.getTotal());
        return result;
    }

    @Override
    public TaotaoResult addContent(TbContent content) {
        //补全pojo
        content.setCreated(new Date());
        content.setUpdated(new Date());
        //插入
        contentMapper.insert(content);
        //同步缓存，删除对应的缓存信息
        jedisClient.hdel(INDEX_CONTENT, content.getCategoryId().toString());
        return TaotaoResult.ok();
    }

    @Override
    public List<TbContent> getContentByCid(long cid) {
        //先查询缓存里有没有
        try {
            String json = jedisClient.hget(INDEX_CONTENT, cid + "");
            if (StringUtils.isNotBlank(json)) {
                List list = JsonUtils.jsonToList(json, TbContent.class);
                return list;
            }
        } catch (Exception e) {

        }
        //缓存中没有，查数据库
        TbContentExample example = new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        List<TbContent> list = contentMapper.selectByExample(example);
        //加缓存
        try {
            jedisClient.hset(INDEX_CONTENT, cid + "", JsonUtils.objectToJson(list));
        } catch (Exception e) {

        }
        return list;
    }
}
