package com.projects.address.domains;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document
public class UserMessage {
  @Id private String id;
  private Map<String, String> attributes;
}
