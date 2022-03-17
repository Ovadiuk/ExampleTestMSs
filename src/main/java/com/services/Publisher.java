package com.services;

import com.dto.publisher.PublisherResponse;
import com.dto.publisher.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Publisher {
  @POST("item")
  Call<PublisherResponse> sendMessage(@Body User user);
}
