package com.pranav_sailor.user_management_system.dto;

import lombok.*;
import java.time.LocalDateTime;

//Purpose: Represents appointment data without exposing the entire Appointment entity.

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentDTO {
    private Long id;
    private Long userId;
    private LocalDateTime appointmentDateTime;
    private String description;
}
