package jedis;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * 2 * @Author: 睿
 * 3 * @Date: 2018/8/30 0030 19:35
 * 4
 */
public class TestJedis {

    public void testJedis() {
        //创建jedis对象,指定服务的ip和端口号
        Jedis jedis = new Jedis("120.78.88.198", 6379);

        //操作数据库
        jedis.set("jedis-key", "1234");
        System.out.println(jedis.get("jedis-key"));
        //关闭连接
        jedis.close();
    }


    public void testJedisPool() {
        //创建连接池
        JedisPool jedisPool = new JedisPool("120.78.88.198", 6379);
        //获得连接
        Jedis jedis = jedisPool.getResource();
        //操作数据库
        System.out.println(jedis.get("jedis-key"));
        //关闭jedis连接
        jedis.close();
        //关闭连接池
        jedisPool.close();
    }


    public void testJedisCluster() throws IOException {
        //穿件JedisCluster对象，构造参数:set集合，HostAndPort
        Set<HostAndPort> nodes = new HashSet<>();
        //添加节点
        nodes.add(new HostAndPort("120.78.88.198", 7001));
        nodes.add(new HostAndPort("120.78.88.198", 7002));
        nodes.add(new HostAndPort("120.78.88.198", 7003));
        nodes.add(new HostAndPort("120.78.88.198", 7004));
        nodes.add(new HostAndPort("120.78.88.198", 7005));
        nodes.add(new HostAndPort("120.78.88.198", 7006));
        JedisCluster jedisCluster = new JedisCluster(nodes);
        //操作redis，自带连接池
        jedisCluster.set("cluster-test2", "hello");
        System.out.println(jedisCluster.get("cluster-test2"));
        //关闭JedisCluster


    }
}
