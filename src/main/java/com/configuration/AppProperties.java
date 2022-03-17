package com.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties
@Setter
@Getter
public class AppProperties {

  private Integer httpTimeout;
  private long queueTimeout;
  private PublisherMicroservice publisherMicroservice = new PublisherMicroservice();
  private SubscriberMicroservice subscriberMicroservice = new SubscriberMicroservice();
  private wireMock wireMock = new wireMock();

  @Setter
  @Getter
  public static class PublisherMicroservice {
    private String incomingExchange;
    private String incomingRoutingKey;
    private String outgoingQueue;
    private String outgoingRoutingKey;
    private String uri;
  }

  @Setter
  @Getter
  public static class SubscriberMicroservice{
    private String incomingExchange;
    private String incomingRoutingKey;
    private String outgoingQueue;
    private String outgoingRoutingKey;
  }

  @Setter
  @Getter
  public static class wireMock{
    private String uri;
  }

}
