package com.example.demo.video;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RabbitListener(queues = "${gzcss.media.queue-name:'default-video-process'}")
public class VideoConsumer {

    @RabbitHandler
    public void receive(@Header(AmqpHeaders.CHANNEL) Channel channel,
                        @Header(AmqpHeaders.DELIVERY_TAG) Long deliveryTag, String in)
            throws IOException, InterruptedException {
        System.out.println("[x] Received '" + in + "'");
        Thread.sleep(10);
        channel.basicAck(deliveryTag, false);
    }
}
