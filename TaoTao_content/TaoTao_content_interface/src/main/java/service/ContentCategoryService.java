package service;

import po.TbContentCategory;
import utils.TaotaoResult;

import java.util.List;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/8/21 0021 19:34
 * 4    内容广告分类查询
 */
public interface ContentCategoryService {

    /**
     * 获取所有分类
     * @param parentId
     * @return
     */
    List<TbContentCategory> getContentCategoryList(long parentId);

    /**
     * 添加分类
     * @param parentId
     * @param name
     * @return
     */
    TaotaoResult addContentCategory(Long parentId, String name);

    /**
     * 修改分类
     * @param id
     * @param name
     * @return
     */
    TaotaoResult updateContentCategory(Long id, String name);

    /**
     * 删除分类
     * @param id
     * @return
     */
    TaotaoResult deleteContentCategory(Long id);

}
