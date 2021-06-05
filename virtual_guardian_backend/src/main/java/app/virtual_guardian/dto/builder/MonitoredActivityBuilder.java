package app.virtual_guardian.dto.builder;

import app.virtual_guardian.dto.MonitoredActivityDTO;
import app.virtual_guardian.entity.Activity;

import java.util.ArrayList;
import java.util.List;

public class MonitoredActivityBuilder {
    public static MonitoredActivityDTO toDTO(Activity activity){
        return new MonitoredActivityDTO(activity.getDay().getDay(), activity.getStartTime(), activity.getEndTime(), activity.getName());
    }

    public static List<MonitoredActivityDTO> toDTOList(List<Activity> activities){
        List<MonitoredActivityDTO> monitoredActivityDTOList = new ArrayList<>();
        activities.forEach(activity -> monitoredActivityDTOList.add(toDTO(activity)));
        return monitoredActivityDTOList;
    }
}
