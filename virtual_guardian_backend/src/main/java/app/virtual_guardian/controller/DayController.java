package app.virtual_guardian.controller;

import app.virtual_guardian.dto.DayDTO;
import app.virtual_guardian.dto.builder.DayBuilder;
import app.virtual_guardian.entity.Day;
import app.virtual_guardian.service.DayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class DayController {

    private final DayService dayService;

    @Autowired
    public DayController(DayService dayService) {
        this.dayService = dayService;
    }

    //----------------------------------DELETE ALL DAYS----------------------------------
    @RequestMapping(value = "/day/delete_all", method = RequestMethod.DELETE)
    public ResponseEntity deleteAllDays() {
        dayService.deleteAll();
        return new ResponseEntity(HttpStatus.OK);
    }

    //----------------------------------GET ALL DAYS----------------------------------
    @RequestMapping(value = "/day/get_all", method = RequestMethod.GET)
    public ResponseEntity<List<DayDTO>> getAllDays() {
        List<Day> savedDays = dayService.getAll();
        List<DayDTO> dayDTOList = new ArrayList<>();
        savedDays.forEach(day -> dayDTOList.add(DayBuilder.toDTO(day)));
        return new ResponseEntity<>(dayDTOList, HttpStatus.OK);
    }

    //----------------------------------GET ALL ANOMALOUS DAYS----------------------------------
    @RequestMapping(value = "/day/get_all/anomalous", method = RequestMethod.GET)
    public ResponseEntity<List<DayDTO>> getAllAnomalousDays() {
        List<Day> savedAnomalousDays = dayService.getAllAnomalous();
        List<DayDTO> anomalousDayDTOList = new ArrayList<>();
        savedAnomalousDays.forEach(day -> anomalousDayDTOList.add(DayBuilder.toDTO(day)));
        return new ResponseEntity<>(anomalousDayDTOList, HttpStatus.OK);
    }

}
