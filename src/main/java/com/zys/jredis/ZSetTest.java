package com.zys.jredis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class ZSetTest {

    private Jedis jedis;
    @Before
    public void before(){
        this.jedis = new Jedis("192.168.42.131",6379);
    }

    @After
    public void After(){
        this.jedis.close();
    }


    //测试ZSET相关
    @Test
    public void testZset(){

        //zadd
        jedis.zadd("names",10,"张三");

        //zrange
        jedis.zrange("names",0,-1);

        //zcard
        jedis.zcard("names");

        //zrangeByScore
        jedis.zrangeByScore("names","0","100",0,5);

        //..

    }

}
