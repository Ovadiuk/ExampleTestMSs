package com.dto.publisher;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
  private final String name;
  private final String correlationId;
}
