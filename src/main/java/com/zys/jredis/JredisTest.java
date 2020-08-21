package com.zys.jredis;

import redis.clients.jedis.Jedis;

import java.util.Set;

public class JredisTest {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.42.131",6379);
        // 选择使用哪个库，默认是0号库
        jedis.select(0);

        //获取redis中key的信息
        Set<String> keys = jedis.keys("*");
        keys.forEach(key -> System.out.println("key:"+key));

        //操作库
        //jedis.flushDB();
        jedis.flushAll();
        //释放资源
        jedis.close();
    }

}
