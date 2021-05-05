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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ProducerService {

    String exchange = "monitored_data-exchange";
    private String routingkey = "monitored_data-key";

    List<MonitoredActivityDTO> contents;
    private int line;

    @Autowired
    private AmqpTemplate rabbitTempl;

    private static List<MonitoredActivityDTO> getMonitoredData() throws IOException {
        Stream<String> activities = Files.lines(Paths.get("src/main/java/monitoreddata/project/activities.txt"));
        List<MonitoredActivityDTO> contents = activities
                .map(e -> e.split(" "))
                .map(array -> new MonitoredActivityDTO(Date.valueOf(array[0]), Time.valueOf(array[1]), Time.valueOf(array[2]), array[3]))
                .collect(Collectors.toList());
        activities.close();
        return contents;
    }

    public ProducerService() throws IOException {
        this.contents = getMonitoredData();
        this.line = 0;
    }

    @Scheduled(fixedDelay = 3000)
    public void send() {
        ObjectMapper mapper = new ObjectMapper();
        if (line < contents.size()) {
            try {
                String toBeSent = mapper.writeValueAsString(contents.get(line));
                rabbitTempl.convertAndSend(exchange, routingkey, toBeSent);
                System.out.println(toBeSent);
                line++;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }
}
