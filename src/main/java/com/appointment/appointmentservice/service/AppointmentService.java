package com.appointment.appointmentservice.service;

import com.appointment.appointmentservice.Mapper.AppointmentMapper;
import com.appointment.appointmentservice.dto.AppointmentDTO;
import com.appointment.appointmentservice.dto.PatientDTO;
import com.appointment.appointmentservice.entity.Appointment;
import com.appointment.appointmentservice.entity.Patient;
import com.appointment.appointmentservice.exceptions.ResourceNotFound;
import com.appointment.appointmentservice.persistence.AppointmentRepository;
import com.appointment.appointmentservice.persistence.PatientRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    private final AppointmentMapper appointmentMapper =new AppointmentMapper();
    final AppointmentRepository appointmentRepository;
    final PatientRepository patientRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
    }

    public AppointmentDTO saveNewAppointment(AppointmentDTO appointmentDTO){
        String lastName= appointmentDTO.getLastName();
        String firstName=appointmentDTO.getFirstName();
        Optional<Patient> optionalPatient=patientRepository.findByLastNameAndFirstName(lastName,firstName);
        if (!optionalPatient.isPresent()){
            new ResourceNotFound("Patient cannot found");
        }
        Patient patient=optionalPatient.get();
        LocalDate appointmentDate=appointmentDTO.getAppointmentDate();
        LocalTime appointmentTime=appointmentDTO.getAppointmentTime();
        Appointment appointment_=appointmentRepository.findAppointmentByAppointmentDateAndAppointmentTime(appointmentDate,appointmentTime);
        if (appointment_.getId() !=null){
            new ResponseEntity<>(HttpStatus.FOUND);
        }
        Optional<Appointment> app=appointmentRepository.findAppointmentByAppointmentDateAndPatient(appointmentDate,patient);
        if (app.isPresent()){
            new ResponseEntity<>("you have Appointment today",HttpStatus.FOUND);
        }
        Appointment appointment=appointmentMapper.toEntity(appointmentDTO);
        appointment.setPatient(patient);
        patient.setAppointments(Arrays.asList(appointment));
        return appointmentDTO;

    }
    public String findAppointmentByDateAndTime(LocalDate date,LocalTime time){
        Appointment appointment=appointmentRepository.findAppointmentByAppointmentDateAndAppointmentTime(date, time);
        if(appointment.getId()==null){
            return "Appointment not reserved !";
        }
        return "Appoint reserved for :"+appointment.getLastName() +"\t"+appointment.getLastName();
    }

    public List<AppointmentDTO>showAllAppointmentsByDate(LocalDate date,int page,int size){
        Pageable pageable= PageRequest.of(page, size);
        List<Appointment> appointments=appointmentRepository.findAppointmentsByAppointmentDate(date,pageable);
        return appointmentMapper.toDto(appointments);
    }
    public AppointmentDTO findAppointmentByPatient(String lastName,String firstName,LocalDate date)throws ResourceNotFound{

        Optional<Patient> patient=patientRepository.findByLastNameAndFirstName(lastName, firstName);

        if (!patient.isPresent()){
            throw new ResourceNotFound("Patient could not find!");
        }
        Patient patient_=patient.get();
        Optional<Appointment> appointment=appointmentRepository.findAppointmentByAppointmentDateAndPatient(date,patient_);
        return appointmentMapper.toDto(appointment.get());
    }

    public void cancelAppointment(String lastName,String firstName,LocalDate date)throws ResourceNotFound{
        Optional<Patient>optionalPatient=patientRepository.findByLastNameAndFirstName(lastName, firstName);
        Patient patient=optionalPatient.orElseThrow(()->new ResourceNotFound("Patient could not found"));
        appointmentRepository.deleteAppointmentByPatientAndAppointmentDate(patient,date);

    }
    public AppointmentDTO changeAppointmentById(Long id,AppointmentDTO appointmentDTO)throws ResourceNotFound{
        Optional<Appointment>optionalAppointment=appointmentRepository.findById(id);
        Appointment appointment_=optionalAppointment.map(appointment ->{
            appointment.setAppointmentDate(appointmentDTO.getAppointmentDate());
            appointment.setAppointmentTime(appointmentDTO.getAppointmentTime());
            appointment.setReason(appointment.getReason());
            return appointmentRepository.save(appointment);
        } ).orElseThrow(()->new ResourceNotFound("Appointment could not found"));
        return appointmentMapper.toDto(appointment_);
    }
}
