package service;

import po.TbUser;
import utils.TaotaoResult;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/9/17 0017 20:37
 * 4    用户登录相关
 */
public interface UserService {
    /**
     * 检查数据的有效性
     */
    TaotaoResult checkData(String data, int type);

    /**
     * 注册
     */
    TaotaoResult register(TbUser user);

    /**
     * 登录
     */
    TaotaoResult longin(String username, String password);

    /**
     * 根据token进行查询
     */
    TaotaoResult getUserByToken(String token);

    /**
     * 安全退出
     */
    TaotaoResult logout(String token);
}
