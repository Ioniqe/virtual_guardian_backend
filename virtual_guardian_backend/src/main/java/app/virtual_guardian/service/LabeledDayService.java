package app.virtual_guardian.service;

import app.virtual_guardian.entity.LabeledDay;
import app.virtual_guardian.repository.LabeledDayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabeledDayService {
    private final LabeledDayRepository labeledDayRepository;

    @Autowired
    public LabeledDayService(LabeledDayRepository labeledDayRepository) {
        this.labeledDayRepository = labeledDayRepository;
    }

    public List<LabeledDay> getLabeledDays(String label){
        return labeledDayRepository.findAllByLabel(label);
    }

    public List<LabeledDay> getAll(){
        return labeledDayRepository.findAll();
    }

    public void saveAll(List<LabeledDay> newlyLabeledDays){
        labeledDayRepository.saveAll(newlyLabeledDays);
    }
}
