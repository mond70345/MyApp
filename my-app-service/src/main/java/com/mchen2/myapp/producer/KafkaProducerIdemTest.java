package com.mchen2.myapp.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

public class KafkaProducerIdemTest {

    public static void main(String[] args) throws Exception {


        // TODO 创建配置对象
        Map<String, Object> configMap = new HashMap<>();
        configMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // TODO 对生产的数据K, V进行序列化的操作
        configMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        configMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        configMap.put(ProducerConfig.ACKS_CONFIG, "-1");
        configMap.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        configMap.put(ProducerConfig.RETRIES_CONFIG, 5);
        configMap.put(ProducerConfig.BATCH_SIZE_CONFIG, 5);
        configMap.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 3000);

        // 增加事务ID
        configMap.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "my-tx-id");

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(configMap);

        // 初始化事务
        producer.initTransactions();
        try {
            // 开启事务
            producer.beginTransaction();
            for (int i = 0; i < 10; i++) {
                ProducerRecord<String, String> record = new ProducerRecord<String, String>(
                        "test2", "akey_" + i, "avalue_" + i
                );

                final Future<RecordMetadata> send = producer.send(record);
//                if (i == 5) {
//                    throw new RuntimeException("模拟异常");
//                }

            }

            // 提交事务
            producer.commitTransaction();
        } catch (Exception e) {
            // 终止事务
            producer.abortTransaction();
        } finally {
            producer.close();
        }
    }
}
