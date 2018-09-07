package pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/8/18 0018 22:14
 * 4
 */
public class CatNode {
    @JsonProperty("n")
    private String name;
    @JsonProperty("u")
    private String url;
    @JsonProperty("i")
    private List<?> item;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<?> getItem() {
        return item;
    }

    public void setItem(List<?> item) {
        this.item = item;
    }
}
