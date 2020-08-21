package com.zys.jredis;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class SetTest {
    private Jedis jedis;
    @Before
    public void before(){
        this.jedis = new Jedis("192.168.42.131",6379);
    }

    @After
    public void After(){
        this.jedis.close();
    }

    //测试SET相关
    @Test
    public void testSet(){

        //sadd
        jedis.sadd("names","zhangsan","lisi");

        //smembers
        jedis.smembers("names");

        //sismember
        jedis.sismember("names","xiaochen");

        //...
    }
}
