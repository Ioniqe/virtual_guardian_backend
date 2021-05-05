package app.virtual_guardian.dto.builder;

import app.virtual_guardian.dto.ActivityDTO;
import app.virtual_guardian.entity.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityBuilder {
    public static ActivityDTO toActivityDTO(Activity activity){
        return new ActivityDTO(activity.getId(), activity.getName(), activity.getStartTime(),
                activity.getEndTime(), activity.getDay(), activity.getPatient().getUserId());
    }

    public static List<ActivityDTO> toActivityDTOsList(List<Activity> activitiesForTheDay){
        List<ActivityDTO> activitiesForTheDayDTO = new ArrayList<>();
        activitiesForTheDay.forEach(activity -> activitiesForTheDayDTO.add(toActivityDTO(activity)));
        return activitiesForTheDayDTO;

    }
}
