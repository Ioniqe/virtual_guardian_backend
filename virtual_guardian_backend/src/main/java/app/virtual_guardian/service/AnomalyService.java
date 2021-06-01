package app.virtual_guardian.service;

import app.virtual_guardian.entity.Anomaly;
import app.virtual_guardian.repository.AnomalyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnomalyService {
    private final AnomalyRepository anomalyRepository;

    @Autowired
    public AnomalyService(AnomalyRepository anomalyRepository) {
        this.anomalyRepository = anomalyRepository;
    }

    public void saveAnomaly(Anomaly anomaly){
        anomalyRepository.save(anomaly);
    }

    public List<Anomaly> getAll(){
        return anomalyRepository.findAll();
    }

    public void deleteAll(){
        anomalyRepository.deleteAll();
    }
}
