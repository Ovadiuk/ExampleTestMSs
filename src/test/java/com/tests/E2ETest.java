package com.tests;

import com.Application;
import com.dto.publisher.User;
import com.dto.wiremock.FindRequests;
import com.preparerequest.WireMockRequest;
import com.step.PublisherSteps;
import com.step.WireMockSteps;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@SpringBootTest(classes = {Application.class})
public class E2ETest extends AbstractTestNGSpringContextTests {

  @Autowired
  private PublisherSteps publisherSteps;

  @Autowired
  private WireMockSteps wireMockSteps;

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
  public void e2eTest() {
    publisherSteps.sendSuccessfullyMessageToMS(user);
    User acUser = wireMockSteps.fineRequestByCorrelationId(findRequests);
    Assert.assertEquals(user, acUser);
  }
}
