package com.services;

import com.dto.wiremock.FindRequests;
import com.dto.wiremock.Requests;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface WireMock {
  @POST("__admin/requests/find")
  Call<Requests> findRequest(@Body FindRequests findRequests);
}
