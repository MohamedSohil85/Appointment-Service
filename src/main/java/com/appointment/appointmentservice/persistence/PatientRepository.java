package com.appointment.appointmentservice.persistence;

import com.appointment.appointmentservice.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {
    Optional<Patient> findByLastNameAndFirstName(String lastName, String firstName);
    Optional<Patient>findPatientByLastNameAndFirstNameAndDateOfBirth(String lastName,String firstName, @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dateOfBirth);
}
