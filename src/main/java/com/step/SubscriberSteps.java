package com.step;

import com.busservices.MessageServicesMiddleWare;
import com.dto.publisher.User;
import io.qameta.allure.Step;

public class SubscriberSteps {

  private final MessageServicesMiddleWare messageServicesMiddleWare;

  public SubscriberSteps (MessageServicesMiddleWare messageServicesMiddleWare){
    this.messageServicesMiddleWare = messageServicesMiddleWare;
  }

  @Step("User send message to exchange Subscriber}")
  public void sendSuccessfullyMessageToMS(User user){
    messageServicesMiddleWare.sendMessage(user);
  }

  @Step("User cleans test queue MiddleWare")
  public void cleanQueues(){
    messageServicesMiddleWare.cleanQueue();
  }

}
