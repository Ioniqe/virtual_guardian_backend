package app.virtual_guardian.dto.builder;

import app.virtual_guardian.dto.SpecialCredentialsDTO;
import app.virtual_guardian.entity.SpecialCredentials;

public class SpecialCredentialsBuilder {

    public static SpecialCredentials toEntity(SpecialCredentialsDTO specialCredentialsDTO){
        return new SpecialCredentials(specialCredentialsDTO.getSpecialCredential());
    }
}
