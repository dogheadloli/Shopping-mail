package service;

import pojo.SearchResult;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/9/5 0005 21:02
 * 4
 */
public interface SearchService {

    SearchResult search(String queryString, int page, int rows) throws Exception;

}
