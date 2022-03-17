package com.step;

import com.dto.publisher.User;
import com.dto.wiremock.FindRequests;
import com.dto.wiremock.Requests;
import com.google.gson.Gson;
import com.services.WireMock;
import io.qameta.allure.Step;
import lombok.SneakyThrows;
import org.testng.Assert;
import retrofit2.Response;

public class WireMockSteps {
  private final WireMock wireMock;
  private final Gson gson;

  public WireMockSteps(WireMock wireMock, Gson gson){
    this.wireMock = wireMock;
    this.gson = gson;
  }

  @SneakyThrows
  @Step("User search request in mock")
  public User fineRequestByCorrelationId(FindRequests findRequests){
    Response<Requests> requests = wireMock.findRequest(findRequests).execute();
    Assert.assertTrue(requests.isSuccessful());
    Assert.assertNotNull(requests.body());
    Assert.assertEquals(requests.body().requests.size(), 1);
    return gson.fromJson(requests.body().requests.get(0).body, User.class);
  }
}
