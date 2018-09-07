package pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/9/5 0005 17:18
 * 4
 */
public class SearchResult implements Serializable {
    private long totalPages;
    private long recordCount;
    private List<SearchItem> itemList;

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public List<SearchItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<SearchItem> itemList) {
        this.itemList = itemList;
    }

    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }
}
