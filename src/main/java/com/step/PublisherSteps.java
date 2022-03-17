package com.step;

import com.busservices.MessageServicesPublisher;
import com.dto.publisher.PublisherResponse;
import com.dto.publisher.User;
import com.google.gson.Gson;
import com.services.Publisher;
import io.qameta.allure.Step;
import lombok.SneakyThrows;
import org.testng.Assert;
import retrofit2.Response;

public class PublisherSteps {
  private final Publisher publisher;
  private final MessageServicesPublisher messageServicesPublisher;
  private final Gson gson;

  public PublisherSteps (Publisher publisher,
      MessageServicesPublisher messageServicesPublisher,
      Gson gson){
    this.publisher = publisher;
    this.messageServicesPublisher = messageServicesPublisher;
    this.gson = gson;

  }
  @SneakyThrows
  @Step ("User send message to publisher MS}")
  public void sendSuccessfullyMessageToMS(User user){
    Response<PublisherResponse> publisherResponse = publisher.sendMessage(user).execute();
    Assert.assertTrue(publisherResponse.isSuccessful());
    Assert.assertNotNull(publisherResponse.body());
    Assert.assertEquals(publisherResponse.body().getResult(),"OK");
  }

  @Step ("User read message from  queue}")
  public User readMessageFromQueue(){
    String payload = messageServicesPublisher.getNextFromQueue();
    return gson.fromJson(payload,User.class);
  }

  @Step("User cleans test queue Publisher")
  public void cleanQueues(){
       messageServicesPublisher.cleanQueue();
  }
}
