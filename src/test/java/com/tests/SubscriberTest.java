package com.tests;

import com.Application;
import com.dto.publisher.User;
import com.dto.wiremock.FindRequests;
import com.preparerequest.WireMockRequest;
import com.step.SubscriberSteps;
import com.step.WireMockSteps;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@SpringBootTest(classes = {Application.class})
public class SubscriberTest extends AbstractTestNGSpringContextTests {

  @Autowired
  private SubscriberSteps subscriberSteps;

  @Autowired
  private WireMockSteps wireMockSteps;

  @BeforeClass
  public void cleanQueue(){
    subscriberSteps.cleanQueues();
  }

  private User user;
  private FindRequests findRequests;

  @BeforeMethod
  public void generationData() {
    String correlationId = UUID.randomUUID().toString();
    user = User.builder()
        .name("AQA")
        .correlationId(correlationId)
        .build();
    findRequests = WireMockRequest.findByCorrelationId(correlationId);
  }

  @Test
  public void subscriberTest() {
    subscriberSteps.sendSuccessfullyMessageToMS(user);
    User acUser = wireMockSteps.fineRequestByCorrelationId(findRequests);
    Assert.assertEquals(user, acUser);
  }

}
