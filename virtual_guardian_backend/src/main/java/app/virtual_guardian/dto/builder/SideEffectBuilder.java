package app.virtual_guardian.dto.builder;

import app.virtual_guardian.dto.SideEffectsDTO;
import app.virtual_guardian.entity.SideEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SideEffectBuilder {
    public static SideEffectsDTO toSideEffectDTO(SideEffect sideEffect){
        return new SideEffectsDTO(sideEffect.getId(), sideEffect.getName());
    }

    public static List<SideEffectsDTO> toSideEffectsDTOListFromSideEffectsSet(Set<SideEffect> sideEffects){
        List<SideEffectsDTO> sideEffectsDTOS = new ArrayList<>();
        sideEffects.forEach(sideEffect -> sideEffectsDTOS.add(toSideEffectDTO(sideEffect)));
        return sideEffectsDTOS;
    }

    public static List<SideEffectsDTO> toSideEffectsDTOListFromSideEffectsList(List<SideEffect> sideEffects){
        List<SideEffectsDTO> sideEffectsDTOS = new ArrayList<>();
        sideEffects.forEach(sideEffect -> sideEffectsDTOS.add(toSideEffectDTO(sideEffect)));
        return sideEffectsDTOS;
    }
}
