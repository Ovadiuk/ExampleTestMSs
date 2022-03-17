package com.busservices;

import com.busclients.MessageBrokerClient;
import com.configuration.AppProperties;
import com.dto.publisher.User;

public class MessageServicesMiddleWare {
  private final MessageBrokerClient messageBrokerClient;
  private final AppProperties appProperties;

  public MessageServicesMiddleWare(MessageBrokerClient messageBrokerClient,
      AppProperties appProperties){
    this.messageBrokerClient = messageBrokerClient;
    this.appProperties = appProperties;
  }

  public void sendMessage(User user) {
    messageBrokerClient.sendMessage(user,
        appProperties.getSubscriberMicroservice().getIncomingExchange(),
        appProperties.getSubscriberMicroservice().getIncomingRoutingKey());
  }

  public String getNextFromQueue() {
    return messageBrokerClient.getNextFromQueue(appProperties.getSubscriberMicroservice().getOutgoingQueue(),
        appProperties.getQueueTimeout());
  }

  public void cleanQueue(){
    messageBrokerClient.cleanQueue(appProperties.getSubscriberMicroservice().getOutgoingQueue(),
        appProperties.getQueueTimeout());
  }
}
