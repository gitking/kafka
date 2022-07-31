package com.atguigu.kafka.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * Kafka生产者，异步发送带有回调函数
 *
 * https://www.bilibili.com/video/BV1vr4y1677k?p=11&spm_id_from=pageDriver&vd_source=acb9b66ebe2b9150f655070a930aeb12
 * 《【尚硅谷】2022版Kafka3.x教程（从入门到调优，深入全面）》
 * 2022年7月31日20:49:58
 */
public class CustomProducerCallback {

    public static void main(String[] args) {

        // 0 properties配置
        Properties properties = new Properties();
        // 连接集群,bootstrap.servers配置,这个参数是必须要配置的
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop102:9092,hadoop103:9092");
        // 指定对应的key和value的序列化类型,key.serializer,这个参数是必须要配置的
        // properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");这行代码等价于下面的那行代码
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // 1. 创建kafka生产者对象
        KafkaProducer<String, String> stringStringKafkaProducer = new KafkaProducer<>(properties);
        // 2. 发送一条数据,new ProducerRecord<>("first", "atguigu")第一个参数是topic主题的名字，第二个是要发送的值
//        stringStringKafkaProducer.send(new ProducerRecord<>("first", "atguigu"));
        // 发送一批数据
        for (int i = 0; i < 5; i++) {
            // 模式就是异步发送的
            stringStringKafkaProducer.send(new ProducerRecord<>("first", "atguigu" + i), new Callback() {
                // 异步回调函数callback
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception == null) {
                        // exception==null说明发送成功了
                        System.out.println("metadata是元数据的意思，主题:" + metadata.topic() + "分区:" + metadata.partition());
                    }
                }
            });
        }
        // 3. 关闭资源
        stringStringKafkaProducer.close();
    }
}
