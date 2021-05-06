package app.virtual_guardian.controller;

import app.virtual_guardian.dto.ActivityDTO;
import app.virtual_guardian.dto.builder.ActivityBuilder;
import app.virtual_guardian.entity.Activity;
import app.virtual_guardian.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ActivityController {

    private final ActivityService activityService;

    @Autowired
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    //TODO

//    //----------------------------------READ----------------------------------
//    @RequestMapping(value = "/activities/{userId}/{date}", method = RequestMethod.GET)
//    public ResponseEntity<List<ActivityDTO>> readActivitiesForTheDay(@PathVariable("userId") String userId, @PathVariable("date") String date) { //TODO verify
//        List<Activity> activitiesForTheDay =  activityService.getActivitiesForTheDay(userId, date);
//        List<ActivityDTO> activitiesForTheDayDTO = ActivityBuilder.toActivityDTOsList(activitiesForTheDay);
//        return new ResponseEntity<>(activitiesForTheDayDTO, HttpStatus.OK);
//    }
//
//    //----------------------------------ADD----------------------------------
//    @RequestMapping(value = "/activity/add/{userId}/{date}", method = RequestMethod.POST)
//    public ResponseEntity addActivity(@PathVariable("userId") String userId, @PathVariable("date") String date) {
//        List<Activity> activitiesForTheDay =  activityService.getActivitiesForTheDay(userId, date);
//        List<ActivityDTO> activitiesForTheDayDTO = ActivityBuilder.toActivityDTOsList(activitiesForTheDay);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
