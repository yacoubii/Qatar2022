package com.example.Qatar2022.repositories;

import com.example.Qatar2022.entities.TicketEntity;
import com.example.Qatar2022.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.Optional;
@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, Long> {
    Optional<TicketEntity> findTicketEntityByBeginningTime(LocalTime beginningTime);
}


