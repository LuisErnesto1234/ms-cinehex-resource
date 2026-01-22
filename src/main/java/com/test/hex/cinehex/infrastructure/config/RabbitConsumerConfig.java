package com.test.hex.cinehex.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJavaTypeMapper;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConsumerConfig {

    public static final String QUEUE_NAME = "cinehex.users.queue";
    public static final String EXCHANGE_NAME = "cinehex.users.exchange";
    public static final String ROUTING_KEY = "users.registered";

    // 1. Declarar la Cola (true = durable, no se borra si reinicias Rabbit)
    @Bean
    public Queue userQueue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public MessageConverter messageConverter() {
        JacksonJsonMessageConverter messageConverter = new JacksonJsonMessageConverter();
        messageConverter.setTypePrecedence(JacksonJavaTypeMapper.TypePrecedence.INFERRED);

        return messageConverter;
    }

    // 2. Declarar el Exchange (Debe coincidir con el del Auth Server)
    @Bean
    public TopicExchange usersExchange() {
        return new TopicExchange(RabbitConsumerConfig.EXCHANGE_NAME);
    }

    // 3. El Binding: Conectar la Cola al Exchange usando la Llave
    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

}
