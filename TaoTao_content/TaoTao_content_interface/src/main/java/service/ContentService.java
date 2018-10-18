package service;

import po.TbContent;
import pojo.DataGridResult;
import utils.TaotaoResult;

import java.util.List;


/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/8/28 0028 18:31
 * 4    内容广告查询
 */
public interface ContentService {

    /**
     * 获取所有
     * @param id
     * @param page
     * @param rows
     * @return
     */
    DataGridResult getContentList(Long id, Integer page, Integer rows);

    /**
     * 添加内容
     * @param content
     * @return
     */
    TaotaoResult addContent(TbContent content);

    /**
     * 通过id查询内容
     * @param cid
     * @return
     */
    List<TbContent> getContentByCid(long cid);

}
