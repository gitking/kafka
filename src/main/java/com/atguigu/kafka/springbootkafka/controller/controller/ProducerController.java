package com.atguigu.kafka.springbootkafka.controller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * https://www.bilibili.com/video/BV1vr4y1677k?p=71&vd_source=acb9b66ebe2b9150f655070a930aeb12
 * 《【尚硅谷】2022版Kafka3.x教程（从入门到调优，深入全面）》  《 P71 71_尚硅谷_Kafka_集成_SpringBoot生产者  09:35》
 * 2022年8月7日10:05:30
 */
@RestController
public class ProducerController {

    @Autowired
    KafkaTemplate<String, String> kafka;

    // 在浏览器上面访问:http://localhost:8080/atguigu?msg=hello
    @RequestMapping("/atguigu")
    public String data(String msg) {
        // 接受外部传送过来的数据，通过kafka发送出去
        kafka.send("firstTopic主题", msg);
        return "ok";
    }
}
