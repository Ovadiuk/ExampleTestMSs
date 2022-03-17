package com.tests;

import com.Application;
import com.dto.publisher.User;
import com.step.PublisherSteps;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@SpringBootTest(classes = {Application.class})
public class PublisherTest extends AbstractTestNGSpringContextTests {

  @Autowired
  private PublisherSteps publisherSteps;

  private User user;

  @BeforeClass
  public void cleanQueue(){
    publisherSteps.cleanQueues();
  }

  @BeforeMethod
  public void generationData() {
    String correlationId = UUID.randomUUID().toString();
    user = User.builder()
        .name("AQA")
        .correlationId(correlationId)
        .build();
  }

  @Test
  public void publisherTest() {
    publisherSteps.sendSuccessfullyMessageToMS(user);
    User acUser = publisherSteps.readMessageFromQueue();
    Assert.assertEquals(user, acUser);
  }


}
