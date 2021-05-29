package app.virtual_guardian.controller;

import app.virtual_guardian.dto.AnomalyDTO;
import app.virtual_guardian.dto.builder.AnomalyBuilder;
import app.virtual_guardian.entity.Anomaly;
import app.virtual_guardian.entity.Emergency;
import app.virtual_guardian.service.AnomalyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class AnomalyController {
    private final AnomalyService anomalyService;

    @Autowired
    public AnomalyController(AnomalyService anomalyService) {
        this.anomalyService = anomalyService;
    }

    //----------------SAVE ANOMALY-------------
    @RequestMapping(value = "/anomaly/save", method = RequestMethod.POST)
    public ResponseEntity saveAnomaly(@RequestBody AnomalyDTO anomalyDTO) {
        anomalyService.saveAnomaly(AnomalyBuilder.toEntity(anomalyDTO));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //----------------GET ANOMALIES-------------
    @RequestMapping(value = "/anomaly/get_all", method = RequestMethod.GET)
    public ResponseEntity<List<AnomalyDTO>> getAnomalies() {
        List<AnomalyDTO> anomalyDTOS = new ArrayList<>();
        anomalyService.getAll().forEach(anomaly -> anomalyDTOS.add(AnomalyBuilder.toDTO(anomaly)));
        return new ResponseEntity<>(anomalyDTOS, HttpStatus.OK);
    }
}
