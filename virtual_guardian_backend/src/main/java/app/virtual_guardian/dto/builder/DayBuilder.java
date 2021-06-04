package app.virtual_guardian.dto.builder;

import app.virtual_guardian.dto.DayDTO;
import app.virtual_guardian.entity.Day;

public class DayBuilder {
    public static Day toEntity(DayDTO dayDTO){
        return new Day(dayDTO.getDay(), dayDTO.getResult());
    }


}
