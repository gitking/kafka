package com.atguigu.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * 自定义生产者的参数，发送参数，缓冲区的参数，目的是为了提高吞吐量
 *
 */
public class CustomProducerParameters {

    public static void main(String[] args) {
        // 0. 配置信息
        Properties properties = new Properties();
        // 配置连接Kafka集群
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop102:9092,hadoop103:9092");
        // 序列化器指定
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // RecordAccumulator缓冲区大小buffer.memory,默认32M,33554432。
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);

        // 批次大小batch.size,默认16k,16384
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);

        // linger.ms:等待时间,默认0ms，这里修改为1ms
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 1);

        // 压缩compression.type,默认none不压缩,可配置值gzip,snappy,lz4和zstd,企业中常用的就是snappy
        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");

        // 1. 创建生产者
        KafkaProducer<String, String> objectObjectKafkaProducer = new KafkaProducer<>(properties);

        // 2. 发送数据
        for (int i = 0; i < 5; i++) {
            objectObjectKafkaProducer.send(new ProducerRecord<>("first", "atguigu" + i));
        }
        // 3. 关闭资源
        objectObjectKafkaProducer.close();
    }
}
