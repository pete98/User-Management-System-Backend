package com.pranav_sailor.user_management_system.controller;

import com.pranav_sailor.user_management_system.dto.AppointmentDTO;
import com.pranav_sailor.user_management_system.dto.UserDTO;
import com.pranav_sailor.user_management_system.service.AppointmentService;
import com.pranav_sailor.user_management_system.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private UserService userService;

    // Create a new appointment
    @PostMapping
    public ResponseEntity<AppointmentDTO> createAppointment(@Valid @RequestBody AppointmentDTO appointmentDTO,
                                                            @AuthenticationPrincipal UserDetails userDetails) {
        // Ensure the appointment is linked to the authenticated user
        UserDTO userDTO = userService.getUserByUsername(userDetails.getUsername());
        appointmentDTO.setUserId(userDTO.getId());

        AppointmentDTO createdAppointment = appointmentService.createAppointment(appointmentDTO);
        return ResponseEntity.ok(createdAppointment);
    }

    // Get all appointments for the authenticated user
    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getUserAppointments(@AuthenticationPrincipal UserDetails userDetails) {
        UserDTO userDTO = userService.getUserByUsername(userDetails.getUsername());
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByUserId(userDTO.getId());
        return ResponseEntity.ok(appointments);
    }

    // Update an existing appointment
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDTO> updateAppointment(@PathVariable Long id,
                                                            @Valid @RequestBody AppointmentDTO appointmentDTO,
                                                            @AuthenticationPrincipal UserDetails userDetails) {
        // Optionally, verify that the appointment belongs to the authenticated user
        AppointmentDTO updatedAppointment = appointmentService.updateAppointment(id, appointmentDTO);
        return ResponseEntity.ok(updatedAppointment);
    }

    // Delete an appointment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id,
                                                  @AuthenticationPrincipal UserDetails userDetails) {
        // Optionally, verify that the appointment belongs to the authenticated user
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

    // Additional appointment-related endpoints can be added here
}