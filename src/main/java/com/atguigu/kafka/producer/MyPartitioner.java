package com.atguigu.kafka.producer;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * 自定义分区器，必须实现org.apache.kafka.clients.producer.Partitioner这个类
 *
 *
 *
 */
public class MyPartitioner implements Partitioner {
    /**
     * 这个方法是最重要的
     *
     * @param topic The topic name
     * @param key The key to partition on (or null if no key)
     * @param keyBytes The serialized key to partition on( or null if no key)
     * @param value The value to partition on or null
     * @param valueBytes The serialized value to partition on or null
     * @param cluster The current cluster metadata
     * @return
     */
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        // 获取生产者发送过来的数据
        String msgVal = value.toString();
        int partition;
        if (msgVal.contains("atguigu")) {
            // 如果发送过来的数据包含atguigu，就发往0号分区
            partition = 0;
        } else {
            // 不包含就发往1号分区
            partition = 1;
        }
        return partition;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
