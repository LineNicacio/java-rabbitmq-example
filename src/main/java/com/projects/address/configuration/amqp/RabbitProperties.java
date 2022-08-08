package com.projects.address.configuration.amqp;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import lombok.Data;
import org.springframework.amqp.AmqpResourceNotAvailableException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ConfigurationProperties("rabbitmq")
@Configuration
public class RabbitProperties {

  private Collection<RabbitConnection> connections;
  private List<Binding> bindings;

  public RabbitConnection getConnection(final RabbitName rabbitName) {
    return connections.stream()
        .filter(c -> rabbitName.equals(c.getName()))
        .findFirst()
        .orElseThrow(() -> new AmqpResourceNotAvailableException(rabbitName.name()));
  }

  @Data
  static class RabbitConnection {
    private RabbitName name;
    private String addresses;
    private RabbitCredentials credentials;
  }

  @Data
  static class RabbitCredentials {
    private String username;
    private String password;
    private String virtualHost;
  }

  public Optional<Binding> getQueueByName(final String queue) {
    return bindings.stream().filter(binding -> binding.getQueue().equals(queue)).findFirst();
  }

  enum RabbitName {
    USER
  }
}
