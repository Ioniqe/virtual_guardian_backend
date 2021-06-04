package app.virtual_guardian.service;

import app.virtual_guardian.entity.Day;
import app.virtual_guardian.repository.DayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DayService {
    private final DayRepository dayRepository;

    @Autowired
    public DayService(DayRepository dayRepository) {
        this.dayRepository = dayRepository;
    }

    public void deleteAll(){
        dayRepository.deleteAll();
    }

    public void saveDay(Day day){
        dayRepository.save(day);
    }
}
