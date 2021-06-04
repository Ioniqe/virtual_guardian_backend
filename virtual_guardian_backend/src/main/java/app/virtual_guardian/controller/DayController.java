package app.virtual_guardian.controller;

import app.virtual_guardian.service.DayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

//    //----------------------------------SAVE DAY----------------------------------
//    @RequestMapping(value = "/day/save", method = RequestMethod.POST)
//    public ResponseEntity saveDay() {
//        dayService.deleteAll();
//        return new ResponseEntity(HttpStatus.OK);
//    }
}
