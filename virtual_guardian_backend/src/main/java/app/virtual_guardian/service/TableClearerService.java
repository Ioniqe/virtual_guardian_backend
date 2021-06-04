package app.virtual_guardian.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TableClearerService implements CommandLineRunner {
    //Autowire your dependency here eg the entity repository
    private final DayService dayService;

    @Autowired
    public TableClearerService(DayService dayService) {
        this.dayService = dayService;
    }

    @Override
    public void run(String... args) throws Exception {
        //place your code here
        dayService.deleteAll();
    }

}
