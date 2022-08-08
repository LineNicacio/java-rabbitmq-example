package com.projects.address.gateways.amqp;

public interface UserMessageGateway {
  void sendUserMessage(String userMessage);
}
