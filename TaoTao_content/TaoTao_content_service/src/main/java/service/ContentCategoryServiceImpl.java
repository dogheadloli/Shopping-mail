package service;

import mapper.TbContentCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import po.TbContentCategory;
import po.TbContentCategoryExample;
import utils.TaotaoResult;

import java.util.Date;
import java.util.List;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/8/21 0021 19:42
 * 4    内容广告分类查询
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
    @Autowired
    TbContentCategoryMapper contentCategoryMapper;

    @Override
    // 查询
    public List<TbContentCategory> getContentCategoryList(long parentId) {
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        return list;
    }

    @Override
    // 添加
    public TaotaoResult addContentCategory(Long parentId, String name) {
        // 创建对象
        TbContentCategory contentCategory = new TbContentCategory();
        // 补全属性
        contentCategory.setParentId(parentId);
        contentCategory.setName(name);
        contentCategory.setStatus(1);
        contentCategory.setSortOrder(1);
        contentCategory.setIsParent(false);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        // 插入
        contentCategoryMapper.insert(contentCategory);
        // 判断父节点
        TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
        if (!parent.getIsParent()) {
            parent.setIsParent(true);
            contentCategoryMapper.updateByPrimaryKey(parent);
        }
        // 返回结果
        return TaotaoResult.ok(contentCategory);
    }

    // 修改
    @Override
    public TaotaoResult updateContentCategory(Long id, String name) {
        // 创建对象
        TbContentCategory contentCategory = new TbContentCategory();
        // 修改
        contentCategory.setId(id);
        contentCategory.setName(name);
        contentCategory.setUpdated(new Date());
        // 提交修改
        contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
        return TaotaoResult.ok();
    }

    // 删除
    @Override
    public TaotaoResult deleteContentCategory(Long id) {
        TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        // 如果是子节点
        if (!contentCategory.getIsParent()) {
            contentCategoryMapper.deleteByPrimaryKey(id);
            // 判断删除后父节点是否还有子节点
            Long parentId = contentCategory.getParentId();

            TbContentCategoryExample example = new TbContentCategoryExample();
            TbContentCategoryExample.Criteria criteria = example.createCriteria();
            criteria.andParentIdEqualTo(parentId);
            List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);

            if (list.size() == 0) {
                // 如果没有
                TbContentCategory parentContentCategory = new TbContentCategory();
                parentContentCategory.setId(parentId);
                parentContentCategory.setIsParent(false);
                contentCategoryMapper.updateByPrimaryKeySelective(parentContentCategory);
                return TaotaoResult.ok();
            } else {
                return TaotaoResult.ok();
            }
        } else {
            // 如果是父节点递归删除
            TbContentCategoryExample example = new TbContentCategoryExample();
            TbContentCategoryExample.Criteria criteria = example.createCriteria();
            criteria.andParentIdEqualTo(id);
            List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
            for (TbContentCategory contentCategory1 : list) {
                deleteContentCategory(contentCategory1.getId());
            }
            contentCategoryMapper.deleteByPrimaryKey(id);
            return TaotaoResult.ok();
        }
    }
}
