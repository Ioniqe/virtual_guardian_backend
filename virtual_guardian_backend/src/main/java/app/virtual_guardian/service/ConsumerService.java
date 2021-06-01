package app.virtual_guardian.service;

import app.virtual_guardian.dto.AnomalyDTO;
import app.virtual_guardian.dto.MonitoredActivityDTO;
import app.virtual_guardian.dto.Prediction;
import app.virtual_guardian.dto.builder.AnomalyBuilder;
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

import java.sql.Date;
import java.util.List;

@Component
public class ConsumerService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final AnomalyService anomalyService;

    @Autowired
    public ConsumerService(SimpMessagingTemplate simpMessagingTemplate, AnomalyService anomalyService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.anomalyService = anomalyService;
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
                Date sqlCurrDay = monitoredActivityDTOList.get(0).getDay();
                String toBeSent = "{\"day\"" + ":" + "\"" + sqlCurrDay.toString() + "\"" + "," + "\"message\"" + ":" + "\"" + prediction.getPrediction() + "\"" + "}";
                simpMessagingTemplate.convertAndSend("/topic/app", toBeSent);

                //save Anomaly
                AnomalyDTO anomalyDTO = new AnomalyDTO();
//                Calendar calendar = Calendar.getInstance();
//                java.util.Date currentDate = calendar.getTime();
//                java.sql.Date currDate = new java.sql.Date(currentDate.getTime());
                anomalyDTO.setDate(sqlCurrDay);

                // TODO maybe don't save the anomalies in the db since everytime i start monitored_data
                // TODO i start from the first date from the list
//                anomalyService.saveAnomaly(AnomalyBuilder.toEntity(anomalyDTO));

                JSONObject item2 = new JSONObject();
                item2.put("day", sqlCurrDay.toString());
                item2.put("activities", monitoredActivityDTOList);
                simpMessagingTemplate.convertAndSend("/topic/anomaly_object", item2.toString());
            }

            item.put("prediction", prediction.getPrediction());
            simpMessagingTemplate.convertAndSend("/topic/patient_activities", item.toString());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }

}
