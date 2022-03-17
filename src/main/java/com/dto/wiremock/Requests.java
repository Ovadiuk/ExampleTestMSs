package com.dto.wiremock;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Requests {
  public List<Request> requests;
  public boolean requestJournalDisabled;
}
