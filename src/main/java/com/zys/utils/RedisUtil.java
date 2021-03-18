package com.zys.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 *
 */
@Component
public class RedisUtil {
	
	private static Logger logger = LoggerFactory.getLogger(RedisUtil.class);
	
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	/**
	 * 设置过期时间
	 * @param key
	 * @param time, 默认时间单位，秒
	 * @return
	 */
	public boolean expire(String key, long time) {
        return this.expire(key, time, null);
    }
	
	/**
	 * 设置过期时间
	 * @param key
	 * @param time
	 * @param unit
	 * @return
	 */
	public boolean expire(String key, long time, TimeUnit unit) {
        try {
            if (time > 0) {
            	redisTemplate.expire(key, time, unit == null ? TimeUnit.SECONDS : unit);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
	
    /**
     * 获取过期时间
     * @param key
     * @return
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }
    
    /**
     * 查询key是否存在
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 删除缓存
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
	public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
            	redisTemplate.delete(key[0]);
            } else {
            	redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }
	
	/**
	 * 查询对象值
	 * @param key
	 * @return
	 */
	public Object getObject(String key) {
		return redisTemplate.opsForValue().get(key);
	}
	
	/**
	 * 查询字符串值
	 * @param key
	 * @return
	 */
	public String getString (String key) {
		return (String)getObject(key);
	}
	
	 /**
     * 普通缓存放入,默认无过期时间
     * @param key 键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {
    	return set(key, value, 0, null);
    }
	
    /**
     * 普通缓存放入
     * @param key
     * @param value
     * @param timeout
     * @param unit
     * @return true成功 false失败
     */
    public boolean set(String key, Object value, long timeout, TimeUnit unit) {
        try {
        	if(timeout > 0) {
        		redisTemplate.opsForValue().set(key, value, timeout, unit);
        	}else {
        		redisTemplate.opsForValue().set(key, value);
        	}
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean lock(String key, String uniqueIdentifier, long timeout) {
        if (stringRedisTemplate.opsForValue().setIfAbsent(key, uniqueIdentifier)) {
        	stringRedisTemplate.expire(key, timeout, TimeUnit.MILLISECONDS);
        	return true;
        }
        //判断锁超时 - 防止原来的操作异常，没有运行解锁操作  防止死锁
        String uuidWithTime = stringRedisTemplate.opsForValue().get(key);
        //如果锁过期
        if (StringUtils.isNotEmpty(uuidWithTime)) {
            long timeMillis = Long.parseLong(uuidWithTime.split("\\|")[1]);
            if (timeMillis < System.currentTimeMillis()) {
                String oldValue = stringRedisTemplate.opsForValue().getAndSet(key, uniqueIdentifier);
                stringRedisTemplate.expire(key, timeout, TimeUnit.MILLISECONDS);
                return StringUtils.isNotEmpty(oldValue) && oldValue.equals(uuidWithTime);
            }
        }
        return false;
    }

    public void unlock(String key, String uniqueIdentifier) {
    	try {
            String identifier = stringRedisTemplate.opsForValue().get(key);
            if (StringUtils.isNotEmpty(identifier) && identifier.equals(uniqueIdentifier))
                stringRedisTemplate.delete(key);
        } catch (Exception e) {
            logger.error("[Redis锁] 解锁出现异常了，{}", e);
        }
    }
}
