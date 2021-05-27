package app.virtual_guardian.service;

import app.virtual_guardian.dto.MonitoredActivityDTO;
import app.virtual_guardian.dto.Prediction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
    public void receive(String in) {
        final String uri = "http://localhost:5000/predict/day";

        List<MonitoredActivityDTO> monitoredActivityDTOList = null;
        try {
            monitoredActivityDTOList = new ObjectMapper().readValue(in, new TypeReference<List<MonitoredActivityDTO>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        RestTemplate restTemplate = new RestTemplate();
        JSONObject item = new JSONObject(); //create object to send
        item.put("arr", monitoredActivityDTOList);

        HttpHeaders headers = new HttpHeaders(); //set Headers
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(item.toString(), headers); //create request
        String result = restTemplate.postForObject(uri, request, String.class); //get result

        assert result != null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            Prediction prediction = mapper.readValue(result, Prediction.class);
            if (prediction.getPrediction().equals("anomalous")) {
                String toBeSent = "{\"day\"" + ":" + "\"" + monitoredActivityDTOList.get(0).getDay().toString() + "\"" + "," + "\"message\"" + ":" + "\"" + prediction.getPrediction() + "\"" + "}";
                simpMessagingTemplate.convertAndSend("/topic/app", toBeSent);
            }
            item.put("prediction", prediction.getPrediction());
            simpMessagingTemplate.convertAndSend("/topic/patient_activities", item.toString());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }

}
