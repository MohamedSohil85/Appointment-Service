package com.appointment.appointmentservice.dto;

import com.appointment.appointmentservice.enurmation.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
@ToString
@NoArgsConstructor
public class PatientDTO {

    @Size(min = 3,max = 10,message = "first name is invalid")
    @NotBlank(message = "Please enter first name")
    @Pattern(regexp = "(\"^.*[A-Z].*[a-z].*$\")",message = "first Letter must be Capital")
    private String firstName;
    @Size(min = 3,max = 10,message = "last name is invalid")
    @NotBlank(message = "Please enter last name")
    @Pattern(regexp = "(\"^.*[A-Z].*[a-z].*$\")",message = "first Letter must be Capital")
    private String lastName;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Past
    private LocalDate dateOfBirth;
    private Gender gender;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm a")
    private LocalDateTime dateOfRegistration;
    private String nationality;
    @NotEmpty
    private String phoneNumber;
    @Email(message = "Please enter valid email", regexp="^[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\\.[a-zA-Z.]{2,5}")
    @NotNull(message = "Please enter email")
    private String email;
    @NotEmpty
    private String city;
    @NotEmpty
    private String state;
    @NotEmpty
    private String street;
    @NotEmpty
    private String country;
    @NotNull
    @Positive
    private int zipcode;
}
