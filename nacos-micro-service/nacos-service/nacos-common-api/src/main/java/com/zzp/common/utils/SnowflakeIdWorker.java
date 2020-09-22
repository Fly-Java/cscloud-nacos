package com.zzp.common.utils;


import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Random;

/**
 * Created by Administrator on 2017/6/26.
 */
@Component
public class SnowflakeIdWorker {
    // ==============================Fields===========================================
    /** 开始时间截 (2015-01-01) */
    private static final long twepoch = 1420041600000L;

    /** 机器id所占的位数 */
    private static final long workerIdBits = 5L;

    /** 数据标识id所占的位数 */
    private static final long datacenterIdBits = 5L;

    /** 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数) */
    private static final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    /** 支持的最大数据标识id，结果是31 */
    private static final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    /** 序列在id中占的位数 */
    private static final long sequenceBits = 12L;

    /** 机器ID向左移12位 */
    private static final long workerIdShift = sequenceBits;

    /** 数据标识id向左移17位(12+5) */
    private static final long datacenterIdShift = sequenceBits + workerIdBits;

    /** 时间截向左移22位(5+5+12) */
    private static final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    /** 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095) */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /** 工作机器ID(0~31) */
    private long workerId;

    /** 数据中心ID(0~31) */
    private long datacenterId;

    /** 毫秒内序列(0~4095) */
    private long sequence = 0L;

    /** 上次生成ID的时间截 */
    private long lastTimestamp = -1L;

    private static long DEFAULT_WORKER_ID;

    private static long DEFAULT_CENTER_ID;

    public void setDEFAULT_WORKER_ID(Long workerId) {
        SnowflakeIdWorker.DEFAULT_WORKER_ID=workerId;
    }

    public void setDEFAULT_CENTER_ID(Long datacenterId) {
        SnowflakeIdWorker.DEFAULT_CENTER_ID=datacenterId;
    }

    /**
     * id获取对象
     */
    private static SnowflakeIdWorker idWorker;

    /**
     * 获取下一个id
     * @return
     */
    public static synchronized long getNextId() {
        if(idWorker==null){
            idWorker=new SnowflakeIdWorker();
        }
        return idWorker.nextId();
    }

    protected SnowflakeIdWorker()  {
        this(DEFAULT_WORKER_ID,DEFAULT_CENTER_ID);
    }

    public static String getMerchantNumber() {
        return idWorker.getRandom620(5);
    }
    /**
     * 获取6-10 的随机位数数字
     * @param length    想要生成的长度
     * @return result
     */
    protected synchronized String getRandom620(Integer length) {
        String result = "";
        Random rand = new Random();
        int n = 20;
        if (null != length && length > 0) {
            n = length;
        }
        int randInt = 0;
        for (int i = 0; i < n; i++) {
            randInt = rand.nextInt(10);

            result += randInt;
        }
        return result;
    }
    // ==============================Constructors=====================================
    /**
     * 构造函数
     *
     * @param workerId
     *            工作ID (0~31)
     * @param datacenterId
     *            数据中心ID (0~31)
     */
    protected SnowflakeIdWorker(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    // ==============================Methods==========================================
    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    protected synchronized long nextId() {
        long timestamp = timeGen();

        // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        // 如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            // 毫秒内序列溢出
            if (sequence == 0) {
                // 阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        // 时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        // 上次生成ID的时间截
        lastTimestamp = timestamp;

        // 移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - twepoch) << timestampLeftShift) //
                | (datacenterId << datacenterIdShift) //
                | (workerId << workerIdShift) //
                | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp
     *            上次生成ID的时间截
     * @return 当前时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * 时间转id
     * @param date
     * @return
     */
    public static long timeConvertToId(Date date){
        long timestamp=date.getTime();
        return ((timestamp - twepoch) << timestampLeftShift) //
                | (0 << datacenterIdShift) //
                | (0 << workerIdShift) //
                | 0;
    }

    /**
     * 获取工作机器id
     * @param id
     * @return
     */
    public static long getWorkerId(long id){
        return  (id>> workerIdShift)%((long)Math.pow(2,workerIdBits));
    }

    /**
     * 获取数据中心id
     * @param id
     * @return
     */
    public static long getDatacenterIdId(long id){
        return  (id>> datacenterIdShift)%((long)Math.pow(2,datacenterIdBits));
    }



    /**
     * id转时间
     * @param id
     * @return
     */
    public static Date idConvertToTime(long id){
        return  new Date((id>> timestampLeftShift) +twepoch);
    }

}
