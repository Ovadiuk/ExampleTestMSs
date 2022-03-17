package com.busclients;

import com.busservices.RabbitMessageTemplate;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class MessageBrokerClientRabbitMqImpl implements MessageBrokerClient {

  private final RabbitTemplate rabbitTemplate;
  private final RabbitMessageTemplate rabbitMessageTemplate;

  public MessageBrokerClientRabbitMqImpl(RabbitTemplate rabbitTemplate,
      RabbitMessageTemplate rabbitMessageTemplate) {
    this.rabbitTemplate = rabbitTemplate;
    this.rabbitMessageTemplate = rabbitMessageTemplate;
  }

  @Override
  @Step("send message payload - {payload}, exchange - {exchange}, routingKey - {routingKey} ")
  public void sendMessage(Object payload, String exchange, String routingKey) {
    rabbitTemplate.convertAndSend(exchange, routingKey, rabbitMessageTemplate.wrap(payload));
  }

  @Override
  @Step("User read message from queue - {queue}")
  @Attachment
  public String getNextFromQueue(String queue, long queueResponseTimeout) {
    Message message = rabbitTemplate.receive(queue, queueResponseTimeout);
    if (message == null) {
      throw new RuntimeException("queue is empty name = " + queue);
    }
    return rabbitMessageTemplate.unwrap(message);
  }

  @Override
  public void cleanQueue(String queue, long queueResponseTimeout) {
    while (rabbitTemplate.receive(queue, 200) != null){
    }
  }
}
