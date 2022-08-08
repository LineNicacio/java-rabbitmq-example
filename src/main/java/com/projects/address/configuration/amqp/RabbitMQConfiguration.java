package com.projects.address.configuration.amqp;

import com.projects.address.configuration.amqp.RabbitProperties.RabbitConnection;
import com.projects.address.configuration.amqp.RabbitProperties.RabbitName;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(RabbitProperties.class)
public class RabbitMQConfiguration {

  private final RabbitProperties rabbitProperties;

  @Bean
  public RabbitTemplate rabbitTemplate() {
    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory(RabbitName.USER));
    rabbitTemplate.setMessageConverter(new SimpleMessageConverter());
    return rabbitTemplate;
  }

  @Bean
  public RabbitAdmin rabbitAdmin() {
    return new RabbitAdmin(connectionFactory(RabbitName.USER));
  }

  private ConnectionFactory connectionFactory(final RabbitName rabbitName) {
    final RabbitConnection connection = rabbitProperties.getConnection(rabbitName);
    final CachingConnectionFactory factory = new CachingConnectionFactory();
    factory.setAddresses(connection.getAddresses());
    factory.setUsername(connection.getCredentials().getUsername());
    factory.setPassword(connection.getCredentials().getPassword());
    factory.setVirtualHost(connection.getCredentials().getVirtualHost());
    return factory;
  }

  @Bean
  public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
    return getFactory();
  }

  private SimpleRabbitListenerContainerFactory getFactory() {
    SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
    factory.setConnectionFactory(connectionFactory(RabbitName.USER));
    return factory;
  }

  @Bean
  public void manageQueues() {
    RabbitAdmin rabbitAdmin = rabbitAdmin();
    rabbitProperties
        .getBindings()
        .forEach(
            binding -> {
              final Queue queue = QueueBuilder.durable(binding.getQueue()).build();
              final Exchange exchange =
                  ExchangeBuilder.topicExchange(binding.getExchange()).durable(true).build();
              final Binding rabbitBinding =
                  BindingBuilder.bind(queue)
                      .to(exchange)
                      .with(binding.getRoutingKey())
                      .and(new HashMap<>());

              rabbitAdmin.declareQueue(queue);
              rabbitAdmin.declareExchange(exchange);
              rabbitAdmin.declareBinding(rabbitBinding);
            });
  }
}
