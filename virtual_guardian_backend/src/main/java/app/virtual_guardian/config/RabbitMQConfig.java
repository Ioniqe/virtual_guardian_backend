package app.virtual_guardian.config;

import app.virtual_guardian.dto.MonitoredActivityDTO;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class RabbitMQConfig {

    String queueName = "monitored_data-queue";
    String exchange = "monitored_data-exchange";
    private String routingkey = "monitored_data-key";

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingkey);
    }

    @Bean
    public MessageConverter converter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setJavaTypeMapper(new DefaultJackson2JavaTypeMapper() {

            @Override
            public JavaType toJavaType(MessageProperties properties) {
                JavaType javaType = super.toJavaType(properties);
                if (javaType instanceof CollectionLikeType) {
                    return TypeFactory.defaultInstance()
                            .constructCollectionLikeType(List.class, MonitoredActivityDTO.class);
                }
                else {
                    return javaType;
                }
            }

        });
        return converter;
    }

    @Bean
    ConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setUri("amqps://zaypciux:RI-lVc2zhZVv9aU0eBqj6w8joJ5XJLYo@clam.rmq.cloudamqp.com/zaypciux");
        return connectionFactory;
    }

    @Bean
    public AmqpTemplate rabbitTempl(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

}