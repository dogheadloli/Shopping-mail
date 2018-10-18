package service;

import jedis.JedisClient;
import mapper.TbUserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import po.TbUser;
import po.TbUserExample;
import utils.JsonUtils;
import utils.TaotaoResult;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/9/17 0017 20:38
 * 4    用户处理:是否可用、登录、注册、查询
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper userMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${USER_SESSION}")
    private String USER_SESSION;
    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    @Override
    public TaotaoResult checkData(String data, int type) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        //判断用户名是否可用
        if (type == 1) {
            criteria.andUsernameEqualTo(data);
        } else if (type == 2) {
            //手机
            criteria.andPhoneEqualTo(data);
        } else if (type == 3) {
            //邮箱
            criteria.andEmailEqualTo(data);
        } else {
            return TaotaoResult.build(400, "非法数据");
        }
        List<TbUser> list = userMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            //查询数据不可用
            return TaotaoResult.ok(false);
        }
        return TaotaoResult.ok(true);
    }

    @Override
    public TaotaoResult register(TbUser user) {
        //检查数据有效性
        if (StringUtils.isBlank(user.getUsername())) {
            return TaotaoResult.build(400, "用户名不能为空");
        }
        TaotaoResult taotaoResult = checkData(user.getUsername(), 1);
        if (!(boolean) taotaoResult.getData()) {
            return TaotaoResult.build(400, "用户名不能重复");
        }
        if (StringUtils.isBlank(user.getPassword())) {
            return TaotaoResult.build(400, "密码不能为空");
        }
        if (!StringUtils.isBlank(user.getPhone())) {
            TaotaoResult taotaoResult2 = checkData(user.getPhone(), 2);
            if (!(boolean) taotaoResult2.getData()) {
                return TaotaoResult.build(400, "手机号不能重复");
            }
        }
        if (!StringUtils.isBlank(user.getEmail())) {
            TaotaoResult taotaoResult3 = checkData(user.getEmail(), 3);
            if (!(boolean) taotaoResult3.getData()) {
                return TaotaoResult.build(400, "邮箱不能重复");
            }
        }
        //补全pojo属性
        user.setCreated(new Date());
        user.setUpdated(new Date());
        //密码加密
        String md5pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5pass);
        //插入数据
        userMapper.insert(user);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult longin(String username, String password) {
        //查数据库判断密码是否正确
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> list = userMapper.selectByExample(example);
        if (list == null || list.size() == 0) {
            return TaotaoResult.build(400, "用户名或密码不正确");
        }
        TbUser user = list.get(0);
        if (!DigestUtils.md5DigestAsHex(password.getBytes())
                .equals(user.getPassword())) {
            return TaotaoResult.build(400, "用户名或密码不正确");
        }
        //生成token，使用uuid
        String token = UUID.randomUUID().toString();
        //保存到redis,key是token，设置过期时间
        user.setPassword(null);
        jedisClient.set(USER_SESSION + ":" + token, JsonUtils.objectToJson(user));
        jedisClient.expire(USER_SESSION + ":" + token, SESSION_EXPIRE);
        return TaotaoResult.ok(token);
    }

    @Override
    public TaotaoResult getUserByToken(String token) {
        String json = jedisClient.get(USER_SESSION + ":" + token);
        if (StringUtils.isBlank(json)) {
            return TaotaoResult.build(400, "用户登录已经过期");
        }
        //重置过期时间
        jedisClient.expire(USER_SESSION + ":" + token, SESSION_EXPIRE);
        //把json装换成user对象
        TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
        return TaotaoResult.ok(user);
    }

    @Override
    public TaotaoResult logout(String token) {
        jedisClient.hdel(USER_SESSION + ":" + token);
        return TaotaoResult.ok();
    }
}
