package com.busservices;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.utils.Util;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.amqp.core.Message;

public class RabbitMessageTemplate {

  private final Gson gson;
  private final JsonParser jsonParser;

  public RabbitMessageTemplate(Gson gson, JsonParser jsonParser) {
    this.gson = gson;
    this.jsonParser = jsonParser;
  }

  public String wrap(Object payload) {
    Map<String, Object> map = new HashMap<>();
    map.put("id", UUID.randomUUID().toString());
    map.put("correlationId", UUID.randomUUID().toString());
    Map<String, Object> meta = new HashMap<>();
    meta.put("createdAt", Util.getCurrentDate());
    map.put("meta", meta);
    map.put("payload", payload);
    return gson.toJson(map);
  }

  public String unwrap (Message message){
    String body = new String (message.getBody());
    return jsonParser.parse(body).getAsJsonObject().get("payload").toString();
  }

}
