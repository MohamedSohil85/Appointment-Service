package com.appointment.appointmentservice.entity;

import com.appointment.appointmentservice.enurmation.AppointmentType;
import com.appointment.appointmentservice.enurmation.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Appointment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String lastName;
    private String firstName;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Future
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate appointmentDate;
    @JsonFormat(pattern = "KK:mm a")
    private LocalTime appointmentTime;
    @Enumerated(EnumType.STRING)
    private AppointmentType reason;
    @ManyToOne
    private Patient patient;
}
