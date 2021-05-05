package app.virtual_guardian.service;

import app.virtual_guardian.entity.SideEffect;
import app.virtual_guardian.repository.SideEffectsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SideEffectsService {
    private final SideEffectsRepository sideEffectsRepository;

    @Autowired
    public SideEffectsService(SideEffectsRepository sideEffectsRepository) {
        this.sideEffectsRepository = sideEffectsRepository;
    }

    public List<SideEffect> getAllSideEffects(){
        return sideEffectsRepository.findAll();
    }
}
