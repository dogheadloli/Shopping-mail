package service;

import pojo.DataGridResult;


/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/8/28 0028 18:31
 * 4    内容查询
 */
public interface ContentService {

    DataGridResult getContentList(Long id, Integer page, Integer rows);
}
