package com.zys.jredis;

import lombok.val;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;

public class StringTest {

    private Jedis jedis;
    @Before
    public void before(){
        this.jedis = new Jedis("192.168.42.131",6379);
    }

    @After
    public void After(){
        this.jedis.close();
    }

    @Test
    public void StringTest(){
        //set
        jedis.set("name","小陈");
        //get
        String s = jedis.get("name");
        System.out.println(s);
        //mset
        jedis.mset("content","好人","address","海淀区");
        //mget
        List<String> mget = jedis.mget("name", "content", "address");
        mget.forEach(v-> System.out.println("v = " + v));
        //getset
        String set = jedis.getSet("name", "小明");
        System.out.println(set);

        //............
    }
}
