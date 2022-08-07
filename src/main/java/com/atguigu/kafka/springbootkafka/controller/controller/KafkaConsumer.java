package com.atguigu.kafka.springbootkafka.controller.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * https://www.bilibili.com/video/BV1vr4y1677k?p=72&spm_id_from=pageDriver&vd_source=acb9b66ebe2b9150f655070a930aeb12
 *
 * 《【尚硅谷】2022版Kafka3.x教程（从入门到调优，深入全面）》
 * 《 P72 72_尚硅谷_Kafka_集成_SpringBoot消费者 04:12》
 */
@Configuration
public class KafkaConsumer {


    /**
     * # 指定消费者组的(zhi ding xiao fei zhe zu de) group_id,消费者组有注解可以用
     * spring.kafka.consumer.group-id=atguigu 这个配置不需要在yml配置文件里面配置，其实是有注解的
     * @param msg
     */
    @KafkaListener(topics = "firsttopic主题")
    public void consumerTopic(String msg) {
        System.out.println("Kafka 消费者监听firsttopic主题，收到的消息为:" + msg);
    }
}
