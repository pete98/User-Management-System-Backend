package com.pranav_sailor.user_management_system.repository;

import com.pranav_sailor.user_management_system.entity.Appointment;
import com.pranav_sailor.user_management_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByUser(User user);
}
