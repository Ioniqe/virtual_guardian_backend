package app.virtual_guardian.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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

        final String uri = "http://localhost:5000/get_ml_variables";
        HttpHeaders headers = new HttpHeaders(); //set Headers
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getForEntity(uri, String.class);
    }

}
