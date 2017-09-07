import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;


public class RedisUtil {


    private final static Logger LOGGER = LoggerFactory.getLogger(RedisUtil.class);

    private static final String DEFAULT_REDIS_PROP = "properties/redis.properties";

    private static final long TIMEOUT_MILLISAECOND_DEFAULT = 10 * 1000L;

    private static final long EXPIRED_MILLISAECOND_DEFAULT = 20 * 1000L;

    private static JedisPool jedisPool = null;

    private static void initialPool() {
        try {
       
            Properties props = loadProperties();
            JedisPoolConfig config = configJedisPoolConfig(props);
            jedisPool = buildJedisPool(props, config);
        } catch (Exception e) {
            LOGGER.error("------ 初始化redis连接池出现异常 ------", e);
        }
    }

    private static Properties loadProperties() {
        Properties defaultProperties = new Properties();
        try {
            defaultProperties.load(RedisUtil.class.getClassLoader().getResourceAsStream(DEFAULT_REDIS_PROP));
        } catch (Exception e) {
            LOGGER.error("------ 加载redis配置文件出现异常 ------", e);
        }
        return defaultProperties;
    }


    private static JedisPoolConfig configJedisPoolConfig(Properties props) {
        JedisPoolConfig config = new JedisPoolConfig();
        String maxActive = props.getProperty("redis.pool.maxActive");
        if (StringUtils.isNumeric(maxActive)) {
            config.setMaxActive(Integer.valueOf(maxActive));
        }
        String maxIdle = props.getProperty("redis.pool.maxIdle");
        if (StringUtils.isNumeric(maxIdle)) {
            config.setMaxIdle(Integer.valueOf(maxIdle));
        }
        String maxWait = props.getProperty("redis.pool.maxWait");
        if (StringUtils.isNumeric(maxWait)) {
            config.setMaxWait(Long.valueOf(maxWait));
        }
        String testOnBorrow = props.getProperty("redis.pool.testOnBorrow");
        if (StringUtils.isNotBlank(testOnBorrow)) {
            config.setTestOnBorrow(Boolean.valueOf(testOnBorrow));
        }
        String testOnReturn = props.getProperty("redis.pool.testOnReturn");
        if (StringUtils.isNotBlank(testOnReturn)) {
            config.setTestOnReturn(Boolean.valueOf(testOnBorrow));
        }
        return config;
    }

    
    private static JedisPool buildJedisPool(Properties props, JedisPoolConfig config) {
        // 创建连接池
        String host = props.getProperty("redis.host");
        String portStr = props.getProperty("redis.port");
        String password = props.getProperty("redis.password");
        int port = Protocol.DEFAULT_PORT;
        if (StringUtils.isNumeric(portStr)) {
            port = Integer.parseInt(portStr);
        }
        int timeout = Protocol.DEFAULT_TIMEOUT;
        String timeoutStr = props.getProperty("redis.timeout");
        if (StringUtils.isNumeric(timeoutStr)) {
            timeout = Integer.parseInt(timeoutStr);
        }
        return new JedisPool(config, host, port, timeout, password);
    }

 
    private static synchronized void poolInit() {
        if (jedisPool == null) {
            initialPool();
        }
    }


    private static Jedis getJedis() {
        if (jedisPool == null) {
            poolInit();
        }
        Jedis jedis = null;
        try {
            if (jedisPool != null) {
                jedis = jedisPool.getResource();
                jedis.select(getDBIndex());
            }
        } catch (Exception e) {
            LOGGER.error("------ 获取redis连接出现异常 ------", e);
        }
        return jedis;
    }


    private static void release(Jedis jedis, boolean isBroken) {
        try {
            if (jedis != null) {
                if (isBroken) {
                    jedisPool.returnBrokenResource(jedis);
                } else {
                    jedisPool.returnResource(jedis);
                }
            }
        } catch (Exception e) {
            LOGGER.error("------ 释放redis连接出现异常 ------", e);
        }
    }

   
    private static int getDBIndex() {
        return 2;//read from config platform
    }

