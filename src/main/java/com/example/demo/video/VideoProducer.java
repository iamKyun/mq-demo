package com.example.demo.video;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class VideoProducer implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {
    private final Queue queue;
    private final RabbitTemplate template;
    AtomicInteger count = new AtomicInteger(0);

    public VideoProducer(Queue queue, RabbitTemplate template) {
        this.queue = queue;
        this.template = template;
    }

    public void send() {
        template.convertAndSend(queue.getName(), String.valueOf(count.incrementAndGet()));
    }

    public void send(String message) {
        template.convertAndSend(queue.getName(), message);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            log.info("消息发送成功:correlationData({}),ack({}),cause({})", correlationData, ack, cause);
        } else {
            log.info("消息发送失败:correlationData({}),ack({}),cause({})", correlationData, ack, cause);
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}", exchange, routingKey, replyCode,
                replyText, message);
    }
}
