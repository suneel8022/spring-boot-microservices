package com.pm.patientservice.controller;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService){
        this.patientService = patientService;
    }


    @GetMapping
    public ResponseEntity<List<PatientResponseDTO>>getPatients(){
        List<PatientResponseDTO> patientResponseDTOS = patientService.patientResponseDTOS();
        return ResponseEntity.ok().body(patientResponseDTOS);
    }


    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(
                @Valid @RequestBody PatientRequestDTO patientRequestDTO){

        PatientResponseDTO patientResponseDTO = patientService.createPatient(patientRequestDTO);

        return ResponseEntity.ok().body(patientResponseDTO);

    }


    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(
                @PathVariable UUID id, @RequestBody PatientRequestDTO patientRequestDTO) throws PatientNotFoundException {
        PatientResponseDTO updatedPatient = patientService.updatePatient(id,patientRequestDTO);

        return ResponseEntity.ok().body(updatedPatient);
    }
}
