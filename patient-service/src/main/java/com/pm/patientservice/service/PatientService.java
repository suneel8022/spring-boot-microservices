package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.exception.EmailAlreadyExistsException;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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


    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO){
//        PatientResponseDTO patientResponseDTO =
//                         PatientMapper.toDTO(patientRepository.save(PatientMapper.toModel(patientRequestDTO)));

        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())){
            throw new EmailAlreadyExistsException("A patient with this email " +
                    "already exists " + patientRequestDTO.getEmail());
        }

        Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));

        return PatientMapper.toDTO(newPatient);
    }


    public PatientResponseDTO updatePatient(UUID id,PatientRequestDTO patientRequestDTO) throws PatientNotFoundException {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + id));

        if(patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id)){
            throw new EmailAlreadyExistsException("A Patient with this email already exists" + patientRequestDTO.getEmail());
        }

        patient.setName(patientRequestDTO.getName());
        patient.setEmail(patientRequestDTO.getEmail());
        patient.setAddress(patientRequestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));

        Patient updadtedPatient = patientRepository.save(patient);

        return PatientMapper.toDTO(updadtedPatient);

    }


}
