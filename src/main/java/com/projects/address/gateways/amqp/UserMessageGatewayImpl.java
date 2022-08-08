package com.projects.address.gateways.amqp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserMessageGatewayImpl implements UserMessageGateway {

  private final RabbitTemplate userRabbitTemplate;

  @Override
  public void sendUserMessage(String message) {
    log.info("Sending message to rabbit {}", message);
    userRabbitTemplate.convertAndSend("ha.user", "ha.user.rk", message);
  }
}
