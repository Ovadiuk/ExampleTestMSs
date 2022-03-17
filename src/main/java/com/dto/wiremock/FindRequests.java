package com.dto.wiremock;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindRequests {
  public String urlPattern;
  public String method;
  public List<BodyPattern> bodyPatterns;
}
