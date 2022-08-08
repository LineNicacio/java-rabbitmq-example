package com.projects.address.gateways.mongo;

import com.projects.address.domains.UserMessage;
import java.util.List;

public interface UserMessageGateway {
  UserMessage save(UserMessage userMessage);

  void delete(UserMessage userMessage);

  List<UserMessage> findAll();
}
