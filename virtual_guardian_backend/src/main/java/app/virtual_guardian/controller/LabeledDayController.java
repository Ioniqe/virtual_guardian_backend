package app.virtual_guardian.controller;

import app.virtual_guardian.dto.LabeledDayDTO;
import app.virtual_guardian.dto.builder.LabeledDayBuilder;
import app.virtual_guardian.entity.LabeledDay;
import app.virtual_guardian.service.LabeledDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class LabeledDayController {
    private final LabeledDayService labeledDayService;

    @Autowired
    public LabeledDayController(LabeledDayService labeledDayService) {
        this.labeledDayService = labeledDayService;
    }

    //-------------------------READ ALL ANOMALOUS DAYS--------------------------------
    @RequestMapping(value = "/labeled_days/{label}", method = RequestMethod.GET)
    public ResponseEntity readAllAnomalousLabeledDays(@PathVariable("label") String label) {
        List<LabeledDay> labeledDays = labeledDayService.getLabeledDays(label);
        List<LabeledDayDTO> labeledDayDTOs = new ArrayList<>();
        labeledDays.forEach(labeledDay -> labeledDayDTOs.add(LabeledDayBuilder.toLabeledDayDTO(labeledDay)));
        return new ResponseEntity(labeledDayDTOs, HttpStatus.OK);
    }

    //-------------------------SET SELECTED DAYS--------------------------------
    @RequestMapping(value = "/labeled_days/set_selected/anomalous", method = RequestMethod.PUT)
    public ResponseEntity setSelectedAsAnomalousLabeledDays(@RequestBody List<Date> labeledDays) {
        List<LabeledDay> allLabeledDays = labeledDayService.getAll();

        allLabeledDays.forEach(labeledDay -> labeledDay.setLabel("normal"));

        labeledDays.forEach(lD ->
            allLabeledDays.forEach(labeledDay -> {
                if(labeledDay.getDay().toLocalDate().compareTo(lD.toLocalDate()) == 0){
                    labeledDay.setLabel("anomalous");
                }
            })
        );

        labeledDayService.saveAll(allLabeledDays);
        return new ResponseEntity(HttpStatus.OK);
    }

}
