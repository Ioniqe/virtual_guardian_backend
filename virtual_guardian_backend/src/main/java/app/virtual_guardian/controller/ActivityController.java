package app.virtual_guardian.controller;

import app.virtual_guardian.dto.MonitoredActivityDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@CrossOrigin(origins = "*")
public class ActivityController {

    //----------------------------------READ ACTIVITIES FROM FILE AND SEND----------------------------------
    @RequestMapping(value = "/activities/readAll", method = RequestMethod.GET)
    public ResponseEntity<List<MonitoredActivityDTO>> readActivitiesFromFile() {
        Stream<String> activities = null;
        List<MonitoredActivityDTO> activitiesLineByLine = null;
        try {
            activities = Files.lines(Paths.get("src/main/resources/activities.txt"));
            activitiesLineByLine = activities
                    .map(e -> e.split(" "))
                    .map(array -> new MonitoredActivityDTO(Date.valueOf(array[0]), Time.valueOf(array[1]), Time.valueOf(array[2]), array[3]))
                    .collect(Collectors.toList());
            activities.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(activitiesLineByLine, HttpStatus.OK);
    }
}
