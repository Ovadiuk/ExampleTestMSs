package com.busservices;

import com.busclients.MessageBrokerClient;
import com.configuration.AppProperties;
import com.dto.publisher.User;

public class MessageServicesPublisher {

  private final MessageBrokerClient messageBrokerClient;
  private final AppProperties appProperties;

  public MessageServicesPublisher(MessageBrokerClient messageBrokerClient,
      AppProperties appProperties) {
    this.messageBrokerClient = messageBrokerClient;
    this.appProperties = appProperties;
  }

  public void sendMessage(User user) {
    messageBrokerClient.sendMessage(user,
        appProperties.getPublisherMicroservice().getIncomingExchange(),
        appProperties.getPublisherMicroservice().getIncomingRoutingKey());
  }

  public String getNextFromQueue() {
    return messageBrokerClient.getNextFromQueue(appProperties.getPublisherMicroservice().getOutgoingQueue(),
        appProperties.getQueueTimeout());
  }

  public void cleanQueue(){
    messageBrokerClient.cleanQueue(appProperties.getPublisherMicroservice().getOutgoingQueue(),
        appProperties.getQueueTimeout());
  }
}
