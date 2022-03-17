package com.dto.wiremock;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BodyPattern {
  public String contains;
}
