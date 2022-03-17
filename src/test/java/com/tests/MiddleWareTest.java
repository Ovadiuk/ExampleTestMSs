package com.tests;

import com.Application;
import com.dto.publisher.User;
import com.step.MiddleWareStep;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@SpringBootTest(classes = {Application.class})
public class MiddleWareTest extends AbstractTestNGSpringContextTests {

  @Autowired
  MiddleWareStep middleWareStep;
  private User user;

  @BeforeClass
  public void cleanQueue(){
    middleWareStep.cleanQueues();
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
  public void middleWareTest() {
    middleWareStep.sendSuccessfullyMessageToMS(user);
    User acUser = middleWareStep.readMessageFromQueue();
    Assert.assertEquals(user, acUser);
  }

}
