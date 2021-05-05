package app.virtual_guardian.service;

import app.virtual_guardian.dto.MonitoredActivityDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerService {

    @RabbitListener(queues = "monitored_data-queue")
    public void receive(String in) throws JsonProcessingException {
        MonitoredActivityDTO monitoredActivityDTO = new ObjectMapper().readValue(in, MonitoredActivityDTO.class);

        System.out.println(monitoredActivityDTO.toString());
    }

}
