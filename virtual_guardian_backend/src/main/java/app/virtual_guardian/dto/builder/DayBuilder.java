package app.virtual_guardian.dto.builder;

import app.virtual_guardian.dto.DayDTO;
import app.virtual_guardian.entity.Activity;
import app.virtual_guardian.entity.Day;

import java.util.ArrayList;
import java.util.List;

public class DayBuilder {
    public static Day toEntity(DayDTO dayDTO){
        return new Day(dayDTO.getDay(), dayDTO.getResult());
    }

    public static DayDTO toDTO(Day day){
        List<Activity> activities = new ArrayList<>( day.getListOfActivities());
        return new DayDTO(day.getId(), day.getDay(), day.getResult(),MonitoredActivityBuilder.toDTOList(activities));
    }
}
