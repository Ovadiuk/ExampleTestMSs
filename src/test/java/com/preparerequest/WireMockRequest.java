package com.preparerequest;

import com.dto.wiremock.BodyPattern;
import com.dto.wiremock.FindRequests;
import java.util.ArrayList;
import java.util.List;

public class WireMockRequest {

  public static FindRequests findByCorrelationId(String correlationId){
    List<BodyPattern> bodyPatterns = new ArrayList<>();
    bodyPatterns.add(BodyPattern.builder()
        .contains(correlationId)
        .build());
    return FindRequests.builder()
        .method("POST")
        .urlPattern("/item")
        .bodyPatterns(bodyPatterns)
        .build();
  }
}
