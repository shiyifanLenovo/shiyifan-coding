package com.pinyg.common.utils;

import org.apache.commons.lang3.RandomUtils;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 */
public class IdWorker {
    /**
     * 时间起始标记点，作为基准，一般取系统的最近时间（一旦确定不能变动）
     */
    private final static long twepoch = 1288834974657L;
    /**
     * 机器标识位数
     */
    private final static long workerIdBits = 5L;
    /**
     * 数据中心ID(0~31)
     */
    private final long dataCenterId;
    /**
     * 数据中心标识所占的位数
     */
    private final static long datacenterIdBits = 5L;
    /**
     * 机器ID最大值
     */
    private final static long maxWorkerId = -1L ^ (-1L << workerIdBits);
    /**
     * 数据中心ID最大值 支持的最大数据标识id，结果是31
     */
    private final static long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    /**
     * 毫秒内自增位
     */
    private final static long sequenceBits = 12L;
    /**
     * 机器ID偏左移12位
     */
    private final static long workerIdShift = sequenceBits;
    /**
     * 数据中心ID左移17位
     */
    private final static long datacenterIdShift = sequenceBits + workerIdBits;
    /**
     * 时间毫秒左移22位
     */
    private final static long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    private final static long sequenceMask = -1L ^ (-1L << sequenceBits);
    /**
     * 上次生产id时间戳
     */
    private static long lastTimestamp = -1L;
    /**
     * 并发控制
     */
    private long sequence = 0L;

    private final long workerId;


    public static class IDWorkInstance{
        private static final IdWorker idWorker=new IdWorker();
    }

    public static long getId(){
        return  IDWorkInstance.idWorker.nextId();
    }


    private IdWorker(){
        this.dataCenterId = getDatacenterId(maxDatacenterId);
        this.workerId = getMaxWorkerId(dataCenterId, maxWorkerId);
    }
    /**
     * @param workerId
     *            工作机器ID
     * @param datacenterId
     *            序列号
     */
    public IdWorker(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.dataCenterId = datacenterId;
    }
    /**
     * 获取下一个ID
     * @return
     */
    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {
            // 当前毫秒内，则+1
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                // 当前毫秒内计数满了，则等待下一秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        // ID偏移组合生成最终的ID，并返回ID
        long nextId = ((timestamp - twepoch) << timestampLeftShift)
                | (dataCenterId << datacenterIdShift)
                | (workerId << workerIdShift) | sequence;

        return nextId;
    }



    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * 获取 最大的机器ID maxWorkerId
     */
    protected static long getMaxWorkerId(long datacenterId, long maxWorkerId) {
        StringBuffer mpid = new StringBuffer();
        mpid.append(datacenterId);
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (!name.isEmpty()) {
         /*
          * GET jvmPid
          */
            mpid.append(name.split("@")[0]);
        }
      /*
       * MAC + PID 的 hashcode 获取16个低位
       */
        return (mpid.toString().hashCode() & 0xffff) % (maxWorkerId + 1);
    }

    /**
     * <p>
     * 数据标识id部分
     * </p>
     */
    protected static long getDatacenterId(long maxDatacenterId) {
        long id = 0L;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            if (network == null) {
                id = 1L;
            } else {
                byte[] mac = network.getHardwareAddress();
                id = ((0x000000FF & (long) mac[mac.length - 1])
                        | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8))) >> 6;
                id = id % (maxDatacenterId + 1);
            }
        } catch (Exception e) {
             return    RandomUtils.nextLong(0,31);
        }
        return id;
    }

    static final CountDownLatch count=new CountDownLatch(10000);
    
    public static void main(String[] args) throws InterruptedException {

        long starEndTime = System.currentTimeMillis();
	    ExecutorService executorService = Executors.newFixedThreadPool(10);


        for (int i=0;i<10000;i++){
	        executorService.execute(()->{
		        long id = IdWorker.getId();
		        System.out.println(id);
		        count.countDown();
	        });

        }
	    count.await();
	    executorService.shutdown();
    	System.out.println("耗时===="+(System.currentTimeMillis()-starEndTime)+"【毫秒】");
        //executorService.shutdown();



    }

}