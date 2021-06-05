package app.virtual_guardian.service;

import app.virtual_guardian.dto.MonitoredActivityDTO;
import app.virtual_guardian.dto.Prediction;
import app.virtual_guardian.dto.builder.ActivityBuilder;
import app.virtual_guardian.entity.Activity;
import app.virtual_guardian.entity.Day;
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
import java.util.Set;

@Component
public class ConsumerService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final DayService dayService;

    @Autowired
    public ConsumerService(SimpMessagingTemplate simpMessagingTemplate, DayService dayService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.dayService = dayService;
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
            Date sqlCurrDay = monitoredActivityDTOList.get(0).getDay();
            if (prediction.getPrediction().equals("anomalous")) {
                String toBeSent = "{\"day\"" + ":" + "\"" + sqlCurrDay.toString() + "\"" + "," + "\"message\"" + ":" + "\"" + prediction.getPrediction() + "\"" + "}";
                simpMessagingTemplate.convertAndSend("/topic/app", toBeSent);

                JSONObject item2 = new JSONObject();
                item2.put("day", sqlCurrDay.toString());
                item2.put("activities", monitoredActivityDTOList);
                simpMessagingTemplate.convertAndSend("/topic/anomaly_object", item2.toString());
            }

            //create day with activities and save in DB
            Day day = new Day(sqlCurrDay, prediction.getPrediction());
            Set<Activity> activitySet = ActivityBuilder.toActivitySet(monitoredActivityDTOList, day);
            day.setListOfActivities(activitySet);
            day = dayService.saveDay(day);


            item.remove("arr");
            item.put("id", day.getId());
            item.put("result", prediction.getPrediction());
            item.put("day", day.getDay());
            item.put("activities", monitoredActivityDTOList);
            simpMessagingTemplate.convertAndSend("/topic/patient_activities", item.toString());

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }

}
