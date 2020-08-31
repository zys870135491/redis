package com.zys.cache;

import com.zys.utils.ApplicationContextUtils;
import org.apache.ibatis.cache.Cache;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.DigestUtils;

public class RedisCache implements Cache{

    //当前放入缓存的mapper的namespace
    private final String id;

    //必须存在构造方法
    public RedisCache(String id) {
        System.out.println("id:=====================> " + id);
        this.id = id;
    }

    //返回cache唯一标识
    @Override
    public String getId() {
        return this.id;
    }

    /**
     * 封装redisTemplate
     */
    private RedisTemplate getRedisTemplate(){
        RedisTemplate redisTemplate = (RedisTemplate)ApplicationContextUtils.getBean("redisTemplate");
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    /**
     * 缓存放入值  redis RedisTemplate   StringRedisTemplate
     */
    @Override
    public void putObject(Object key, Object value) {
        System.out.println("key:" + key.toString());
        System.out.println("value:" + value);

        //使用redisHash类型作为缓存存储类型， key hashkey value
        getRedisTemplate().opsForHash().put(id.toString(),getKeyToMD5(key.toString()),value);
    }

    /**
     * 获取redis缓存中数据中获取数据
     */
    @Override
    public Object getObject(Object key) {
        return getRedisTemplate().opsForHash().get(id.toString(),getKeyToMD5(key.toString()));
    }

    /**
     * 注意:这个方法为mybatis保留方法 默认没有实现 后续版本可能会实现
     */
    @Override
    public Object removeObject(Object o) {
        return null;
    }

    /**
     * 清空namespace的缓存（增删改都会触发这个方法）
     */
    @Override
    public void clear() {
        System.out.println("清空缓存.........");
        getRedisTemplate().delete(id.toString());
    }

    /**
     *用来计算缓存数量
     */
    @Override
    public int getSize() {
        return  getRedisTemplate().opsForHash().size(id.toString()).intValue();
    }

    /**
     *  mybatis提供的key过长，建议使用md5进行优化
     * 进行MD5加密
     * @param key
     * @return
     */
    public String getKeyToMD5(String key){
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }
}
