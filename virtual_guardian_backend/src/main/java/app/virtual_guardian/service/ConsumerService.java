package app.virtual_guardian.service;

import app.virtual_guardian.dto.MonitoredActivityDTO;
import app.virtual_guardian.dto.Prediction;
import app.virtual_guardian.utils.UsefulMethods;
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

    String ML_ALGORITHM = "logisticRegression";
    String ML_FEATURES = "durationFrequencyRatio";

    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public ConsumerService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @RabbitListener(queues = "monitored_data-queue")
    public void receive(String in) {
        final String uri = "http://localhost:5000/predict/day";

        List<MonitoredActivityDTO> monitoredActivityDTOList = null;
        double[][] arr = null;
        try {
            monitoredActivityDTOList = new ObjectMapper().readValue(in, new TypeReference<List<MonitoredActivityDTO>>() {
            });
            arr = new double[][]{UsefulMethods.toDurationFrequencyRatio(monitoredActivityDTOList)};
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        RestTemplate restTemplate = new RestTemplate();
        JSONObject item = new JSONObject();
        item.put("algorithm", ML_ALGORITHM);
        item.put("features", ML_FEATURES);
        item.put("arr", arr);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(item.toString(), headers);
        String result = restTemplate.postForObject(uri, request, String.class);  //getBody()

        assert result != null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            Prediction prediction = mapper.readValue(result, Prediction.class);
//            System.out.println(prediction.getPrediction());
            if (prediction.getPrediction().equals("anomalous")) {
                String toBeSent = "{\"day\"" + ":" + "\"" + monitoredActivityDTOList.get(0).getDay().toString() + "\"" + "," + "\"message\"" + ":" + "\"" + prediction.getPrediction() + "\"" + "}";
                simpMessagingTemplate.convertAndSend("/topic/app", toBeSent);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }

}
