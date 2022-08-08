package com.projects.address.gateways.amqp.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.address.domains.UserMessage;
import com.projects.address.services.UserMessageService;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class UserMessageListener {

  public static final String USER_QUEUE = "ha.user.input";

  @Autowired private final RabbitTemplate rabbitTemplate;

  private final ObjectMapper objectMapper;

  private final UserMessageService userMessageService;

  @RabbitListener(queues = USER_QUEUE)
  private void userMessageListener(final Message message) {
    final String payload = new String(message.getBody(), StandardCharsets.UTF_8);

    log.info("UserMessage arrived! Payload: {}",  payload);

    userMessageService.save( deserialize(payload));

  }

  private UserMessage deserialize(final String payload) {
    UserMessage userMessage = null;

    try {
      userMessage = objectMapper.readValue(payload, UserMessage.class);

    } catch (IOException e) {
      throw new AmqpRejectAndDontRequeueException(e.getMessage(), e);
    }
    return userMessage;
  }
}
