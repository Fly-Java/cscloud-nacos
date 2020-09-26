package com.zzp.second.utils;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
public class RedisServiceUtil {

    private final RedisTemplate redisTemplate;
    public String hget(String key,String field){
        //  System.out.println(redisTemplate.opsForHash().keys(key));
        String ret= (String)redisTemplate.opsForHash().get(key,field);
        return ret;
    }
    public void hset(String key,String field,String value){
        redisTemplate.opsForHash().put(key,field,value);
    }
    public void hset(String key,String field,Object value){
        redisTemplate.opsForHash().put(key,field,value);
    }
    @Deprecated
    public List hKeys(String key) {
        return hkeys(key);
    }
    public List hkeys(String key){
        Set keySet =redisTemplate.opsForHash().keys(key);
        List keyList=new ArrayList(keySet);
        return keyList;
    }
    @Deprecated
    public void  hDel(String key,String field) {
        hdel(key,field);
    }
    public Long  hdel(String key,String field){
        Long count= redisTemplate.opsForHash().delete(key,field);
        return count;
    }
    @Deprecated
    public String getValue(String key){
        return (String) get(key);
    }
    public void set(String key,String value,long seconds){
        redisTemplate.opsForValue().set(key,value,seconds, TimeUnit.SECONDS);
    }
    public boolean setnx(String key,String value,long seconds){
        boolean flag=   redisTemplate.opsForValue().setIfAbsent(key,value);
        //设定时长
        if(flag) {
            setExpire(key ,seconds);
        }
        return flag;
    }

    public void setExpire(String key,long seconds){
        redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }
    public long getExpire(String key){
        long expire = redisTemplate.getExpire(key);
        return expire;
    }


    public Object get(String key){
        Object result= redisTemplate.opsForValue().get(key);
        return result;
    }

    public void set(String key,String value ){
        redisTemplate.opsForValue().set(key,value);
    }
    public Long  incr(String key,long step ){
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return operations.increment(key, step);
    }
    public Long  hincr(String key,String field,long step ){
        Long result= redisTemplate.opsForHash().increment(key,field,step);
        return result;
    }
    //对数据加锁
    public boolean tryLock(String key, String lock, int timeout){
        boolean flag = redisTemplate.opsForValue().setIfAbsent(key, lock);
        if(!flag){
            return flag;
        }
        /**
         * 拿到锁设置锁key的超时时间
         */
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        return flag;
    }
    //解除锁
    public  void releaseLock(String key){
         redisTemplate.delete(key);
    }


    /**
     *  List操作
     */
    public long lpush(String key,String value){
        return redisTemplate.opsForList().leftPush(key,value);
    }
    public int rpoplpush_ALL(String key, String key2){
        long i=0;
        int num=0;
        do {
            redisTemplate.opsForList().rightPopAndLeftPush(key,key2);
            i=getLlen(key);
            num++;
        }while(i>0);
        return num;
    }
    public long getLlen(String key){
        return redisTemplate.opsForList().size(key);
    }
    /**
     * @auther wangcz
     * hvals
     */
    public List hVals(String key){
     List list=  redisTemplate.opsForHash().values(key);
        return list;
    }
    /**
     * @auther wangcz
     * hgetall
     */
    public Map hgetAll(String key){
       Map map= redisTemplate.boundHashOps(key).entries();
       return map;
    }

    /**
     * @auther wangcz
     * @param key
     */
    public void del(String key){
        redisTemplate.delete(key);
    }
    /**@auther wangcz
     * 有序集合
     */
    public void zAdd(String key,Long scores,String value){
        redisTemplate.boundZSetOps(key).add(value,scores);
    }

    /**
     * @auther wangcz
     * 删除key
     */
    public void zRem(String key,String value){
        redisTemplate.boundZSetOps(key).remove(value);
    }
    /**@auther wangcz
     * 获取所有key
     */
    public LinkedHashSet  zRange(String key,long v1,long v2){
       LinkedHashSet keys= (LinkedHashSet)redisTemplate.boundZSetOps(key).range(v1,v2);
       return keys;
    }

    public boolean setNx(String key,String value,long seconds){
        ValueOperations<String,String> operations=getOperationsStringRedisSerializer();
        boolean flag= operations.setIfAbsent(key,value);
        //设定时长
        if(flag) {
            setExpire(key ,seconds);
        }
        return flag;
    }

    public Long  inCr(String key,long step ){
        ValueOperations<String,String> operations=getOperationsStringRedisSerializer();
        return operations.increment(key, step);
    }

    public Object getCr(String key){
        ValueOperations<String,String> operations=getOperationsStringRedisSerializer();
        return operations.get(key);
    }

    private ValueOperations<String,String> getOperationsStringRedisSerializer() {
        ValueOperations<String,String> operations= redisTemplate.opsForValue();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return operations;
    }

    @PostConstruct
    public void initRedisTemplateSerializerType(){
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
    }

}
