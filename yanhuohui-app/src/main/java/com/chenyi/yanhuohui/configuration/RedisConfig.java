package com.chenyi.yanhuohui.configuration;

import com.alibaba.nacos.shaded.org.checkerframework.checker.nullness.qual.NonNull;
import com.alibaba.nacos.shaded.org.checkerframework.checker.nullness.qual.Nullable;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.RemovalListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 1：RedisTemplate
 *
 * 2：CacheManager
 *
 * 前者用于自己书写缓存，后者用于使用springcache
 *
 * 这也分别对应着使用缓存的两种方式：自己设置缓存层、将缓存交给spring管理（当然CacheManager不只能够使用redis）
 */

@Configuration
@EnableCaching
@Slf4j
public class RedisConfig {

    @Autowired
    private CacheLoader cacheLoader;

    @Bean//此时，将我们的redisTemplate加载到了我们的spring的上下文中，applicationContext
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory){
        //1.初始化一个redisTemplate
        RedisTemplate<String,Object> redisTemplate=new RedisTemplate<String,Object>();
        //2.序列话（一般用于key值）
        RedisSerializer<String>  redisSerializer=new StringRedisSerializer();
        //3.引入json串的转化类（一般用于value的处理）
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer=new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper=new ObjectMapper();
        //3.1设置objectMapper的访问权限
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //3.2指定序列化输入类型,就是将数据库里的数据按照一定类型存储到redis缓存中。
        //objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);//最近升级SpringBoot，发现enableDefaultTyping方法过期过期了。可以使用下面的方法代替
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_ARRAY);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        //4.创建链接
        redisTemplate.setConnectionFactory(factory);
        //4.1redis key值序列化
        redisTemplate.setKeySerializer(redisSerializer);
        //4.2value序列化，因为我们的value大多是通过对象转化过来的，所以使用jackson2JsonRedisSerializer
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        //4.3value序列化，hashmap的序列话
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        return redisTemplate;
    }

    @Bean(name = "redisCacheManager")
    @Primary
    public CacheManager cacheManager(RedisConnectionFactory factory){
        //1.序列话（一般用于key值）
        RedisSerializer<String> redisSerializer=new StringRedisSerializer();
        //2.引入json串的转化类（一般用于value的处理）
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer=new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper=new ObjectMapper();
        //2.1设置objectMapper的访问权限
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //2.2指定序列化输入类型,就是将数据库里的数据按照一定类型存储到redis缓存中。
        //objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);//最近升级SpringBoot，发现enableDefaultTyping方法过期过期了。可以使用下面的方法代替
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_ARRAY);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        //3.序列话配置，乱码问题解决以及我们缓存的时效性
        RedisCacheConfiguration config=RedisCacheConfiguration.defaultCacheConfig().
                entryTtl(Duration.ofSeconds(1000)).//缓存时效性设置
                serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)).//key序列化
                serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer)).//value序列化
                disableCachingNullValues();//空值不存入缓存
        //4.创建cacheManager链接并设置属性
        RedisCacheManager cacheManager= RedisCacheManager.builder(factory).cacheDefaults(config).build();
        return cacheManager;
    }

    /** LoadingCache对Cache做了一个升级，提供了更加强大的功能
     * caffeine是不缓存null值的，如果在load的时候返回null，caffeine将会把对应的key从缓存中删除，
     * 同时，loadAll返回的map里是不可以包含value为null的数据，否则将会报NullPointerException
     * */
    @Bean(name = "LoadingCacheByCaffeine")
    public LoadingCache cacheManagerWithAsyncCacheLoader(){
        log.info("cacheManagerWithCacheLoading" );
        LoadingCache<Integer, String> loadingCache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.SECONDS)
                .refreshAfterWrite(500, TimeUnit.MILLISECONDS)
                .maximumSize(10) // 缓存最大数量
                .removalListener((RemovalListener<Integer, String>) (integer, s, removalCause) -> {
                    System.out.println("key:" + integer + " value:" + s + " cause:"+removalCause);
                })
                .build(new CacheLoader<Integer, String>() {
                    @Nullable
                    @Override
                    public String load(@NonNull Integer i) throws Exception {
                        return i.toString();
                    }

                    @Override
                    public Map<Integer, String> loadAll(Iterable<? extends Integer> keys) {
                        Map<Integer, String> map = new HashMap<>();
                        for (Integer i : keys) {
                            map.put(i, i.toString());
                        }
                        return map;
                    }
                });
        return loadingCache;
    }

    @Bean(name = "caffeineCacheManager")
    public CacheManager cacheManagerWithCaffeine(){
        log.info("This is cacheManagerWithCaffeine");
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        Caffeine caffeine = Caffeine.newBuilder()
                //cache的初始容量值
                .initialCapacity(100)
                //maximumSize用来控制cache的最大缓存数量，maximumSize和maximumWeight不可以同时使用，
                .maximumSize(1000)
        //控制最大权重
//                .maximumWeight(100);
//                .expireAfter();
        //使用refreshAfterWrite必须要设置cacheLoader
                .refreshAfterWrite(5,TimeUnit.SECONDS);
        cacheManager.setCaffeine(caffeine);
        cacheManager.setCacheLoader(cacheLoader);
        cacheManager.setCacheNames(getNames());
//        cacheManager.setAllowNullValues(false);
        return cacheManager;
    }

    //@Cacheable注解有个属性可以设置这个ID生成器
    @Bean(name = "idGenerator")
    public KeyGenerator idGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(params[0].toString());
                return sb.toString();
            }
        };
    }

    private static List<String> getNames(){
        List<String> names = new ArrayList<>(2);
        names.add("outLimit");
        names.add("notOutLimit");
        return names;
    }
}
