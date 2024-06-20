package com.mchen2.myapp.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = {"mc_test"})
    public void onNormalMessage(ConsumerRecords<String, Object> records, Acknowledgment acknowledgment) {
        System.out.println("数量: " + records.count());
        for (ConsumerRecord<String, Object> record : records) {
            System.out.println("简单消费：" + record.topic() + "-" + record.partition() + "=" +
                    record.value());
        }
    }

}
