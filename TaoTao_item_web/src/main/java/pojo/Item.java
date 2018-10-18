package pojo;

import po.TbItem;


/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/9/12 0012 17:34
 * 4    商品包装类
 */
public class Item extends TbItem {

    public Item(TbItem tbItem) {
        this.setId(tbItem.getId());

        this.setTitle(tbItem.getTitle());

        this.setSellPoint(tbItem.getSellPoint());

        this.setPrice(tbItem.getPrice());

        this.setNum(tbItem.getNum());

        this.setBarcode(tbItem.getBarcode());

        this.setImage(tbItem.getImage());

        this.setCid(tbItem.getCid());

        this.setStatus(tbItem.getStatus());

        this.setCreated(tbItem.getCreated());

        this.setUpdated(tbItem.getUpdated());
        //初始化属性
    }

    public String[] getImages() {
        if (this.getImage() != null && !"".equals(this.getImage())) {
            String image2 = this.getImage();
            String[] strings = image2.split(",");
            return strings;
        }
        return  null;
    }
}
