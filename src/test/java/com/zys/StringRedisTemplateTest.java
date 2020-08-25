package com.zys;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
class StringRedisTemplateTest {

	@Autowired
	private StringRedisTemplate srTemplate;

	@Test
	void keyTest() {
		Boolean nameIsExit = srTemplate.hasKey("name"); // 判断某个key是否存在
		System.out.println("nameIsExit:"+nameIsExit);
		DataType type = srTemplate.type("name");	  // 判断某个key的类型
		System.out.println("type:"+type);
		Set<String> keys = srTemplate.keys("*"); // 获取所有的key
		keys.forEach(key-> System.out.println("key:"+key));
		srTemplate.expire("name",60, TimeUnit.SECONDS); //设置key超时时间
		Long nameExpireTime = srTemplate.getExpire("name");		//查询key有效时间 -1永不超时，-2超时key不存在了
		System.out.println("nameExpireTime:"+nameExpireTime);
		String randomKey = srTemplate.randomKey();						// 任意返回一个key
		System.out.println("randomKey:"+randomKey);
		srTemplate.rename("name","name1");			// 修改key的名字，要求oldkey必须存在，否则报错
		srTemplate.renameIfAbsent("name", "name1"); // 修改key的名字,但自身会先判断key是否存在

	}

	// 操作Redis中字符串 opsForValue 实际操作就是redis中String类型
	@Test
	void StringTest() {
		srTemplate.opsForValue().set("name","wangxiaoxiao");
		srTemplate.opsForValue().append("name","123");	// 给Key里面的值追加值
		String name = srTemplate.opsForValue().get("name");
		System.out.println("name:"+name);
		srTemplate.opsForValue().set("sex","man",100,TimeUnit.SECONDS); // 添加key同时设置超时时间
	}

	// 操作Redis中字符串 opsForList 实际操作就是redis中List类型
	@Test
	void ListTest(){
		srTemplate.opsForList().rightPush("names","xiaochen");	// 创建一个列表，放入一个元素
		srTemplate.opsForList().rightPush("names","xiaochen","xiaohei"); // 创建一个列表，放入多个元素
		List<String> list = new ArrayList<>();
		list.add("heihie");
		list.add("canrrr");
		srTemplate.opsForList().rightPushAll("names",list); // 创建一个列表，放入多个元素
		List<String> namesList = srTemplate.opsForList().range("names", 0, -1);
		namesList.forEach(name -> System.out.print("name:"+name +" "));
		srTemplate.opsForList().trim("names",1,2);  // 截取指定区间的list
	}

	// 操作Redis中字符串 opsForSet 实际操作就是redis中Set类型
	@Test
	void SetTest(){
		srTemplate.opsForSet().add("sets","zhangsan","zhangsan","lisi","wangwu");
		Set<String> sets = srTemplate.opsForSet().members("sets");	//查看sets中的元素
		sets.forEach(value -> System.out.println("value:"+value));
		Long size = srTemplate.opsForSet().size("sets");			//查看sets中的元素个数
		System.out.println("size:"+size);
	}

	@Test
	void ZsetTest(){
		srTemplate.opsForZSet().add("zets","xiaohei",10);
		srTemplate.opsForZSet().add("zets","xiaobai",70);
		srTemplate.opsForZSet().add("zets","xiaohong",50);

		Set<String> zets = srTemplate.opsForZSet().range("zets", 0, -1);	//指定范围查找
		zets.forEach(zet-> System.out.println(zet));
		System.out.println("================================");
		// 获取指定元素分数
		Set<ZSetOperations.TypedTuple<String>> zets1 = srTemplate.opsForZSet().rangeByScoreWithScores("zets", 0, 100);
		zets1.forEach(typedTuple ->{
			System.out.println(typedTuple.getValue()+" "+typedTuple.getScore());
		});
	}

	//操作redis中Hash类型   opsForHash 实际操作就是redis中Hash类型

	@Test
	public void testHash(){

		srTemplate.opsForHash().put("maps","name","张三");//创建一个hash类型 并放入key value

		Map<String,String> map =  new HashMap<String,String>();
		map.put("age","12");
		map.put("bir","2012-12-12");
		srTemplate.opsForHash().putAll("maps",map);  //放入多个key value


		List<Object> values = srTemplate.opsForHash().multiGet("maps", Arrays.asList("name", "age"));//获取多个key的value
		values.forEach(value-> System.out.println(value));

		String value  = (String) srTemplate.opsForHash().get("maps", "name");//获取hash中某个key的值

		List<Object> vals = srTemplate.opsForHash().values("maps");//获取所有values

		Set<Object> keys = srTemplate.opsForHash().keys("maps");//获取所有keys


	}

}
