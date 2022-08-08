package com.projects.address.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.address.domains.UserMessage;
import com.projects.address.gateways.amqp.UserMessageGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserMessageSendRequest {
  private final UserMessageGateway userMessageGateway;
  private final ObjectMapper objectMapper;

  public void sendUserMessage(UserMessage userMessage) {
    userMessageGateway.sendUserMessage(deserialize(userMessage));
  }

  private String deserialize(UserMessage userMessage) {
    String payload = null;
    try {
      payload = objectMapper.writeValueAsString(userMessage);
    } catch (Exception e) {
      log.error("Erro deserializing userMessage");
    }
    return payload;
  }
}
