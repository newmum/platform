package net.evecom.rd.ie.baseline.utils.database.redis;

import net.evecom.rd.ie.baseline.utils.iterable.IterableForamt;
import net.evecom.rd.ie.baseline.utils.verify.CheckUtil;
import redis.clients.jedis.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName: RedisClient
 * @Description: redis操作组件 @author： zhengc @date： 2015年3月9日
 */
public class RedisClient {

	public static Jedis jedis;// 非切片额客户端连接
	public static JedisPool jedisPool;// 非切片连接池
	public static ShardedJedis shardedJedis;// 切片额客户端连接
	public static ShardedJedisPool shardedJedisPool;// 切片连接池


	public RedisClient(JedisConfig jedisConfig) {
		createPools(jedisConfig);
	}

	private void createPools(JedisConfig jedisConfig) {
		String ip = jedisConfig.getIp();
		int post = jedisConfig.getPost();
		String password = jedisConfig.getPassword();
		// 池基本配置
		JedisPoolConfig config = new JedisPoolConfig();
		// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
		config.setMaxTotal(jedisConfig.getMaxActive());
		// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
		config.setMaxIdle(jedisConfig.getMaxIdle());
		// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
		config.setMaxWaitMillis(jedisConfig.getMaxWait());
		// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
		config.setTestOnBorrow(jedisConfig.getTestOnBorrow());

		// 初始化切片池 slave链接
		// List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		// JedisShardInfo jedisShardInfo = new JedisShardInfo(ip, post,
		// "master");
		// jedisShardInfo.setPassword(password);
		// shards.add(jedisShardInfo);
		// 构造池
		// shardedJedisPool = new ShardedJedisPool(config, shards);
		// 初始化非切片池
		if (CheckUtil.isNotNull(password)) {
			jedisPool = new JedisPool(config, ip, post, 3000, password);
		} else {
			jedisPool = new JedisPool(config, ip, post);
		}
	}

	/**
	 * 返还到连接池
	 */
	public void close(Jedis jedis) throws Exception {
		try {
			jedisPool.returnResource(jedis);
		} catch (Exception e) {
			if (jedis.isConnected()) {
				jedis.quit();
				jedis.disconnect();
			}
			throw e;
		}
	}

