package com.pranav_sailor.user_management_system.service;

import com.pranav_sailor.user_management_system.dto.AppointmentDTO;
import com.pranav_sailor.user_management_system.entity.Appointment;
import com.pranav_sailor.user_management_system.entity.User;
import com.pranav_sailor.user_management_system.repository.AppointmentRepository;
import com.pranav_sailor.user_management_system.repository.UserRepository;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    //Create a new appointment
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO){
        User user = userRepository.findById(appointmentDTO.getId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"User id not found " + appointmentDTO.getUserId()));

        Appointment appointment = Appointment.builder()
                .user(user)
                .appointmentDateTime(appointmentDTO.getAppointmentDateTime())
                .description(appointmentDTO.getDescription())
                .build();

        Appointment savedAppointment = appointmentRepository.save(appointment);
        return mapToDTO(savedAppointment);

    }

    //Get All appointments for a user
    public List<AppointmentDTO> getAppointmentsByUserId(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User id not found " + userId));

        List<Appointment> appointments = appointmentRepository.findByUser(user);
            return appointments.stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());

    }

    //Update an existing appointment
    public AppointmentDTO updateAppointment(Long appointmentId, AppointmentDTO appointmentDTO){
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found " + appointmentId));

        Appointment updateAppointment = appointmentRepository.save(appointment);
        return mapToDTO(updateAppointment);

    }

    //Delete an appointment

    public void deleteAppointment(Long appointmentId){
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment Not found " + appointmentId));
    }


    //Helper method to map Appointment to Appointment DTO
    private AppointmentDTO mapToDTO(Appointment appointment){
        return AppointmentDTO.builder()
                .id(appointment.getId())
                .userId(appointment.getId())
                .appointmentDateTime(appointment.getAppointmentDateTime())
                .description(appointment.getDescription())
                .build();

    }
}
