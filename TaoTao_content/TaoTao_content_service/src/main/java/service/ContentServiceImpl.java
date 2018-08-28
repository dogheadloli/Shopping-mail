package service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import mapper.TbContentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import po.TbContent;
import po.TbContentExample;
import pojo.DataGridResult;

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
}
