package com.zys;

import com.zys.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BoundAPITest {

    @Autowired
    private StringRedisTemplate strRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    //spring data 为了方便我们对redis进行更友好的操作 因此有提供了bound api 简化操作
    @Test
    public void boundApiTest(){
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

      /*  strRedisTemplate.opsForValue().set("name","zhangsan");
        strRedisTemplate.opsForValue().append("name","123");
        String name = strRedisTemplate.opsForValue().get("name");
        System.out.println(name);*/

        BoundValueOperations<String, String> nameValueOperations = strRedisTemplate.boundValueOps("name");
        nameValueOperations.set("zhangsan");
        nameValueOperations.append("123");
        String name = nameValueOperations.get();
        System.out.println(name);

        BoundListOperations<String, String> lists = strRedisTemplate.boundListOps("lists");
        lists.leftPushAll("zhangsna","lisi","wangwu");
        for (String s : lists.range(0, -1)) {
            System.out.println(s);
        };

        //set
        //redisTemplate.boundSetOps();
        //stringRedisTemplate.boundSetOps()
        //zset
        //stringRedisTemplate.boundZSetOps();
        //redisTemplate.boundZSetOps();
        //hash
        //stringRedisTemplate.boundHashOps();
        //redisTemplate.boundHashOps()


        /**
         * 1.针对于日后处理key value 都是 String 使用 StringRedisTemplate
         * 2.针对于日后处理的key value 存在对象 使用 RedisTemplate
         * 3.针对于同一个key多次操作可以使用boundXXxOps() Value List Set Zset Hash的api 简化书写
         */

        /**
         * redis应用场景
         *
         *  1.利用redis 中字符串类型完成 项目中手机验证码存储的实现
         *  2.利用redis 中字符串类型完成 具有失效性业务功能  12306  淘宝  订单还有:40分钟
         *  3.利用redis 分布式集群系统中 Session共享  memcache 内存 数据存储上限 数据类型比较简单  redis 内存  数据上限  数据类型丰富
         *  4.利用redis zset类型 可排序set类型  元素 分数  排行榜之类功能   dangdang 销量排行  sales(zset) [商品id,商品销量] ......
         *  5.利用redis 分布式缓存  实现
         *  6.利用redis 存储认证之后token信息   微信小程序 微信公众号 |用户 openid   ---> 令牌(token) redis 超时
         *  7.利用redis 解决分布式集群系统中分布式锁问题       redis 单进程 单线程   n 20 定义
         *      jvm  1进程开启多个线程 synchronize int n=20
         *      jvm  1进程开启多个线程 synchronize int n=20
         *      .....  LRA脚本
         *
         */

    }

}
