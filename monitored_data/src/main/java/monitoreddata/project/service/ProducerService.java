package monitoreddata.project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import monitoreddata.project.dto.MonitoredActivityDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProducerService {

    String exchange = "monitored_data-exchange";
    private String routingkey = "monitored_data-key";

    List<List<MonitoredActivityDTO>> contents;
    private int index;

    @Autowired
    private AmqpTemplate rabbitTempl;

    private static List<List<MonitoredActivityDTO>> getMonitoredData() throws IOException {
        Stream<String> activities = Files.lines(Paths.get("src/main/java/monitoreddata/project/activities.txt"));
        List<MonitoredActivityDTO> activitiesLineByLine = activities
                .map(e -> e.split(" "))
                .map(array -> new MonitoredActivityDTO(Date.valueOf(array[0]), Time.valueOf(array[1]), Time.valueOf(array[2]), array[3]))
                .collect(Collectors.toList());
        activities.close();


        List<List<MonitoredActivityDTO>> contents = new ArrayList<>();
        Date prevDay = activitiesLineByLine.get(0).getDay();
        int index = 0;

        List<MonitoredActivityDTO> firstDay = new ArrayList<>();
        firstDay.add(activitiesLineByLine.get(0));
        contents.add(firstDay);

        for (int i = 1; i < activitiesLineByLine.size(); i++) {
            MonitoredActivityDTO activity = activitiesLineByLine.get(i);
            if (prevDay.equals(activity.getDay())) {
                contents.get(index).add(activity);
            } else {
                prevDay = activity.getDay();
                index++;
                List<MonitoredActivityDTO> newDay = new ArrayList<>();
                newDay.add(activity);
                contents.add(newDay);
            }
        }

        return contents;
    }

    public ProducerService() throws IOException {
        this.contents = getMonitoredData();
        this.index = 0;
    }

    @Scheduled(fixedDelay = 5000)
    public void send() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        if (index < contents.size()) {
            String toBeSent = mapper.writeValueAsString(contents.get(index));
            System.out.println(toBeSent);
            rabbitTempl.convertAndSend(exchange, routingkey, toBeSent);
            index++;
        }
    }
}
