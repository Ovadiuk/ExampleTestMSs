package com.step;

import com.busservices.MessageServicesMiddleWare;
import com.busservices.MessageServicesPublisher;
import com.dto.publisher.User;
import com.google.gson.Gson;
import io.qameta.allure.Step;

public class MiddleWareStep {
  private final MessageServicesPublisher messageServicesPublisher;
  private final MessageServicesMiddleWare messageServicesMiddleWare;
  private final Gson gson;

  public MiddleWareStep (MessageServicesPublisher messageServicesPublisher,
      MessageServicesMiddleWare messageServicesMiddleWare,
      Gson gson){
    this.messageServicesPublisher = messageServicesPublisher;
    this.messageServicesMiddleWare = messageServicesMiddleWare;
    this.gson = gson;
  }

  @Step("User send message to exchange MiddleWare")
  public void sendSuccessfullyMessageToMS(User user){
    messageServicesPublisher.sendMessage(user);
  }

  @Step ("User read message from queue MiddleWare")
  public User readMessageFromQueue(){
    String payload = messageServicesMiddleWare.getNextFromQueue();
    return gson.fromJson(payload,User.class);
  }

  @Step("User cleans test queue Publisher and MiddleWare")
  public void cleanQueues(){
    messageServicesMiddleWare.cleanQueue();
    messageServicesPublisher.cleanQueue();
  }
}
