package app.virtual_guardian.controller;

import app.virtual_guardian.dto.SpecialCredentialsDTO;
import app.virtual_guardian.dto.builder.SpecialCredentialsBuilder;
import app.virtual_guardian.entity.SpecialCredentials;
import app.virtual_guardian.service.SpecialCredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class SpecialCredentialsController {

    private final SpecialCredentialsService specialCredentialsService;

    @Autowired
    public SpecialCredentialsController(SpecialCredentialsService specialCredentialsService) {
        this.specialCredentialsService = specialCredentialsService;
    }

    //---------------------------------CREATE---------------------------------
    @RequestMapping(value = "/specialCredentials/new", method = RequestMethod.POST)
    public ResponseEntity createSpecialCredential(@RequestBody SpecialCredentialsDTO specialCredentialsDTO) {
        SpecialCredentials specialCredentials = SpecialCredentialsBuilder.toEntity(specialCredentialsDTO);
        specialCredentialsService.insertDoctor(specialCredentials);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //---------------------------------READ---------------------------------
    @RequestMapping(value = "/specialCredentials/all", method = RequestMethod.GET)
    public ResponseEntity<List<SpecialCredentials>> geSpecialCredentialsList() {
        List<SpecialCredentials> specialCredentialsList = specialCredentialsService.getAllDoctorIds();
        return new ResponseEntity<>(specialCredentialsList, HttpStatus.OK);
    }
}
