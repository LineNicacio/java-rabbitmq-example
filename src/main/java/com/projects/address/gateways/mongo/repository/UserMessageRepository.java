package com.projects.address.gateways.mongo.repository;

import com.projects.address.domains.UserMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMessageRepository extends MongoRepository<UserMessage, String> {}