    /**
     * 
     * 功能描述: <br>
     * 往redis中添加数据
     * 
     * @param key 键
     * @param value 值
     * @param cacheSeconds 生命时间
     * @return 原数据
     */
    public static String addString(String key, String value, int cacheSeconds) {
        Jedis jedis = null;
        boolean isBroken = false;
        String lastVal = null;
        try {
            jedis = getJedis();
            lastVal = jedis.getSet(key, value);
            if (cacheSeconds != 0) {
                jedis.expire(key, cacheSeconds);
            } else {
                LOGGER.error("------ 往redis中保存数据的操作异常（设置生存时间为0），key:" + key + ",value:" + value);
            }
        } catch (Exception e) {
            isBroken = true;
            LOGGER.error("------ 往redis中保存数据出现异常，key:" + key + ",value:" + value, e);
        } finally {
            release(jedis, isBroken);
        }
        return lastVal;
    }

    /**
     * 
     * 功能描述: <br>
     * 从redis中获取数据
     * 
     * @param key 键
     * @return 值
     */
    public static String getString(String key) {
        String value = null;
        boolean isBroken = false;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            if (jedis == null) {
                Thread.sleep(50);
                jedis = getJedis();
            }
            value = jedis.get(key);
            value = StringUtils.isNotBlank(value) && !"nil".equalsIgnoreCase(value) ? value : null;
        } catch (Exception e) {
            isBroken = true;
            LOGGER.error("------ 从redis中获取数据出现异常，key:" + key, e);
        } finally {
            release(jedis, isBroken);
        }
        return value;
    }

    /**
     * 功能描述: <br>
     * 根据条件查询键集合
     * 
     * @param pattern 条件
     * @return 符合条件的键集合
     */
    public static Set<String> keys(String pattern) {
        Set<String> list = null;
        boolean isBroken = false;
        Jedis jedis = null;
        try {
            jedis = getJedis();
            list = jedis.keys(pattern);
        } catch (Exception e) {
            isBroken = true;
            LOGGER.error("------ 从redis中根据条件查询key集合出现异常，pattern:" + pattern + " ------", e);
        } finally {
            release(jedis, isBroken);
        }
        return list;
    }

    /**
     * 功能描述: <br>
     * 从redis中清除数据
     * 
     * @param cacheKey 键
     */
    public static void delKey(String cacheKey) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            if (jedis.exists(cacheKey)) {
                jedis.del(cacheKey);
            }
        } catch (Exception e) {
            isBroken = true;
            LOGGER.error("------ 从redis中删除出现异常，cacheKey:" + cacheKey + " ------", e);
        } finally {
            release(jedis, isBroken);
        }
    }

    public static boolean lock(String lockKey) throws Exception {
        return lock(lockKey, TIMEOUT_MILLISAECOND_DEFAULT, EXPIRED_MILLISAECOND_DEFAULT);
    }

    /**
     * 
     * 功能描述: <br>
     * 分布式锁
     *
     * @version [V1.0, 2017年9月7日]
     * @param key
     * @param timeoutMillisecond 锁定等等超时时间
     * @param expireMillisecond 锁失效时间
     * @return
     * @throws Exception
     */
    public static boolean lock(String lockKey, long timeoutMillisecond, long expireMillisecond) throws Exception {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            Random random = new Random();

            long timeout = timeoutMillisecond;
            while (timeout >= 0) {
                long expiresTime = System.currentTimeMillis() + expireMillisecond + 1;
                String newExpireTime = String.valueOf(expiresTime);
                if (jedis.setnx(lockKey, newExpireTime) == 1) {
                    return true;
                }
                String currentExpiredTime = jedis.get(lockKey);
                if (currentExpiredTime != null && Long.parseLong(currentExpiredTime) < System.currentTimeMillis()) {

                    String oldExpireTime = jedis.getSet(lockKey, newExpireTime);
                    if (currentExpiredTime.equals(oldExpireTime)) {
                        return true;
                    }
                }
                long sleepTime = random.nextInt(100) + 30;
                timeout -= sleepTime;// TODO 100修改成常量
                Thread.sleep(sleepTime);
            }

        } catch (Exception e) {
            isBroken = true;
            LOGGER.error("------ redis锁定lockKey = {} 出现异常", lockKey, e);
            throw e;
        } finally {
            release(jedis, isBroken);
        }

        return false;
    }

    public static void unlock(String lockKey) {
        Jedis jedis = null;
        boolean isBroken = false;
        try {
            jedis = getJedis();
            String expireTime = jedis.get(lockKey);
            if (expireTime != null && Long.parseLong(expireTime) > System.currentTimeMillis()) {
                jedis.del(lockKey);
            }
        } catch (Exception e) {
            isBroken = true;
            LOGGER.error("------ redis释放lockKey = {} 出现异常", lockKey, e);
        } finally {
            release(jedis, isBroken);
        }

    }

}
