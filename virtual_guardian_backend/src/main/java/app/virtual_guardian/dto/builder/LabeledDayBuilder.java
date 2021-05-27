package app.virtual_guardian.dto.builder;

import app.virtual_guardian.dto.LabeledDayDTO;
import app.virtual_guardian.entity.LabeledDay;

public class LabeledDayBuilder {
    public static LabeledDayDTO toLabeledDayDTO(LabeledDay labeledDay){
        return new LabeledDayDTO(labeledDay.getId(), labeledDay.getDay(), labeledDay.getLabel());
    }
}
