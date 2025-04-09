package com.pm.patientservice.exception;

public class PatientNotFoundException extends Exception{
    public PatientNotFoundException(String message){
        super(message);
    }
}
