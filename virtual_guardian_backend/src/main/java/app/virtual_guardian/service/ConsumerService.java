package app.virtual_guardian.service;

import app.virtual_guardian.dto.MonitoredActivityDTO;
import app.virtual_guardian.utils.UsefulMethods;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class ConsumerService {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public ConsumerService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @RabbitListener(queues = "monitored_data-queue")
    public void receive(String in) throws JsonProcessingException {
        List<MonitoredActivityDTO> monitoredActivityDTOList = new ObjectMapper().readValue(in, new TypeReference<List<MonitoredActivityDTO>>(){});

//        Arrays.toString(UsefulMethods.toDurationFrequencyRatio(monitoredActivityDTOList));

        double[][] inputML = new double[][]{UsefulMethods.toDurationFrequencyRatio(monitoredActivityDTOList)};
        final String uri = "http://localhost:5000/predict/day";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject( uri, inputML, String.class);

//        System.out.println(monitoredActivityDTOList.get(0).getDay() + " " + result);

        assert result != null;
        if(result.equals("anomalous")){
            String toBeSent = "{\"day\"" + ":" + "\"" + monitoredActivityDTOList.get(0).getDay().toString() + "\"" + "," + "\"message\"" + ":" + "\"" + result + "\"" + "}";
            System.out.println(toBeSent);
            simpMessagingTemplate.convertAndSend("/topic/app", toBeSent);
        }
    }

}
