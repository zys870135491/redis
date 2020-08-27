package com.zys;

import com.zys.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
class RedisTemplateTest {

	//注入RedisTemplate key Object  Value Object  ===>   对象序列化   name  new User() ====>   name序列化  对象序列化结果
	@Autowired
	private RedisTemplate redisTemplate;

	//opsForxxx  Value String  List  Set  Zset  hash
	@Test
	public void testRedisTemplate(){
		/**
		 * redisTemplate对象中 key 和 value 的序列化都是 JdkSerializationRedisSerializer
		 *      key: string
		 *      value: object
		 *      修改默认key序列化方案 :  key  StringRedisSerializer
		 */
        //修改key序列化方案   String类型序列
		redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());

		User user = new User();
		user.setId(UUID.randomUUID().toString());
		user.setName("张三");
		user.setAge(22);
		user.setBirthDay(new Date());
		redisTemplate.opsForValue().set("user",user);
		User user1 = (User)redisTemplate.opsForValue().get("user");
        System.out.println(user1);

        redisTemplate.opsForList().leftPush("list",user);
        redisTemplate.opsForSet().add("sets",user);
        redisTemplate.opsForZSet().add("zsets",user,10);
        redisTemplate.opsForHash().put("maps","user",user);
        User user2 = (User) redisTemplate.opsForHash().get("maps", "user");
        System.out.println("user2:"+user2);
	}

}
