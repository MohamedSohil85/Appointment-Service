package com.appointment.appointmentservice.dto;

import com.appointment.appointmentservice.enurmation.AppointmentType;
import com.appointment.appointmentservice.enurmation.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AppointmentDTO {

    @NotEmpty
    private String lastName;
    @NotEmpty
    private String firstName;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;
    @NotEmpty
    private String phoneNumber;
    @NotEmpty
    private Status status;
    @Future
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate appointmentDate;
    @JsonFormat(pattern = "KK:mm a")
    private LocalTime appointmentTime;
    private AppointmentType reason;
}
