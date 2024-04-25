package com.appointment.appointmentservice.Mapper;

import com.appointment.appointmentservice.dto.AppointmentDTO;
import com.appointment.appointmentservice.entity.Appointment;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.ArrayList;
import java.util.List;

public class AppointmentMapper implements EntityMapper<AppointmentDTO, Appointment> {
    @Override
    public Appointment toEntity(AppointmentDTO dto) {
        ModelMapper modelMapper=new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(dto,Appointment.class);
    }

    @Override
    public AppointmentDTO toDto(Appointment entity) {
        ModelMapper modelMapper=new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(entity,AppointmentDTO.class);
    }

    @Override
    public List<Appointment> toEntity(List<AppointmentDTO> dtoList) {
        ModelMapper modelMapper=new ModelMapper();
        List<Appointment>appointments=new ArrayList<>();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        dtoList.forEach(appointmentDTO -> {
            appointments.add(modelMapper.map(appointmentDTO,Appointment.class));
        });
        return null;
    }

    @Override
    public List<AppointmentDTO> toDto(List<Appointment> entityList) {
        ModelMapper modelMapper=new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
        List<AppointmentDTO>appointmentDTOS=new ArrayList<>();
        for (Appointment appointment:entityList){
            appointmentDTOS.add(modelMapper.map(appointment,AppointmentDTO.class));
        }
        return appointmentDTOS;
    }
}
