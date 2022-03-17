package com.busclients;

public interface MessageBrokerClient {
  void sendMessage(Object payload, String exchange, String routingKey);
  String getNextFromQueue(String queue, long queueResponseTimeout);
  void cleanQueue(String queue, long queueResponseTimeout);
}
