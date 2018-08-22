package pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/8/14 0014 11:11
 * 4
 */
public class DataGridResult implements Serializable {
    private int total;
    private List<?> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
