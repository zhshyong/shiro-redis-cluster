/**
 * Copyright 2017 asiainfo Inc.
 **/
import org.crazycake.shiro.RedisCache;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.SerializeUtils;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.JedisCluster;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author zhangsy
 */
public class AppTest {
  private ApplicationContext sContext;

  private RedisManager redisManager;

  private RedisCache redisCache;

  private final Logger logger = LoggerFactory.getLogger(AppTest.class.getName());


  @Before
  public void setUp() {
    sContext = new ClassPathXmlApplicationContext("classpath:spring-config-cache.xml");
    redisManager = (RedisManager) sContext.getBean("redisManager");
    redisCache = (RedisCache) sContext.getBean("redisCache");

  }

  @Test
  public void test() throws Exception {
    JedisCluster jedisCluster = (JedisCluster) sContext.getBean("jedisCluster");
    System.out.print(jedisCluster.toString());
    logger.debug(jedisCluster.toString());
  }

  @Test
  public void test1() {
    RedisManager redisManager = (RedisManager) sContext.getBean("redisManager");
    String key = "key3";
    String key1 = "key4";
    String value3 = "value3";
    String value4 = "value4";
    redisManager.set(SerializeUtils.serialize(key), SerializeUtils.serialize(value3));
    redisManager.set(SerializeUtils.serialize(key1), SerializeUtils.serialize(value4));
  }

  @Test
  public void test2() {
    String key = "key19";
    System.out.println(redisCache.get(key));
  }

  @Test
  public void test4() {
    RedisManager redisManager = (RedisManager) sContext.getBean("redisManager");
    String key = "key4";
    System.out.println("get key------" + SerializeUtils.deserialize(redisManager.get(SerializeUtils.serialize(key))));
    redisManager.del(SerializeUtils.serialize(key));
    System.out.print("get key------" + SerializeUtils.deserialize(redisManager.get(SerializeUtils.serialize(key))));
  }

  @Test
  public void testTreeSet() throws Exception {
    Set<String> treeSet = redisCache.keys();
    Iterator it = treeSet.iterator();
    while (it.hasNext()) {
      System.out.println("----key---" + it.next());
    }
  }

  @Test
  public void testRedisCachePut() {
    for (int i = 0; i < 20; i++) {
      redisCache.put("key"+i, i);
    }
    for (int i = 0; i < 20; i++) {
      System.out.println("获取key" + i + ":" + redisCache.get("key"+i));
    }
  }

  @Test
  public void testRedisCacheSize() {
    System.out.print(redisCache.size());
  }

  @Test
  public void testValues() {
    Collection<Object> collection =  redisCache.values();
    Iterator it = collection.iterator();
    while(it.hasNext()) {
      System.out.println(it.next());
    }
  }

  @Test
  public void testRemove() {
    String key = "key1";
    System.out.print(redisCache.remove(key));
  }
  @Test
  public void testclearDb() {
    redisCache.clear();
  }
}
