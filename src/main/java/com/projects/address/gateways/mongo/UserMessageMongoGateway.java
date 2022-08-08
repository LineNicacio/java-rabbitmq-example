package com.projects.address.gateways.mongo;

import com.projects.address.domains.UserMessage;
import com.projects.address.gateways.mongo.repository.UserMessageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMessageMongoGateway implements UserMessageGateway {

  private final UserMessageRepository userMessageRepository;

  @Override
  public UserMessage save(UserMessage userMessage) {
    return userMessageRepository.save(userMessage);
  }

  @Override
  public void delete(UserMessage userMessage) {
    userMessageRepository.delete(userMessage);
  }

  @Override
  public List<UserMessage> findAll() {
    return userMessageRepository.findAll();
  }

}
