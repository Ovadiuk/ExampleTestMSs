package com.dto.wiremock;

import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Request {
  public String url;
  public String absoluteUrl;
  public String method;
  public String clientIp;
  public Headers headers;
  public Cookies cookies;
  public boolean browserProxyRequest;
  public long loggedDate;
  public String bodyAsBase64;
  public String body;
  public String scheme;
  public String host;
  public int port;
  public Date loggedDateString;
  public QueryParams queryParams;
}
