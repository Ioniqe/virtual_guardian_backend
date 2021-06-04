package app.virtual_guardian.dto.builder;

import app.virtual_guardian.dto.MonitoredActivityDTO;
import app.virtual_guardian.entity.Activity;
import app.virtual_guardian.entity.Day;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ActivityBuilder {
    public static Activity toEntity(MonitoredActivityDTO monitoredActivityDTO, Day day) {
        return new Activity(monitoredActivityDTO.getActivity(), monitoredActivityDTO.getStartTime(), monitoredActivityDTO.getEndTime(), day);
    }

    public static Set<Activity> toActivitySet(List<MonitoredActivityDTO> monitoredActivityDTOS, Day day) {
        Set<Activity> activitySet = new HashSet<>();
        monitoredActivityDTOS.forEach(monitoredActivityDTO -> activitySet.add(toEntity(monitoredActivityDTO,day)));

        return activitySet;
    }
}
