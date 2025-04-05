package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDTO> patientResponseDTOS(){
        List<Patient> patients = patientRepository.findAll();

        List<PatientResponseDTO> patientResponseDTOS =
                patients
                        .stream()
//                        .map(patient -> PatientMapper.toDTO(patient))
                        .map(PatientMapper::toDTO)
                        .toList();

        return patientResponseDTOS;
    }

}
