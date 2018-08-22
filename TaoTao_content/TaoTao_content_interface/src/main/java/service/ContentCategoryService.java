package service;

import po.TbContentCategory;
import utils.TaotaoResult;

import java.util.List;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/8/21 0021 19:34
 * 4    内容分类查询
 */
public interface ContentCategoryService {

    List<TbContentCategory> getContentCategoryList(long parentId);

    TaotaoResult addContentCategory(Long parentId, String name);

    TaotaoResult updateContentCategory(Long id, String name);

    TaotaoResult deleteContentCategory(Long id);

}
