package app.virtual_guardian.dto.builder;

import app.virtual_guardian.dto.DiseaseDTO;
import app.virtual_guardian.entity.Disease;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DiseaseBuilder {
    public static DiseaseDTO toDiseaseDTO(Disease disease){
        return new DiseaseDTO(disease.getId(), disease.getName());
    }

    public static List<DiseaseDTO> toDiseaseDTOListFromDiseaseSet(Set<Disease> diseases){
        List<DiseaseDTO> diseasesDTO = new ArrayList<>();
        diseases.forEach(disease -> diseasesDTO.add(toDiseaseDTO(disease)));
        return diseasesDTO;
    }

    public static List<DiseaseDTO> toDiseaseDTOListFromDiseaseList(List<Disease> diseases){
        List<DiseaseDTO> diseasesDTO = new ArrayList<>();
        diseases.forEach(disease -> diseasesDTO.add(toDiseaseDTO(disease)));
        return diseasesDTO;
    }
}
