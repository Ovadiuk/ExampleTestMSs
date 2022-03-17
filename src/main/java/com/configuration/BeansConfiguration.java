package com.configuration;

import com.busclients.MessageBrokerClient;
import com.busclients.MessageBrokerClientRabbitMqImpl;
import com.busservices.MessageServicesMiddleWare;
import com.busservices.MessageServicesPublisher;
import com.busservices.RabbitMessageTemplate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.services.Publisher;
import com.services.WireMock;
import com.step.MiddleWareStep;
import com.step.PublisherSteps;
import com.step.SubscriberSteps;
import com.step.WireMockSteps;
import io.qameta.allure.okhttp3.AllureOkHttp3;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class BeansConfiguration {

  @Bean
  public ConnectionFactory connectionFactory(RabbitProperties rabbitProperties) {
    CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
    cachingConnectionFactory.setHost(rabbitProperties.getHost());
    cachingConnectionFactory.setVirtualHost(rabbitProperties.getVirtualHost());
    cachingConnectionFactory.setUsername(rabbitProperties.getUsername());
    cachingConnectionFactory.setPassword(rabbitProperties.getPassword());
    return cachingConnectionFactory;
  }

  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    return new RabbitTemplate(connectionFactory);
  }

  @Bean
  public RabbitMessageTemplate rabbitMessageTemplate(Gson gson, JsonParser jsonParser) {
    return new RabbitMessageTemplate(gson, jsonParser);
  }

  @Bean
  public MessageBrokerClient messageBrokerClient(RabbitTemplate rabbitTemplate, RabbitMessageTemplate rabbitMessageTemplate) {
    return new MessageBrokerClientRabbitMqImpl(rabbitTemplate, rabbitMessageTemplate);
  }

  @Bean
  public MessageServicesPublisher messageServicesPublisher(MessageBrokerClient client, AppProperties properties) {
    return new MessageServicesPublisher(client, properties);
  }

  @Bean
  public MessageServicesMiddleWare messageServicesSubscriber(MessageBrokerClient client, AppProperties properties) {
    return new MessageServicesMiddleWare(client, properties);
  }

  @Bean
  public OkHttpClient okHttpClient(AppProperties appProperties) {
    return new OkHttpClient.Builder()
        .addInterceptor(new AllureOkHttp3())
        .connectTimeout(appProperties.getHttpTimeout(), TimeUnit.SECONDS)
        .readTimeout(appProperties.getHttpTimeout(), TimeUnit.SECONDS)
        .build();
  }

  @Bean
  public Gson gson() {
    return new GsonBuilder()
        .setLenient()
        .create();
  }

  @Bean
  public JsonParser jsonParser() {
    return new JsonParser();
  }

  @Bean
  public Retrofit retrofit(OkHttpClient okHttpClient, Gson gson, AppProperties appProperties) {
    return new Retrofit.Builder()
        .baseUrl(appProperties.getWireMock().getUri())
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build();
  }

  @Bean
  public Publisher publisher(Retrofit retrofit, AppProperties appProperties) {
    return retrofit
        .newBuilder()
        .baseUrl(appProperties.getPublisherMicroservice().getUri())
        .build()
        .create(Publisher.class);
  }

  @Bean
  public WireMock wireMock(Retrofit retrofit, AppProperties appProperties) {
    return retrofit
        .newBuilder()
        .baseUrl(appProperties.getWireMock().getUri())
        .build()
        .create(WireMock.class);
  }

  @Bean
  public PublisherSteps publisherSteps(Publisher publisher,
      MessageServicesPublisher messageServicesPublisher, Gson gson) {
    return new PublisherSteps(publisher, messageServicesPublisher, gson);
  }

  @Bean
  public MiddleWareStep middleWareStep(MessageServicesPublisher messageServicesPublisher,
      MessageServicesMiddleWare messageServicesMiddleWare, Gson gson) {
    return new MiddleWareStep(messageServicesPublisher, messageServicesMiddleWare, gson);
  }

  @Bean
  public SubscriberSteps subscriberSteps (MessageServicesMiddleWare messageServicesMiddleWare){
    return new SubscriberSteps(messageServicesMiddleWare);
  }

  @Bean
  public WireMockSteps wireMockSteps(WireMock wireMock, Gson gson) {
    return new WireMockSteps(wireMock, gson);
  }
}