	/**
	 * 清理所有数据
	 */
	public void clearAll(int index) throws Exception {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(index);
			jedis.flushDB();
		} catch (Exception e) {

			throw e;
		} finally {
			close(jedis);
		}
	}

	/**
	 * 获取所有key值
	 * @param index
	 * @return
	 * @throws Exception
	 */
	public Set<String> getKeyAll(int index) throws Exception {
		Set<String> keys = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(index);
			keys = jedis.keys("*");
		} catch (Exception e) {
			throw e;
		} finally {
			close(jedis);
		}
		return keys;
	}

	/**
	 * 获取key值所剩过期时间
	 *
	 * @param index
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public Long ttl(int index, String key) throws Exception {
		Long time = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(index);
			time = jedis.ttl(key);
		} catch (Exception e) {
			throw e;
		} finally {
			close(jedis);
		}
		return time;
	}

	/**
	 * key值是否存在
	 *
	 * @param index
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public boolean existsKey(int index, String key) throws Exception {
		boolean bo = false;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(index);
			bo = jedis.exists(key);
		} catch (Exception e) {
			throw e;
		} finally {
			close(jedis);
		}
		return bo;
	}

	/**
	 * String得到数据
	 *
	 * @throws Exception
	 */
	public Object get(int index, String key) throws Exception {
		byte[] result = null;
		Object value = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(index);
			result = jedis.get(key.getBytes());
			if (result != null) {
				value = IterableForamt.bytesToObject(result);
			}
		} catch (Exception e) {

			throw e;
		} finally {
			close(jedis);
		}
		return value;
	}

	/**
	 * String存入数据
	 *
	 * @throws Exception
	 */
	public void set(int index, String key, Object value) throws Exception {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(index);
			jedis.set(key.getBytes(), IterableForamt.objectToBytes(value));
		} catch (Exception e) {
			throw e;
		} finally {
			close(jedis);
		}
	}

	/**
	 * String存入数据，有超时时间
	 *
	 * @throws Exception
	 */
	public void set(int index, String key, Object value, int time) throws Exception {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(index);
			jedis.set(key.getBytes(), IterableForamt.objectToBytes(value));
			jedis.expire(key, time);
		} catch (Exception e) {

			throw e;
		} finally {
			close(jedis);
		}
	}

	/**
	 * String删除数据
	 *
	 * @throws Exception
	 */
	public void del(int index, String key) throws Exception {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(index);
			jedis.del(key.getBytes());
		} catch (Exception e) {

			throw e;
		} finally {
			close(jedis);
		}
	}

	/**
	 * Hash存入数据
	 *
	 * @throws Exception
	 */
	public void hset(int index, String key, String field, Object value) throws Exception {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(index);
			jedis.hset(key.getBytes(), field.getBytes(), IterableForamt.objectToBytes(value));
		} catch (Exception e) {

			throw e;
		} finally {
			close(jedis);
		}
	}

	/**
	 * Hash得到数据
	 *
	 * @throws Exception
	 */
	public Object hget(int index, String key, String field) throws Exception {
		byte[] result = null;
		Object value = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(index);
			result = jedis.hget(key.getBytes(), field.getBytes());
			if (result != null) {
				value = IterableForamt.bytesToObject(result);
			}
		} catch (Exception e) {

			throw e;
		} finally {
			close(jedis);
		}
		return value;
	}

	/**
	 * Hash得到map对象所有value
	 *
	 * @throws Exception
	 */
	public List<byte[]> hvals(int index, String key) throws Exception {
		List<byte[]> value = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(index);
			value = jedis.hvals(key.getBytes());
		} catch (Exception e) {

			throw e;
		} finally {
			close(jedis);
		}
		return value;
	}

	/**
	 * Hash存入map对象
	 *
	 * @throws Exception
	 */
	public void hmset(int index, Object key, Map<String, String> hash) throws Exception {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(index);
			jedis.hmset(key.toString(), hash);
		} catch (Exception e) {

			throw e;
		} finally {
			close(jedis);
		}
	}

	/**
	 * Hash存入map对象，有超时时间
	 *
	 * @throws Exception
	 */
	public void hmset(int index, Object key, Map<String, String> hash, int time) throws Exception {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(index);
			jedis.hmset(key.toString(), hash);
			jedis.expire(key.toString(), time);
		} catch (Exception e) {

			throw e;
		} finally {
			close(jedis);
		}
	}

	/**
	 * Hash得到map对象
	 *
	 * @throws Exception
	 */
	public List<String> hmget(int index, Object key, String... fields) throws Exception {
		List<String> result = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(index);
			result = jedis.hmget(key.toString(), fields);
		} catch (Exception e) {

			throw e;
		} finally {
			close(jedis);
		}
		return result;
	}

	/**
	 * Hash删除数据
	 *
	 * @throws Exception
	 */
	public void hdel(int index, String key, String field) throws Exception {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(index);
			jedis.hdel(key.getBytes(), field.getBytes());
		} catch (Exception e) {

			throw e;
		} finally {
			close(jedis);
		}
	}

	/**
	 * Hash得到map对象中的所有key
	 *
	 * @throws Exception
	 */
	public Set<String> hkeys(int index, String key) throws Exception {
		Set<String> result = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(index);
			result = jedis.hkeys(key);
		} catch (Exception e) {

			throw e;
		} finally {
			close(jedis);
		}
		return result;
	}

	/**
	 * List数组长度
	 *
	 * @throws Exception
	 */
	public long llen(int index, String key) throws Exception {
		long len = 0;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(index);
			jedis.llen(key.getBytes());
		} catch (Exception e) {

			throw e;
		} finally {
			close(jedis);
		}
		return len;
	}

	/**
	 * List存储REDIS队列 顺序存储
	 *
	 * @throws Exception
	 */
	public void lpush(int index, byte[] key, byte[] value) throws Exception {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(index);
			jedis.lpush(key, value);
		} catch (Exception e) {

			throw e;
		} finally {
			close(jedis);
		}
	}

	/**
	 * List存储REDIS队列 反向存储
	 *
	 * @throws Exception
	 */
	public void rpush(int index, byte[] key, byte[] value) throws Exception {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(index);
			jedis.rpush(key, value);
		} catch (Exception e) {

			throw e;
		} finally {
			close(jedis);
		}
	}

	/**
	 * 将列表 source 中的最后一个元素(尾元素)弹出，并返回给客户端
	 *
	 * @throws Exception
	 */
	public void rpoplpush(int index, byte[] key, byte[] destination) throws Exception {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(index);
			jedis.rpoplpush(key, destination);
		} catch (Exception e) {

			throw e;
		} finally {
			close(jedis);
		}
	}

	/**
	 * List取得所有数据
	 *
	 * @throws Exception
	 */
	public List<byte[]> getList(int index, byte[] key) throws Exception {
		List<byte[]> list = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(index);
			list = jedis.lrange(key, 0, -1);
		} catch (Exception e) {

			throw e;
		} finally {
			close(jedis);
		}
		return list;
	}

	/**
	 * List按范围取出，第一个是key，第二个是起始位置，第三个是结束位置，jedis.llen获取长度 -1表示取得所有
	 *
	 * @throws Exception
	 */
	public List<byte[]> lrange(int index, byte[] key, int from, int to) throws Exception {
		List<byte[]> result = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(index);
			result = jedis.lrange(key, from, to);
		} catch (Exception e) {

			throw e;
		} finally {
			close(jedis);
		}
		return result;
	}

	public byte[] rpop(int index, byte[] key) throws Exception {
		byte[] bytes = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(index);
			bytes = jedis.rpop(key);
		} catch (Exception e) {

			throw e;
		} finally {
			close(jedis);
		}
		return bytes;
	}

	public Map<byte[], byte[]> hgetAll(int index, byte[] key) throws Exception {
		Map<byte[], byte[]> result = null;
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.select(index);
			result = jedis.hgetAll(key);
		} catch (Exception e) {
			throw e;
		} finally {
			close(jedis);
		}
		return result;
	}
}
