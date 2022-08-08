package com.projects.address.services;

import com.projects.address.domains.UserMessage;
import com.projects.address.gateways.mongo.UserMessageGateway;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserMessageService {

  private final UserMessageGateway userMessageGateway;

  public void save(UserMessage userMessage) {
    log.info("Processing and saving userMessage.");
    userMessageGateway.save(userMessage);
  }

  public void delete(UserMessage user) {
    userMessageGateway.delete(user);
  }

  public List<UserMessage> listAllMessages() {
    return userMessageGateway.findAll();
  }
}
