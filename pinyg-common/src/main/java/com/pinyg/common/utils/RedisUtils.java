package com.pinyg.common.utils;

import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class RedisUtils {


	private static final Logger logger=LoggerFactory.getLogger(RedisUtils.class);

	private  RedisTemplate<String,Object> redisTemplate;


	public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}


	/**
	 * 指定缓存失效时间
	 *
	 * @param key  键
	 * @param time 时间(秒)
	 * @return
	 */
	public boolean expire(String key, long time) {
		try {
			if (time > 0) {
				redisTemplate.expire(key, time, TimeUnit.SECONDS);
			}
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 根据key 获取过期时间
	 *
	 * @param key 键 不能为null
	 * @return 时间(秒) 返回0代表为永久有效
	 */
	public long getExpire(String key) {
		return redisTemplate.getExpire(key, TimeUnit.SECONDS);
	}

	/**
	 * 判断key是否存在
	 *
	 * @param key 键
	 * @return true 存在 false不存在
	 */
	public boolean hasKey(String key) {
		try {
			return redisTemplate.hasKey(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 删除缓存
	 *
	 * @param key 可以传一个值 或多个
	 */
	@SuppressWarnings("unchecked")
	public boolean del(String key) {
		try {
			logger.info(" del key {} ", key);
			return redisTemplate.delete(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}


	/**
	 * 删除缓存
	 *
	 * @param key 可以传一个值 或多个
	 */
	@SuppressWarnings("unchecked")
	public Long batchDel(String... key) {
		return redisTemplate.delete(CollectionUtils.arrayToList(key));
	}

	/**
	 * 普通缓存获取
	 *
	 * @param key 键
	 * @return 值
	 */
	public Object get(String key) {

		try {
			return key == null ? null : redisTemplate.opsForValue().get(key);
		} catch (Exception e) {
			logger.error("redis get key{" + key + "} error ", e);
		}
		return null;
	}



	/**
	 * 普通缓存模糊匹配获取
	 *
	 * @param key 键
	 * @return 值
	 */
	public  Long delelteKeys(String key) {
		Set<String> keys = redisTemplate.keys(key + "*");
		logger.info(" deleteKeys Size {} , keys is {} " , keys.size(), keys);
		Iterator keysSet = keys.iterator();
		Collection<String> cell = new ArrayList<String>();
		while (keysSet.hasNext()) {
			String s = String.valueOf(keysSet.next());
			cell.add(s);
		}
		return redisTemplate.delete(cell);
	}

	/**
	 * 普通缓存模糊匹配获取
	 *
	 * @param key 键
	 * @return 值
	 */
	public  Set<String> getKeys(String key) {
		if(null == key || StringUtil.isNullOrEmpty(key.trim()))
		{
			logger.error(" don't get all the keys, danger");
			return new HashSet<>();
		}
		return redisTemplate.keys(key + "*");
	}

	/**
	 * 普通缓存放入
	 *
	 * @param key   键
	 * @param value 值
	 * @return true成功 false失败
	 */
	public boolean set(String key, Object value) {
		try {
			redisTemplate.opsForValue().set(key, value);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}

	}

	/**
	 * 普通缓存放入并设置时间
	 *
	 * @param key   键
	 * @param value 值
	 * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
	 * @return true成功 false 失败
	 */
	public boolean set(String key, Object value, long time) {
		try {
			if (time > 0) {
				redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
			} else {
				set(key, value);
			}
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 递增
	 *
	 * @param key   键
	 * @param delta 要增加几(大于0)
	 * @return
	 */
	public long incr(String key, long delta) {
		if (delta < 0) {
			throw new RuntimeException("递增因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, delta);
	}

	/**
	 * 递增
	 *
	 * @param key   键
	 * @param delta 要增加几(大于0)
	 * @return
	 */
	public Double incrForDouble(String key, Double delta) {
		if (delta < 0) {
			throw new RuntimeException("递增因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, delta);
	}

	/**
	 * 递减
	 *
	 * @param key   键
	 * @param delta 要减少几(小于0)
	 * @return long
	 */
	public Double decrForDouble(String key, Double delta) {
		if (delta < 0) {
			throw new RuntimeException("递减因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, -delta);
	}

	/**
	 * 递减
	 *
	 * @param key   键
	 * @param delta 要减少几(小于0)
	 * @return long
	 */
	public long decr(String key, long delta) {
		if (delta < 0) {
			throw new RuntimeException("递减因子必须大于0");
		}
		return redisTemplate.opsForValue().increment(key, -delta);
	}

	//================================Map=================================

	/**
	 * HashGet
	 *
	 * @param key  键 不能为null
	 * @param item 项 不能为null
	 * @return 值
	 */
	public Object hget(String key, String item) {
		return redisTemplate.opsForHash().get(key, item);
	}

	/**
	 * 获取hashKey对应的所有键值
	 *
	 * @param key 键
	 * @return 对应的多个键值
	 */
	public Map<Object, Object> hmget(String key) {
		return redisTemplate.opsForHash().entries(key);
	}

	/**
	 * HashSet
	 *
	 * @param key 键
	 * @param map 对应多个键值
	 * @return true 成功 false 失败
	 */
	public boolean hmset(String key, Map<String, Object> map) {
		try {
			redisTemplate.opsForHash().putAll(key, map);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * HashSet 并设置时间
	 *
	 * @param key  键
	 * @param map  对应多个键值
	 * @param time 时间(秒)
	 * @return true成功 false失败
	 */
	public boolean hmset(String key, Map<String, Object> map, long time) {
		try {
			redisTemplate.opsForHash().putAll(key, map);
			if (time > 0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 向一张hash表中放入数据,如果不存在将创建
	 *
	 * @param key   键
	 * @param item  项
	 * @param value 值
	 * @return true 成功 false失败
	 */
	public boolean hset(String key, String item, Object value) {
		try {
			redisTemplate.opsForHash().put(key, item, value);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 向一张hash表中放入数据,如果不存在将创建
	 *
	 * @param key   键
	 * @param item  项
	 * @param value 值
	 * @param time  时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
	 * @return true 成功 false失败
	 */
	public boolean hset(String key, String item, Object value, long time) {
		try {
			redisTemplate.opsForHash().put(key, item, value);
			if (time > 0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 删除hash表中的值
	 *
	 * @param key  键 不能为null
	 * @param item 项 可以使多个 不能为null
	 */
	public void hdel(String key, Object... item) {
		redisTemplate.opsForHash().delete(key, item);
	}

	/**
	 * 判断hash表中是否有该项的值
	 *
	 * @param key  键 不能为null
	 * @param item 项 不能为null
	 * @return true 存在 false不存在
	 */
	public boolean hHasKey(String key, String item) {
		return redisTemplate.opsForHash().hasKey(key, item);
	}

	/**
	 * hash递增 如果不存在,就会创建一个 并把新增后的值返回
	 *
	 * @param key  键
	 * @param item 项
	 * @param by   要增加几(大于0)
	 * @return
	 */
	public double hincr(String key, String item, double by) {
		return redisTemplate.opsForHash().increment(key, item, by);
	}

	/**
	 * hash递减
	 *
	 * @param key  键
	 * @param item 项
	 * @param by   要减少记(小于0)
	 * @return
	 */
	public double hdecr(String key, String item, double by) {
		return redisTemplate.opsForHash().increment(key, item, -by);
	}

	//============================set=============================

	/**
	 * 根据key获取Set中的所有值
	 *
	 * @param key 键
	 * @return
	 */
	public Set<Object> sMembers(String key) {
		try {
			return redisTemplate.opsForSet().members(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 根据value从一个set中查询,是否存在
	 *
	 * @param key   键
	 * @param value 值
	 * @return true 存在 false不存在
	 */
	public boolean sHasKey(String key, Object value) {
		try {
			return redisTemplate.opsForSet().isMember(key, value);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 将数据放入set缓存
	 *
	 * @param key    键
	 * @param values 值 可以是多个
	 * @return 成功个数
	 */
	public long sAdd(String key, Object... values) {
		try {
			return redisTemplate.opsForSet().add(key, values);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 0;
		}
	}

	/**
	 * 将数据放入set缓存
	 *
	 * @param key    键
	 * @param values 值 可以是多个
	 * @return 成功个数
	 */
	public long sSet(String key, Object... values) {
		try {
			return redisTemplate.opsForSet().add(key, values);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 0;
		}
	}

	/**
	 * 实现命令：LPOP key，移除并返回列表 key的头元素。
	 *
	 * @param key
	 * @return 列表key的头元素。
	 */
	public Object sPop(String key) {
		return redisTemplate.opsForSet().pop(key);
	}

	/**
	 * 将set数据放入缓存
	 *
	 * @param key    键
	 * @param time   时间(秒)
	 * @param values 值 可以是多个
	 * @return 成功个数
	 */
	public long sAddAndTime(String key, long time, Object... values) {
		try {
			Long count = redisTemplate.opsForSet().add(key, values);
			if (time > 0) {
				expire(key, time);
			}
			return count;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 0;
		}
	}


	/**
	 * 获取set缓存的长度
	 *
	 * @param key 键
	 * @return
	 */
	public long sGetSetSize(String key) {
		try {
			return redisTemplate.opsForSet().size(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 0;
		}
	}

	/**
	 * 移除值为value的
	 *
	 * @param key    键
	 * @param values 值 可以是多个
	 * @return 移除的个数
	 */
	public long setRemove(String key, Object... values) {
		try {
			Long count = redisTemplate.opsForSet().remove(key, values);
			return count;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 0;
		}
	}
	//===============================list=================================

	/**
	 * 获取list缓存的内容
	 *
	 * @param key   键
	 * @param start 开始
	 * @param end   结束  0 到 -1代表所有值
	 * @return
	 */
	public List<Object> lGet(String key, long start, long end) {
		try {
			return redisTemplate.opsForList().range(key, start, end);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 获取list缓存的长度
	 *
	 * @param key 键
	 * @return
	 */
	public long lGetListSize(String key) {
		try {
			return redisTemplate.opsForList().size(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 0;
		}
	}

	/**
	 * 通过索引 获取list中的值
	 *
	 * @param key   键
	 * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
	 * @return
	 */
	public Object lGetIndex(String key, long index) {
		try {
			return redisTemplate.opsForList().index(key, index);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

	/**
	 * 将list放入缓存
	 *
	 * @param key   键
	 * @param value 值
	 * @return
	 */
	public boolean lSet(String key, Object value) {
		try {
			redisTemplate.opsForList().rightPush(key, value);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 将list放入缓存
	 *
	 * @param key   键
	 * @param value 值
	 * @param time  时间(秒)
	 * @return
	 */
	public boolean lSet(String key, Object value, long time) {
		try {
			redisTemplate.opsForList().rightPush(key, value);
			if (time > 0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 将list放入缓存
	 *
	 * @param key   键
	 * @param value 值
	 * @return
	 */
	public boolean lSet(String key, List<Object> value) {
		try {
			redisTemplate.opsForList().rightPushAll(key, value);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 将list放入缓存
	 *
	 * @param key   键
	 * @param value 值
	 * @param time  时间(秒)
	 * @return
	 */
	public boolean lSet(String key, List<Object> value, long time) {
		try {
			redisTemplate.opsForList().rightPushAll(key, value);
			if (time > 0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 根据索引修改list中的某条数据
	 *
	 * @param key   键
	 * @param index 索引
	 * @param value 值
	 * @return
	 */
	public boolean lUpdateIndex(String key, long index, Object value) {
		try {
			redisTemplate.opsForList().set(key, index, value);
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 移除N个值为value
	 *
	 * @param key   键
	 * @param count 移除多少个
	 * @param value 值
	 * @return 移除的个数
	 */
	public long lRemove(String key, long count, Object value) {
		try {
			Long remove = redisTemplate.opsForList().remove(key, count, value);
			return remove;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return 0;
		}
	}



	/**
	 * 限流工具类
	 *
	 * @param acquireKey
	 * @param acquireCount
	 * @param expireTime
	 * @return
	 * @author shiyf
	 */
	public boolean acquire(String acquireKey, int acquireCount, Long expireTime) {

		DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
		redisScript.setResultType(Long.class);
		ResourceScriptSource resourceScriptSource = new ResourceScriptSource(new ClassPathResource("ratelimit.lua"));
		redisScript.setScriptSource(resourceScriptSource);
		try{
			Long execute = redisTemplate.execute(redisScript, Collections.singletonList(acquireKey), acquireCount, expireTime);
			if(execute>0){
				return true;
			}
		}catch (Exception e){
			logger.error("分布式限流=Key【" + acquireKey + "】,限制次数【" + acquireCount + "】，限制时间【" + expireTime + "】秒 error",e);
		}
		return  false;

		/*Long executeResult = redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
				Object nativeConnection = redisConnection.getNativeConnection();
				ResourceScriptSource resourceScriptSource = new ResourceScriptSource(new ClassPathResource("ratelimit.lua"));
				String scriptAsString = null;
				try {
					scriptAsString = resourceScriptSource.getScriptAsString();
					if (nativeConnection instanceof JedisCluster) {
						return (Long) ((JedisCluster) nativeConnection).eval(scriptAsString, 1, acquireKey, String.valueOf(acquireCount), String.valueOf(expireTime));
					} else if (nativeConnection instanceof Jedis) {
						return (Long) ((Jedis) nativeConnection).eval(scriptAsString, 1, acquireKey, String.valueOf(acquireCount), String.valueOf(expireTime));
					}
				} catch (IOException e) {
					logger.error(e.getMessage(),e);
				}
				return null;
			}
		});
		if (executeResult == 0) {
			logger.info("分布式限流=Key【" + acquireKey + "】,限制次数【" + acquireCount + "】，限制时间【" + expireTime + "】秒");
			return false;
		}
		return true;*/

	}




}
