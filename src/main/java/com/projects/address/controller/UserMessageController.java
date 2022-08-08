package com.projects.address.controller;

import com.projects.address.domains.UserMessage;
import com.projects.address.services.UserMessageSendRequest;
import com.projects.address.services.UserMessageService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v1/message")
@RestController
public class UserMessageController {

  private final UserMessageService messageService;
  private final UserMessageSendRequest userMessageSendRequest;

  @PostMapping(value = "/new-user-message")
  public ResponseEntity saveUser(@RequestBody UserMessage userMessage) {
    userMessageSendRequest.sendUserMessage(userMessage);
    return new ResponseEntity(HttpStatus.OK);
  }

  @GetMapping(value = "/list-user-messages")
  public ResponseEntity listUser() {
    List<UserMessage> userMessages = messageService.listAllMessages();
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(userMessages);
  }

  @DeleteMapping(value = "/delete-user-message")
  public ResponseEntity deleteUser(@RequestBody UserMessage userMessage) {
    messageService.delete(userMessage);
    return new ResponseEntity((HttpStatus.OK));
  }
}
