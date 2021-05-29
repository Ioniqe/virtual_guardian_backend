package app.virtual_guardian.dto.builder;

import app.virtual_guardian.dto.AnomalyDTO;
import app.virtual_guardian.entity.Anomaly;

public class AnomalyBuilder {
    public static Anomaly toEntity(AnomalyDTO anomalyDTO){
        return new Anomaly(anomalyDTO.getDate());
    }

    public static AnomalyDTO toDTO(Anomaly anomaly){
        return new AnomalyDTO(anomaly.getId(), anomaly.getDate());
    }
}
