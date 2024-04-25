package com.appointment.appointmentservice.resource;

import com.appointment.appointmentservice.dto.AppointmentDTO;
import com.appointment.appointmentservice.service.AppointmentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
public class AppointmentResource {

    private final AppointmentService appointmentService;

    public AppointmentResource(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping(value = "/appointment")
    public AppointmentDTO addAppointment(@RequestBody AppointmentDTO appointmentDTO){
        return appointmentService.saveNewAppointment(appointmentDTO);
    }


    @GetMapping(value = "/showAppointmentsByDate")
    public List<AppointmentDTO>loadAllAppointmentsByDate(@RequestParam("appointmentDate")@DateTimeFormat(iso =DateTimeFormat.ISO.DATE,pattern = "dd/MM/yyyy")LocalDate appointmentDate
            ,@RequestParam(value = "page",defaultValue = "0")int page
            ,@RequestParam(value = "size",defaultValue = "1")int size)
    {

        return appointmentService.showAllAppointmentsByDate(appointmentDate, page, size);
    }
    @GetMapping(value = "/showAppointmentByDateAndTime")
    public ResponseEntity findByDateAndTime(@RequestParam("appointmentDate")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE,pattern = "dd/MM/yyyy")LocalDate date
            ,@RequestParam("appointmentTime")@DateTimeFormat(iso = DateTimeFormat.ISO.TIME,pattern = "HH:mm a")LocalTime time){
        return new ResponseEntity(appointmentService.findAppointmentByDateAndTime(date, time), HttpStatus.OK);
    }
    @GetMapping(value = "/findAppointmentByPatientName")
    public ResponseEntity findByPatientName(@RequestParam("lastName")String lastName,@RequestParam("firstName")String firstName,@RequestParam("appointmentDate")@DateTimeFormat(iso = DateTimeFormat.ISO.DATE,pattern = "dd/MM/yyyy")LocalDate date){
        return new ResponseEntity(appointmentService.findAppointmentByPatient(lastName,firstName,date),HttpStatus.OK);
    }
    @PutMapping(value = "/changeAppointment/{id}")
    public AppointmentDTO changeAppointmentDateById(@PathVariable("id")Long id,@RequestBody AppointmentDTO appointmentDTO){
        return appointmentService.changeAppointmentById(id, appointmentDTO);
    }
    @DeleteMapping(value = "/daleteAppointment")
    public void cancelAppointmentByNameAndDate(@RequestParam("lastName")String lastName,@RequestParam("firstName")String firstName,@RequestParam("date")@DateTimeFormat(iso =DateTimeFormat.ISO.DATE,pattern = "dd/MM/yyyy")LocalDate date){
        appointmentService.cancelAppointment(lastName,firstName,date);
    }
    @PutMapping(value = "/changeAppointment/name/{lastName}/firstName/{firstName}/date/{date}")

    public AppointmentDTO changeAppointmentByPatientAndDate(@PathVariable("lastName")String lastName,@PathVariable("firstName")String firstName,@PathVariable("date")LocalDate date,@RequestBody AppointmentDTO appointmentDTO){
        return appointmentService.changeAppointmentByPatientName(lastName, firstName, date, appointmentDTO);
    }
}
