package com.atguigu.kafka.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * Kafka生产者，异步发送带有回调函数，测试Kafka的默认分区策略
 * 默认分区策略可以看源码org.apache.kafka.clients.producer.internals.DefaultPartitioner.class 和
 * org.apache.kafka.clients.producer.ProducerRecord.class
 *
 * https://www.bilibili.com/video/BV1vr4y1677k?p=11&spm_id_from=pageDriver&vd_source=acb9b66ebe2b9150f655070a930aeb12
 * 《【尚硅谷】2022版Kafka3.x教程（从入门到调优，深入全面）》
 * 2022年7月31日20:49:58
 */
public class CustomProducerCallbackPartitions {

    public static void main(String[] args) throws InterruptedException {

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
        for (int i = 0; i < 50; i++) {
            //
            /**
             * send方法默认就是异步发送的
             * new ProducerRecord<>("first", 1, "", "atguigu" + i),第一个参数是主题topic,第二个参数是指定分区的编号，第三个参数是key可以传空,
             * 第四个参数是value值，就是你真正要传送的数据
             *
             * new ProducerRecord<>("first", "a", "atguigu" + i) 这种不指定partition分区时，会根据key的hahcode值与topic的partition数进行取余得到最终要放的partition分区
             * 有一道面试题，我们希望把订单表的数据都发送到kafka的某一个分区，这个怎么实现？非常简单，把表面当做key值就行了，根据key值取模，取出来的值是固定的。
             * new ProducerRecord<>("first", "a", "atguigu" + i) 这种不指定partition分区时，会根据key的hahcode值与topic的partition数进行取余得到最终要放的partition分区。但是分库分表就不行了。
             *
             * new ProducerRecord<>("first", "atguigu" + i), 这种没有指定key也没有指定partitions分区，默认就是粘性分区了。
             *
             */
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

            // 休眠2毫秒，容易将粘性分区打散
            Thread.sleep(2);
        }
        // 3. 关闭资源
        stringStringKafkaProducer.close();
    }
}
