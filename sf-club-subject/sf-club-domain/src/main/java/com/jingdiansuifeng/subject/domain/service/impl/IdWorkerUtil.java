package com.jingdiansuifeng.subject.domain.service.impl;

/**
 * Twitter的SnowFlake算法
 */
public class IdWorkerUtil {
    // 系统时间戳位数
    private final long nodeIdBits = 10L;
    // 序列号位数
    private final long sequenceBits = 12L;
    // 最大的序列号
    private final long maxSequence = ~(-1L << sequenceBits);

    private long nodeId;
    private long lastTimestamp = -1L;
    private long sequence = 0L;

    public IdWorkerUtil(long nodeId) {
        this.nodeId = nodeId << sequenceBits;
    }

    public synchronized long nextId() {
        long timestamp = System.currentTimeMillis();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("时钟回拨");
        }
        if (timestamp == lastTimestamp) {
            // 在同一时间戳内
            sequence = (sequence + 1) & maxSequence;
            if (sequence == 0) {
                // 当前时间戳下序列号用完了
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            // 时间戳改变，重置序列号
            sequence = 0;
        }
        lastTimestamp = timestamp;
        return (timestamp << (nodeIdBits + sequenceBits)) 
               | nodeId 
               | sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}