package com.atguigu.kafka.springbootkafka.controller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
